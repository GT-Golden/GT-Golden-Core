package io.github.gtgolden.gtgoldencore.materials.impl;

import io.github.gtgolden.gtgoldencore.GTGoldenCore;
import io.github.gtgolden.gtgoldencore.materials.GTMaterials;
import io.github.gtgolden.gtgoldencore.materials.api.Material;
import io.github.gtgolden.gtgoldencore.materials.api.module.ColorModule;
import io.github.gtgolden.gtgoldencore.materials.api.module.ItemFormsModule;
import io.github.gtgolden.gtgoldencore.materials.api.module.Module;
import io.github.gtgolden.gtgoldencore.materials.api.module.ToolMaterialModule;
import net.minecraft.item.ItemBase;
import net.minecraft.item.ItemInstance;
import net.minecraft.item.tool.ToolMaterial;
import org.jetbrains.annotations.Nullable;

import java.awt.*;
import java.util.HashMap;

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
                        .registerTranslationProvider(GTGoldenCore.NAMESPACE)
        );
        defaultMaterials.put(
                "stone",
                new Material("stone")
//                        .addModule(new ItemFormsModule("block", BlockBase.STONE))
                        .addModule(new ColorModule(new Color(0x707070)))
                        .addModule(new ToolMaterialModule(
                                ToolMaterial.field_1689
                        ))
                        .registerTranslationProvider(GTGoldenCore.NAMESPACE)
        );
        defaultMaterials.put(
                "iron",
                new Material("iron")
                        .addModule(new ItemFormsModule("ingot", ItemBase.ironIngot))
                        .addModule(new ColorModule(new Color(0xFFFFFF)))
                        .addModule(new ToolMaterialModule(ToolMaterial.field_1690))
                        .registerTranslationProvider(GTGoldenCore.NAMESPACE)
        );
        defaultMaterials.put(
                "gold",
                new Material("gold")
                        .addModule(new ItemFormsModule("ingot", ItemBase.goldIngot))
                        .addModule(new ColorModule(new Color(0xFFCC00)))
                        .addModule(new ToolMaterialModule(ToolMaterial.field_1692))
                        .registerTranslationProvider(GTGoldenCore.NAMESPACE)
        );
        defaultMaterials.put(
                "diamond",
                new Material("diamond")
                        .addModule(new ItemFormsModule("gem", ItemBase.diamond))
                        .addModule(new ColorModule(new Color(0x61B6A8)))
                        .addModule(new ToolMaterialModule(ToolMaterial.field_1691))
                        .registerTranslationProvider(GTGoldenCore.NAMESPACE)
        );
        defaultMaterials.put(
                "redstone",
                new Material("redstone")
                        .addModule(new ItemFormsModule("dust", ItemBase.redstoneDust))
                        .addModule(new ColorModule(new Color(0xFF0000)))
                        .registerTranslationProvider(GTGoldenCore.NAMESPACE)
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

    public static @Nullable Material getMaterial(String materialName) {
        if (materials.containsKey(materialName)) {
            return materials.get(materialName);
        } else if (defaultMaterials.containsKey(materialName)) {
            GTGoldenCore.LOGGER.info("Lazy registered default " + materialName + " material.");
            materials.put(materialName, defaultMaterials.get(materialName));
            return materials.get(materialName);
        }
        return null;
    }

    public static <T extends Module> @Nullable T getMaterialModule(String materialName, Class<T> module) {
        var material = getMaterial(materialName);
        if (material == null) return null;
        return material.getModule(module);
    }

    public static @Nullable ItemInstance getItemForm(String materialName, String form) {
        var module = getMaterialModule(materialName, ItemFormsModule.class);
        if (module == null) return null;
        return module.getForm(form);
    }
}
