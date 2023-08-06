package com.github.gtgolden.gtgoldencore.materials.impl;

import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.resource.language.TranslationStorage;
import net.minecraft.item.ItemInstance;
import net.minecraft.item.tool.ToolMaterial;
import net.modificationstation.stationapi.api.registry.Identifier;

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
    private final Identifier identifier;
    private final String translationKey;
    private final ToolMaterial toolMaterial;
    private final Color color;
    private final HashMap<String, ItemInstance> states;

    public GTMaterial(Color color, ToolMaterial baseMaterial, Identifier identifier, String translationKey, HashMap<String, ItemInstance> states) {
        this.color = color;
        this.toolMaterial = baseMaterial;
        this.translationKey = translationKey;
        this.identifier = identifier;
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

    public String getName() {
        return identifier.id;
    }

    public String getTranslationKey() {
        return translationKey;
    }

    public String getTranslatedName() {
        return I18n.translate(translationKey);
    }
}