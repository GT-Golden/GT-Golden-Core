package com.github.gtgolden.gtgoldencore.materials.impl;

import com.github.gtgolden.gtgoldencore.GTGoldenCore;
import com.github.gtgolden.gtgoldencore.materials.GTMaterials;

import java.awt.*;
import java.util.HashMap;
import java.util.Objects;

public class GTMaterialRegistry {
    public static final GTMaterial MISSING_MATERIAL = new GTMaterial(Color.BLACK, null, GTGoldenCore.MOD_ID.id(""), "material.gt-golden-core:.name", null);

    private static final HashMap<String, GTMaterial> materials = new HashMap<>();

    public static void register(GTMaterial material) {
        materials.put(material.getName(), material);
        if (Objects.equals(material.getName(), "")) {
            GTMaterials.LOGGER.info("Registered null (\"\") material.");
        } else {
            GTMaterials.LOGGER.info("Registered material: " + material.getName() + ".");
        }
    }

    public static GTMaterial get(String name) {
        return materials.getOrDefault(name, MISSING_MATERIAL);
    }

    public static String[] allNames() {
        return materials.keySet().toArray(new String[0]);
    }

    public static GTMaterial[] allMaterials() {
        return materials.values().toArray(new GTMaterial[0]);
    }
}
