package com.github.gtgolden.gtgoldencore.material;

import com.github.gtgolden.gtgoldencore.utils.ColorConverter;
import net.minecraft.item.ItemBase;
import net.minecraft.item.ItemInstance;
import net.minecraft.item.tool.ToolMaterial;
import net.modificationstation.stationapi.api.item.tool.ToolMaterialFactory;

import java.awt.*;
import java.util.HashMap;

import static com.github.gtgolden.gtgoldencore.material.MaterialState.*;

public class GTMaterial {

    public String name;
    private final ToolMaterial toolMaterial;
    private final int color;
    private HashMap<MaterialState, ItemInstance> states;

    private GTMaterial(int color, ToolMaterial baseMaterial, String name, HashMap<MaterialState, ItemInstance> states) {
        this.color = color;
        this.toolMaterial = baseMaterial;
        this.name = name;
        this.states = states;
    }


    public static GTMaterial registerNewUniqueMaterial(int color, ToolMaterial baseMaterial, String name, HashMap<MaterialState, ItemInstance> states) {
        GTMaterial material = new GTMaterial(color, baseMaterial, name, states);
        Materials.put(name, material);
        return material;
    }

    public ToolMaterial getToolMaterial() {
        return toolMaterial;
    }

    public int getMaterialColor() {
        return color;
    }

    public ItemInstance ingot() {
        return states.get("ingot");
    }
    public ItemInstance dust() {
        return states.get("dust");
    }
    public ItemInstance ore() {
        return states.get("ore");
    }

    public static class Builder {
        private ToolMaterial material;
        private int color;
        private String name;
        private HashMap<MaterialState, ItemInstance> states;
        public Builder(String name) {
            this.name = name;
            this.states = new HashMap<>();
        }
        public GTMaterial build() {
            return GTMaterial.registerNewUniqueMaterial(color, material, name, states);
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
            material = ToolMaterialFactory
                    .create(name, miningLevel, durability, miningSpeed, attackDamage);
            return this;
        }
        public Builder color(int rgb) {
            return color(new Color(rgb));
        }
        public Builder color(int r, int g, int b) {
            return color(new Color(r,g,b));
        }
        public Builder color(Color rgba) {
            color = ColorConverter.colorToInt(rgba);
            return this;
        }

        // mess? maybe..
        public Builder states(MaterialState ... states) {
            for(MaterialState state: states) {
                // DynamicItem will be used only for conversion during registry events
                // TODO: uncomment when DynamicItem is done
                // this.states.put(ingot, DynamicItem.ingot(name));
            }
            return this;
        }
        public Builder ingot(ItemBase itemBase) {
            ingot(new ItemInstance(itemBase));
            return this;
        }
        public Builder ingot(ItemInstance itemInstance) {
            states.put(ingot, itemInstance);
            return this;
        }

        public Builder dust(ItemBase itemBase) {
            dust(new ItemInstance(itemBase));
            return this;
        }
        public Builder dust(ItemInstance itemInstance) {
            states.put(dust, itemInstance);
            return this;
        }

        public Builder ore(ItemBase itemBase) {
            ore(new ItemInstance(itemBase));
            return this;
        }
        public Builder ore(ItemInstance itemInstance) {
            states.put(ore, itemInstance);
            return this;
        }

        // Todo: fluid, sludge, gas, crystal, etc...
    }
}