package com.github.gtgolden.gtgoldencore.materials.impl;

import com.github.gtgolden.gtgoldencore.GTGoldenCore;
import com.github.gtgolden.gtgoldencore.materials.GTMaterials;
import com.github.gtgolden.gtgoldencore.materials.api.Material;
import com.github.gtgolden.gtgoldencore.materials.api.module.Module;
import com.github.gtgolden.gtgoldencore.materials.api.module.*;
import net.minecraft.item.ItemBase;
import net.minecraft.item.ItemInstance;
import net.minecraft.item.tool.ToolMaterial;

import java.awt.*;
import java.util.HashMap;
import java.util.Optional;

public class MaterialRegistry {
    public static final HashMap<String, Material> materials = new HashMap<>();
    private static final HashMap<String, Material> defaultMaterials = new HashMap<>();

    // TODO: Fix these colors
    public static void initDefaults() {
        defaultMaterials.put(
                "missingMaterial",
                new Material("missingMaterial")
                        .addModule(new ToolMaterialModule(GTMaterials.MISSING_TOOL_MATERIAL))
        );
        defaultMaterials.put(
                "wood",
                new Material("wood")
//                        .addModule(new ItemFormsModule("block", BlockBase.WOOD))
                        .addModule(new ColorModule(new Color(0x835C3E)))
                        .addModule(new ToolMaterialModule(
                                ToolMaterial.field_1688
                        ))
        );
        defaultMaterials.put(
                "stone",
                new Material("stone")
//                        .addModule(new ItemFormsModule("block", BlockBase.STONE))
                        .addModule(new ColorModule(new Color(0x707070)))
                        .addModule(new ToolMaterialModule(
                                ToolMaterial.field_1689
                        ))
                        .addModule(new TranslationModule(GTGoldenCore.MOD_ID.id("stone")))
        );
        defaultMaterials.put(
                "iron",
                new Material("iron")
                        .addModule(new ItemFormsModule("ingot", ItemBase.ironIngot))
                        .addModule(new ColorModule(new Color(0xFFFFFF)))
                        .addModule(new ToolMaterialModule(ToolMaterial.field_1690))
                        .addModule(new TranslationModule(GTGoldenCore.MOD_ID.id("iron")))
        );
        defaultMaterials.put(
                "gold",
                new Material("gold")
                        .addModule(new ItemFormsModule("ingot", ItemBase.goldIngot))
                        .addModule(new ColorModule(new Color(0xFFCC00)))
                        .addModule(new ToolMaterialModule(ToolMaterial.field_1692))
                        .addModule(new TranslationModule(GTGoldenCore.MOD_ID.id("gold")))
        );
        defaultMaterials.put(
                "diamond",
                new Material("diamond")
                        .addModule(new ItemFormsModule("gem", ItemBase.diamond))
                        .addModule(new ColorModule(new Color(0x61B6A8)))
                        .addModule(new ToolMaterialModule(ToolMaterial.field_1691))
                        .addModule(new TranslationModule(GTGoldenCore.MOD_ID.id("diamond")))
        );
        defaultMaterials.put(
                "redstone",
                new Material("redstone")
                        .addModule(new ItemFormsModule("dust", ItemBase.redstoneDust))
                        .addModule(new ColorModule(new Color(0xFF0000)))
                        .addModule(new TranslationModule(GTGoldenCore.MOD_ID.id("redstone")))
        );
    }

    public static boolean registerModule(String materialName, Module module) {
        if (materials.containsKey(materialName)) {
            materials.get(materialName).addModule(module);
            return true;
        } else if (defaultMaterials.containsKey(materialName)) {
            GTGoldenCore.LOGGER.info("Lazy registered default " + materialName + " material.");
            materials.put(materialName, defaultMaterials.get(materialName));
            materials.get(materialName).addModule(module);
            return true;
        } else {
            materials.put(materialName, new Material(materialName).addModule(module));
        }
        return false;
    }

    public static Optional<Material> getMaterial(String materialName) {
        if (materials.containsKey(materialName)) {
            return Optional.of(materials.get(materialName));
        } else if (defaultMaterials.containsKey(materialName)) {
            GTGoldenCore.LOGGER.info("Lazy registered default " + materialName + " material.");
            materials.put(materialName, defaultMaterials.get(materialName));
            return Optional.of(materials.get(materialName));
        }
        return Optional.empty();
    }

    public static <T extends Module> Optional<T> getMaterialModule(String materialName, Class<T> moduleName) {
        return getMaterial(materialName).flatMap(material -> material.getModule(moduleName));
    }

    public static Optional<ItemInstance> getItemForm(String materialName, String form) {
        return getMaterialModule(materialName, ItemFormsModule.class).flatMap(formModule -> formModule.getForm(form));
    }
}
