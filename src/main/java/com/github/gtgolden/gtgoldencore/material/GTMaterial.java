package com.github.gtgolden.gtgoldencore.material;

import com.github.gtgolden.gtgoldencore.item.MetaItem;
import com.github.gtgolden.gtgoldencore.material.chemistry.Element;
import com.github.gtgolden.gtgoldencore.material.chemistry.Elements;
import com.github.gtgolden.gtgoldencore.material.property.MaterialProperties;
import com.github.gtgolden.gtgoldencore.material.property.MaterialProperty;
import com.github.gtgolden.gtgoldencore.material.property.properties.ToolProperty;
import com.github.gtgolden.gtgoldencore.utils.ColorConverter;
import com.google.common.collect.ImmutableList;
import net.minecraft.item.ItemInstance;
import net.minecraft.item.tool.ToolMaterial;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static com.github.gtgolden.gtgoldencore.GTGoldenCore.LOGGER;

/**
 * Example:
 *
 * @EventListener public void registerItems(ItemRegistryEvent event) {
 * new GTMaterial.Builder("name")
 * .toolProperties(3, 512, 5, 10)
 * .color(100, 0, 255)
 * .states(ingot, dust, ore)
 * .build();
 * }
 */
public class GTMaterial {

    @NotNull
    private final MaterialInfo materialInfo;
    @NotNull
    private final MaterialProperties materialProperties;
    private final List<String> states;
    private String chemicalFormula;

    @NotNull
    private GTMaterial(
            @NotNull MaterialInfo materialInfo,
            @NotNull MaterialProperties materialProperties,
            @NotNull List<String> states
    ) {
        this.materialInfo = materialInfo;
        this.materialProperties = materialProperties;
        this.states = states;
    }

    public String getName() {
        return materialInfo.name;
    }

    public String getSourceMod() {
        return materialInfo.sourceMod;
    }

    public MaterialProperty getToolMaterial() {
        return materialProperties.get("toolProperty");
    }

    public int getMaterialColor() {
        return materialInfo.color;
    }

    public ItemInstance as(String state) {
        return MetaItem.convert(state, this.getName());
    }

    public ItemInstance as(String state, int amount) {
        ItemInstance item = MetaItem.convert(state, this.getName());
        item.count = amount;
        return item;
    }

    public List<String> states() {
        return states;
    }

    public Element getElement() {
        return materialInfo.element;
    }

    private String calculateChemicalFormula() {
        if (chemicalFormula != null) return chemicalFormula;
        if (materialInfo.element != null) {
            return materialInfo.element.getSymbol();
        }
        if (!materialInfo.componentList.isEmpty()) {
            StringBuilder components = new StringBuilder();
            for (MaterialStack component : materialInfo.componentList)
                components.append(component.toString());
            return components.toString();
        }
        return "";
    }

    public static class Builder {
        private final MaterialProperties materialProperties;
        private Element element;
        private final List<String> states;
        private final List<MaterialStack> chemList;
        private final String name;
        private int color;

        public Builder(@NotNull String name) {
            this.name = name;
            this.states = new ArrayList<>();
            this.chemList = new ArrayList<>();
            this.materialProperties = new MaterialProperties(this);
        }

        public GTMaterial build() {
            GTMaterial material = new GTMaterial(
                    new MaterialInfo(this),
                    materialProperties,
                    states
            );
            Materials.put(name, material);
            return material;
        }

        public Builder toolProperty(@NotNull ToolMaterial toolMaterial) {
            return toolProperty(
                    toolMaterial.getMiningLevel(), toolMaterial.getDurability(),
                    toolMaterial.getMiningSpeed(), toolMaterial.getAttackDamage()
            );
        }

        public Builder setProperty(String name, MaterialProperty<?> materialProperty) {
            materialProperties.set(name, materialProperty);
            return this;
        }
        public Builder toolProperty(int miningLevel, int durability, float miningSpeed, int attackDamage) {
            // default for diamond is 3, 1561, 8.0, 3
            materialProperties.set("tool", new ToolProperty(miningLevel, durability, miningSpeed, attackDamage));
            return this;
        }

        public Builder color(int rgb) {
            return color(new Color(rgb));
        }

        public Builder color(short r, short g, short b) {
            return color(new Color(r, g, b));
        }

        public Builder color(Color rgba) {
            color = ColorConverter.colorToInt(rgba);
            return this;
        }

        /**
         * @param state name of MetaItem
         */
        public Builder state(String state, @Nullable int harvestLevel, @Nullable int burnTime) {
            // todo harvest level & burn time
            return states(state);
        }

        /**
         * @param states names of MetaItem's
         */
        public Builder states(@NotNull String... states) {
            this.states.addAll(List.of(states));
            return this;
        }

        public Builder element(String name) {
            element = Elements.get(name);
            return this;
        }

        /**
         * @param components: GTMaterial, int, ...
         */
        public Builder components(Object @NotNull ... components) {
            if (components.length % 2 != 0) {
                LOGGER.warn("Material Components list malformed!");
                return this;
            }
            for (int i = 0; i < components.length; i += 2) {
                if (components[i].getClass() != GTMaterial.class || components[i + 1].getClass() != int.class) {
                    LOGGER.warn(
                            components[i].getClass().getName() + " and " +
                                    components[i + 1].getClass().getName() +
                                    " is not GTMaterial and int"
                    );
                    continue;
                }
                this.chemList.add(new MaterialStack(
                        (GTMaterial) components[i],
                        (Integer) components[i + 1]
                ));
            }
            return this;
        }

        public Builder components(MaterialStack... components) {
            chemList.addAll(List.of(components));
            return this;
        }

        public Builder components(List<MaterialStack> components) {
            chemList.addAll(components);
            return this;
        }
    }

    private static class MaterialInfo {
        private final String name;
        private final String sourceMod;
        private int color;
        private final boolean hasFluidColor = true;
        private final ImmutableList<MaterialStack> componentList;
        private final Element element;

        private MaterialInfo(@NotNull Builder builder) {
            this.name = builder.name;
            this.sourceMod = Materials.modID;
            this.componentList = ImmutableList.copyOf(builder.chemList);
            this.element = builder.element;
            this.color = builder.color;
            averageColor();
        }

        private void averageColor() {
            // Verify MaterialRGB
            if (color != -1) return;
            if (componentList == null || componentList.isEmpty()) {
                color = 0xFFFFFF;
                return;
            }
            long colorTemp = 0;
            int divisor = 0;
            for (MaterialStack stack : componentList) {
                colorTemp += stack.material().materialInfo.color * stack.amount();
                divisor += stack.amount();
            }
            color = (int) (colorTemp / divisor);

        }

    }
}