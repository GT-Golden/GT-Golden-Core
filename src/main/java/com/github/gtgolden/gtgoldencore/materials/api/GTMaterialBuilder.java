package com.github.gtgolden.gtgoldencore.materials.api;

import com.github.gtgolden.gtgoldencore.materials.impl.GTMaterial;
import com.github.gtgolden.gtgoldencore.materials.impl.GTMaterialRegistry;
import net.minecraft.item.ItemBase;
import net.minecraft.item.ItemInstance;
import net.minecraft.item.tool.ToolMaterial;
import net.modificationstation.stationapi.api.item.tool.ToolMaterialFactory;
import net.modificationstation.stationapi.api.registry.Identifier;

import java.awt.*;
import java.util.HashMap;

import static com.github.gtgolden.gtgoldencore.GTGoldenCore.LOGGER;

public class GTMaterialBuilder {
    private ToolMaterial toolMaterial;
    private Color color;
    private final Identifier identifier;
    private String translationKey;
    private final HashMap<String, ItemInstance> states;

    public GTMaterialBuilder(Identifier identifier) {
        this.identifier = identifier;
        this.states = new HashMap<>();
        translationKey(identifier);
    }

    public GTMaterial build() {
        GTMaterial material = new GTMaterial(color, toolMaterial, identifier, translationKey, states);
        GTMaterialRegistry.register(material);
        return material;
    }

    public GTMaterialBuilder translationKey(Identifier translationKey) {
        this.translationKey = String.format("material.%s.name", translationKey.toString());
        return this;
    }

    public GTMaterialBuilder toolProperties(ToolMaterial material) {
        toolProperties(
                material.getMiningLevel(), material.getDurability(),
                material.getMiningSpeed(), material.getAttackDamage()
        );
        return this;
    }

    public GTMaterialBuilder toolProperties(int miningLevel, int durability, float miningSpeed, int attackDamage) {
        // default for diamond is 3, 1561, 8.0, 3
        toolMaterial = ToolMaterialFactory
                .create(identifier.id, miningLevel, durability, miningSpeed, attackDamage);
        return this;
    }

    public GTMaterialBuilder color(Color color) {
        this.color = color;
        return this;
    }

    public GTMaterialBuilder states(String... states) {
        for (String state : states)
            setItem(state, MetaItem.convert(state, identifier.id));
        return this;
    }

    public GTMaterialBuilder setItem(String state, ItemBase itemBase) {
        return setItem(state, new ItemInstance(itemBase));
    }

    public GTMaterialBuilder setItem(String state, ItemInstance itemInstance) {
        GTMaterial material = GTMaterialRegistry.get(identifier.id);
        if (material != null && material.as(state) != null)
            LOGGER.error("Can't find state " + state + " for material " + identifier.id);
        else
            this.states.put(state, itemInstance);
        return this;
    }
}
