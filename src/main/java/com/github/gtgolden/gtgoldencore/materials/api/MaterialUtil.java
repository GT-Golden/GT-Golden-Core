package com.github.gtgolden.gtgoldencore.materials.api;

import com.github.gtgolden.gtgoldencore.materials.impl.GTMaterial;
import com.github.gtgolden.gtgoldencore.materials.impl.GTMaterialRegistry;
import net.minecraft.item.ItemInstance;
import net.minecraft.util.io.CompoundTag;

public class MaterialUtil {
    public static boolean compare(ItemInstance filter, ItemInstance item) {
        if (item == null || filter == null) return false;

        boolean sameId = filter.itemId == item.itemId;
        if (!item.getStationNBT().values().isEmpty() && !filter.getStationNBT().values().isEmpty()) {
            boolean sameNBT = item.getStationNBT().getString("material")
                    .equals(filter.getStationNBT().getString("material"));
            return sameId && sameNBT;
        }
        return sameId;
    }

    public static GTMaterial getUniqueMaterial(ItemInstance item) {
        CompoundTag nbt = item.getStationNBT();
        if (nbt == null || nbt.getString("material") == null) return GTMaterialRegistry.get("");
        return GTMaterialRegistry.get(nbt.getString("material"));
    }
}
