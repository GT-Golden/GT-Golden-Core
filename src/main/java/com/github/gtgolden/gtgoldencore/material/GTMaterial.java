package com.github.gtgolden.gtgoldencore.material;

import com.github.gtgolden.gtgoldencore.item.MetaItem;
import com.github.gtgolden.gtgoldencore.utils.ColorConverter;
import net.minecraft.item.ItemBase;
import net.minecraft.item.ItemInstance;
import net.minecraft.item.tool.ToolMaterial;
import net.modificationstation.stationapi.api.item.tool.ToolMaterialFactory;

import java.awt.*;
import java.util.HashMap;

import static com.github.gtgolden.gtgoldencore.item.MetaItem.MISSING;
import static com.github.gtgolden.gtgoldencore.GTGoldenCore.LOGGER;

/**
 * Example:
 * @EventListener
 * public void registerItems(ItemRegistryEvent event) {
 *     new GTMaterial.Builder("name")
 *            .toolProperties(3, 512, 5, 10)
 *            .color(100, 0, 255)
 *            .states(ingot, dust, ore)
 *            .build();
 * }
 */
public class GTMaterial {
    public String name;
    private final ToolMaterial toolMaterial;
    private final int color;
    private final Element element;
    private HashMap<String, ItemInstance> states;

    private GTMaterial(int color, ToolMaterial baseMaterial, String name, HashMap<String, ItemInstance> states, Element element) {
        this.color = color;
        this.toolMaterial = baseMaterial;
        this.name = name;
        this.states = states;
        this.element = element;
    }

    public ToolMaterial getToolMaterial() {
        return toolMaterial;
    }

    public int getMaterialColor() {
        return color;
    }

    public ItemInstance as(String state) {
        return states.get(state);
    }

    public String[] states() {
        return states.keySet().toArray(new String[0]);
    }

    public Element getElement() {
        return element;
    }

    public static class Builder {
        protected ToolMaterial toolMaterial;
        protected int color;
        protected String name;
        protected HashMap<String, ItemInstance> states;
        private Element element;
        public Builder(String name) {
            this.name = name;
            this.states = new HashMap<>();
        }

        public GTMaterial build() {
            GTMaterial material = new GTMaterial(color, toolMaterial, name, states, element);
            Materials.put(name, material);
            return material;
        }

        public Builder toolProperties(ToolMaterial material){
            toolProperties(
                    material.getMiningLevel(), material.getDurability(),
                    material.getMiningSpeed(), material.getAttackDamage()
            );
            return this;
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
            return color(new Color(r,g,b));
        }
        public Builder color(Color rgba) {
            color = ColorConverter.colorToInt(rgba);
            return this;
        }

        public Builder states(String... states) {
            for(String state: states)
                setItem(state, MetaItem.convert(state, name));
            return this;
        }
        public Builder setItem(String state, ItemBase itemBase) {
            return setItem(state, new ItemInstance(itemBase));
        }
        public Builder setItem(String state, ItemInstance itemInstance) {
            GTMaterial material = Materials.get(name);
            if (material != null && material.as(state) != null)
                LOGGER.error("Can't find state " + state + " for material " + name);
            else
                this.states.put(state, itemInstance);
            return this;
        }

        public Builder element(String name) {
            element=Elements.get(name);
            return this;
        }
    }
}