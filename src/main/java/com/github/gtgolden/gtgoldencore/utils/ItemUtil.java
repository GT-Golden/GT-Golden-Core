package com.github.gtgolden.gtgoldencore.utils;

import com.github.gtgolden.gtgoldencore.material.GTMaterial;
import com.github.gtgolden.gtgoldencore.material.Materials;
import net.minecraft.item.ItemInstance;
import net.minecraft.util.io.CompoundTag;

public class ItemUtil {
    public static boolean compare(ItemInstance filter, ItemInstance item) {
        if (item == null || filter == null) return false;

        boolean sameId = filter.itemId == item.itemId;
        if(!item.getStationNBT().values().isEmpty() && !filter.getStationNBT().values().isEmpty()) {
            boolean sameNBT = item.getStationNBT().getString("material")
                    .equals(filter.getStationNBT().getString("material"));
            return sameId && sameNBT;
        }
        return sameId;
    }
    public static GTMaterial getUniqueMaterial(ItemInstance item) {
        CompoundTag nbt = item.getStationNBT();
        return Materials.get(nbt.getString("material"));
    }
}
