package com.github.gtgolden.gtgoldencore.material;

import com.github.gtgolden.gtgoldencore.item.MetaItem;
import com.github.gtgolden.gtgoldencore.utils.ColorConverter;
import com.google.common.collect.ImmutableList;
import net.minecraft.item.ItemBase;
import net.minecraft.item.ItemInstance;
import net.minecraft.item.tool.ToolMaterial;
import net.modificationstation.stationapi.api.item.tool.ToolMaterialFactory;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.github.gtgolden.gtgoldencore.GTGoldenCore.LOGGER;

/**
 * Example:
 *
 * @EventListener public void registerItems(ItemRegistryEvent event) {
 *     new GTMaterial.Builder("name")
 *         .toolProperties(3, 512, 5, 10)
 *         .color(100, 0, 255)
 *         .states(ingot, dust, ore)
 *         .build();
 * }
 */
public class GTMaterial {

    @NotNull
    private final MaterialInfo materialInfo;
    @NotNull
    private final MaterialProperties materialProperties;
    private final ToolMaterial toolMaterial;
    private HashMap<String, ItemInstance> states;
    private String chemicalFormula;
    private ImmutableList<MaterialStack> componentList;

    @NotNull
    private GTMaterial(
            @NotNull MaterialInfo materialInfo,
            @NotNull MaterialProperties materialProperties,
            @Nullable ToolMaterial baseMaterial,
            @NotNull HashMap<String, ItemInstance> states
    ) {
        this.materialInfo = materialInfo;
        this.materialProperties = materialProperties;
        this.toolMaterial = baseMaterial;
        this.states = states;
    }

    public String getName() {
        return materialInfo.name;
    }
    public String getSourceMod() {
        return materialInfo.sourceMod;
    }

    public ToolMaterial getToolMaterial() {
        return toolMaterial;
    }

    public int getMaterialColor() {
        return materialInfo.color;
    }

    public ItemInstance as(String state) {
        return states.get(state).copy();
    }
    public ItemInstance as(String state, int amount) {
        ItemInstance item = states.get(state).copy();
        item.count = amount;
        return item;
    }

    public String[] states() {
        return states.keySet().toArray(new String[0]);
    }

    public Element getElement() {
        return materialInfo.element;
    }

    private String calculateChemicalFormula() {
        if (chemicalFormula != null) return chemicalFormula;
        if (materialInfo.element != null) {
            return materialInfo.element.getSymbol();
        }
        if (!componentList.isEmpty()) {
            StringBuilder components = new StringBuilder();
            for (MaterialStack component : componentList)
                components.append(component.toString());
            return components.toString();
        }
        return "";
    }

    public static class Builder {
        private Element element;
        private final HashMap<String, ItemInstance> states;
        private final List<MaterialStack> chemList;
        private ToolMaterial toolMaterial;
        private String name;
        private int color;

        public Builder(@NotNull String name) {
            this.name = name;
            this.states = new HashMap<>();
            this.chemList = new ArrayList<>();
        }

        public GTMaterial build() {
            GTMaterial material = new GTMaterial(
                    new MaterialInfo(this),
                    new MaterialProperties(),
                    toolMaterial,
                    states
            );
            Materials.put(name, material);
            return material;
        }

        public Builder toolProperties(@NotNull ToolMaterial toolMaterial) {
            return toolProperties(
                    toolMaterial.getMiningLevel(), toolMaterial.getDurability(),
                    toolMaterial.getMiningSpeed(), toolMaterial.getAttackDamage()
            );
        }

        public Builder toolProperties(int miningLevel, int durability, float miningSpeed, int attackDamage) {
            // default for diamond is 3, 1561, 8.0, 3
            toolMaterial = ToolMaterialFactory
                    .create(name, miningLevel, durability, miningSpeed, attackDamage);
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

        public Builder state(String state, @Nullable int harvestLevel, @Nullable int burnTime) {
            // todo harvest level & burn time
            return states(state);
        }

        /**
         * @param states names of MetaItem's
         */
        public Builder states(@NotNull String... states) {
            for (String state : states)
                setItem(state, MetaItem.convert(state, name));
            return this;
        }

        public Builder setItem(@NotNull String state, @NotNull ItemBase itemBase) {
            return setItem(state, new ItemInstance(itemBase));
        }

        public Builder setItem(@NotNull String state, @NotNull ItemInstance itemInstance) {
            GTMaterial material = Materials.get(name);
            if (material != null && material.as(state) != null)
                LOGGER.warn(material.as(state).getTranslationKey() + " already exists");
            else
                this.states.put(state, itemInstance);
            return this;
        }

        public Builder element(String name) {
            element = Elements.get(name);
            return this;
        }


        /**
         * @param components: GTMaterial, int, ...
         */
        public Builder components(Object... components) {
            if (components.length % 2 != 0) {
                LOGGER.trace("Material Components list malformed!");
                return this;
            }
            for (int i = 0; i < components.length; i += 2) {
                if (components[i].getClass() != GTMaterial.class || components[i+1].getClass() != int.class) {
                    LOGGER.trace(
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

    private static class MaterialProperties {

    }

    private static class MaterialInfo {
        private final String name;
        private final String sourceMod;
        private int color;
        private final boolean hasFluidColor = true;
        private final ImmutableList<MaterialStack> componentList;
        private final Element element;
        private MaterialInfo(Builder builder) {
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