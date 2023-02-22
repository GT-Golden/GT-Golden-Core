package com.github.gtgolden.gtgoldencore.material;

import java.util.HashMap;

public class Materials {

    private static HashMap<String, GTMaterial> materials = new HashMap<>();
    public static void put(String name, GTMaterial material) {
        materials.put(name, material);
    }
    public static GTMaterial get(String name) {
        return materials.getOrDefault(name, materials.get(""));
    }
    public static String[] allNames() {
        return materials.keySet().toArray(new String[0]);
    }
    public static GTMaterial[] allMaterials() {
        return materials.values().toArray(new GTMaterial[0]);
    }
}
