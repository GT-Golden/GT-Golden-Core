package com.github.gtgolden.gtgoldencore.material;

import com.github.gtgolden.gtgoldencore.item.MetaItem;
import com.github.gtgolden.gtgoldencore.material.chemistry.Element;
import com.github.gtgolden.gtgoldencore.material.chemistry.Elements;
import com.github.gtgolden.gtgoldencore.material.chemistry.Formulas;
import com.github.gtgolden.gtgoldencore.material.property.MaterialProperties;
import com.github.gtgolden.gtgoldencore.material.property.MaterialProperty;
import com.github.gtgolden.gtgoldencore.material.property.properties.ToolProperty;
import com.github.gtgolden.gtgoldencore.utils.ChemUtils;
import com.github.gtgolden.gtgoldencore.utils.ColorConverter;
import com.google.common.collect.ImmutableList;
import net.minecraft.item.ItemInstance;
import net.minecraft.item.tool.ToolMaterial;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

    @NotNull
    private GTMaterial(
            @NotNull MaterialInfo materialInfo,
            @NotNull MaterialProperties materialProperties
    ) {
        this.materialInfo = materialInfo;
        this.materialProperties = materialProperties;
    }

    public String getName() {
        return materialInfo.name;
    }

    public String getSourceMod() {
        return materialInfo.sourceMod;
    }

    public MaterialProperty getToolProperty() {
        return materialProperties.get("tool");
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
        return materialProperties.states;
    }

    public String getFormula() {
        return Formulas.get(this.getName());
    }

    public static class Builder {
        private final List<String> states;
        private final HashMap<String, MaterialProperty<?>> properties;
        private final List<MaterialStack> chemList;
        private final String name;
        private String formula;
        private int color;

        public Builder(@NotNull String name) {
            this.name = name;
            this.states = new ArrayList<>();
            this.chemList = new ArrayList<>();
            this.properties = new HashMap<>();
        }

        public GTMaterial build() {
            GTMaterial material = new GTMaterial(
                    new MaterialInfo(this),
                    new MaterialProperties(properties, states)
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

        public Builder toolProperty(int miningLevel, int durability, float miningSpeed, int attackDamage) {
            // default for diamond is 3, 1561, 8.0, 3
            return setProperty("tool", new ToolProperty(miningLevel, durability, miningSpeed, attackDamage));
        }

        public Builder setProperty(String name, MaterialProperty<?> materialProperty) {
            properties.put(name, materialProperty);
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
            return states(Elements.get(name).symbol);
        }

        public Builder components(String chemFormula) {
            Formulas.add(chemFormula, this.name);
            Formulas.add(this.name, chemFormula);

            List<MaterialStack> components = ChemUtils.parse(chemFormula);

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

        private MaterialInfo(@NotNull Builder builder) {
            this.name = builder.name;
            this.sourceMod = Materials.modID;
            this.componentList = ImmutableList.copyOf(builder.chemList);
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
                colorTemp += Materials.get(stack.name()).materialInfo.color * stack.amount();
                divisor += stack.amount();
            }
            color = (int) (colorTemp / divisor);

        }

    }
}