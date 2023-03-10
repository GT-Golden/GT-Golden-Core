package com.github.gtgolden.gtgoldencore.material;

import net.modificationstation.stationapi.api.registry.ModID;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

public class Materials {

    private static final HashMap<String, GTMaterial> materials = new HashMap<>();
    protected static String modID;
    public static void put(String name, GTMaterial material) {
        materials.put(name, material);
    }

    /**
     * Don't forget to supply MOD_ID of your mod before registering any materials
     */
    public static void useModID(@Nullable ModID modID) {
        Materials.modID = null;
        if (modID != null)
            Materials.modID = modID.getName();
    }
    @NotNull
    public static GTMaterial get(@NotNull String name) {
        return materials.getOrDefault(name, materials.get("missing"));
    }
    public static String @NotNull [] allNames() {
        return materials.keySet().toArray(new String[0]);
    }
    public static GTMaterial @NotNull [] allMaterials() {
        return materials.values().toArray(new GTMaterial[0]);
    }
}
