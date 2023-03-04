package com.github.gtgolden.gtgoldencore.material;

import com.github.gtgolden.gtgoldencore.item.MetaItem;
import com.github.gtgolden.gtgoldencore.utils.ColorConverter;
import com.google.common.collect.ImmutableList;
import net.minecraft.item.ItemBase;
import net.minecraft.item.ItemInstance;
import net.minecraft.item.tool.ToolMaterial;
import net.modificationstation.stationapi.api.item.tool.ToolMaterialFactory;
import org.apache.logging.log4j.message.Message;
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
        materialInfo.verifyInfo(materialProperties, true);
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
        return states.get(state);
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
        private final List<MaterialStack> componentList;
        private ToolMaterial toolMaterial;
        private String name;
        private List<MaterialStack> materialStack;
        private int color;

        public Builder(@NotNull String name) {
            this.states = new HashMap<>();
            this.componentList = new ArrayList<>();
        }

        public GTMaterial build() {
            GTMaterial material = new GTMaterial(
                    new MaterialInfo(name, materialStack),
                    new MaterialProperties(),
                    toolMaterial,
                    states
            );
            Materials.put(name, material);
//            LOGGER.info(material);
//            LOGGER.info(StackWalker.getInstance().walk(stream -> stream.skip(1).findFirst().get()).toString().split("\\.")[3]);
//            StackTraceElement[] stacktrace = Thread.currentThread().getStackTrace();
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
            List<MaterialStack> elementStack = new ArrayList<>();
            for (int i = 0; i < components.length; i += 2) {
                if (components[i].getClass() != GTMaterial.class || components[i+1].getClass() != int.class) {
                    LOGGER.trace(
                            components[i].getClass().getName() + " and " +
                            components[i + 1].getClass().getName() +
                            " is not GTMaterial and int"
                    );
                    continue;
                }
                elementStack.add(new MaterialStack(
                        (GTMaterial) components[i],
                        (Integer) components[i + 1]
                ));
            }
            return this;
        }

        public Builder components(MaterialStack... components) {
            componentList.addAll(List.of(components));
            return this;
        }

        public Builder components(ImmutableList<MaterialStack> components) {
            componentList.addAll(components);
            return this;
        }
    }

    private static class MaterialProperties {

    }

    private static class MaterialInfo {
        /**
         * The unlocalized name of this Material.
         * <p>
         * Required.
         */
        private final String name;
        /**
         * The mod this material added first.
         * <p>
         * Required.
         */
        private final String sourceMod;
        /**
         * The color of this Material.
         * <p>
         * Default: 0xFFFFFF if no Components, otherwise it will be the average of Components.
         */
        private int color = -1;
        /**
         * The color of this Material.
         * <p>
         * Default: 0xFFFFFF if no Components, otherwise it will be the average of Components.
         */
        private boolean hasFluidColor = true;
        /**
         * The components of this Material.
         * <p>
         * Default: none.
         */
        private final ImmutableList<MaterialStack> componentList;
        /**
         * The Element of this Material, if it is a direct Element.
         * <p>
         * Default: none.
         */
        private Element element;
        private MaterialInfo(String name, List<MaterialStack> materialStacks) {
            this.name = name;
            this.sourceMod = Materials.modID;
            if (materialStacks == null)
                materialStacks = new ArrayList<>();
            this.componentList = ImmutableList.copyOf(materialStacks);
        }

        private void verifyInfo(MaterialProperties p, boolean averageRGB) {
            // Verify MaterialRGB
            if (color != -1) return;
            if (!averageRGB || componentList == null || componentList.isEmpty()) {
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