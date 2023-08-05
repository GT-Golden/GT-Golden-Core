package com.github.gtgolden.gtgoldencore.materials.impl;

import net.minecraft.item.ItemInstance;
import net.minecraft.item.tool.ToolMaterial;

import java.awt.*;
import java.util.HashMap;

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
    public String name;
    private final ToolMaterial toolMaterial;
    private final Color color;
    private final HashMap<String, ItemInstance> states;

    public GTMaterial(Color color, ToolMaterial baseMaterial, String name, HashMap<String, ItemInstance> states) {
        this.color = color;
        this.toolMaterial = baseMaterial;
        this.name = name;
        this.states = states;
    }

    public ToolMaterial getToolMaterial() {
        return toolMaterial;
    }

    public Color getMaterialColor() {
        return color;
    }

    public ItemInstance as(String state) {
        return states.get(state);
    }

    public String[] states() {
        return states.keySet().toArray(new String[0]);
    }

    public static class Builder {

    }
}