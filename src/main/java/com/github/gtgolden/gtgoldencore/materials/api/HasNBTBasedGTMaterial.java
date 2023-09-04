package com.github.gtgolden.gtgoldencore.materials.api;

import com.github.gtgolden.gtgoldencore.materials.GTMaterials;
import com.github.gtgolden.gtgoldencore.materials.impl.MaterialRegistry;
import net.minecraft.item.ItemInstance;

import java.util.Optional;

public interface HasNBTBasedGTMaterial extends HasGTMaterial {
    default void setGTMaterial(ItemInstance itemInstance, String materialName) {
        itemInstance.getStationNBT().put(GTMaterials.MATERIAL_NBT_KEY, materialName);
    }

    default void setGTMaterial(ItemInstance itemInstance, Material material) {
        setGTMaterial(itemInstance, material.name);
    }

    @Override
    default Optional<Material> getGTMaterial(ItemInstance itemInstance) {
        return MaterialRegistry.getMaterial(itemInstance.getStationNBT().getString(GTMaterials.MATERIAL_NBT_KEY));
    }
}
