package com.github.gtgolden.gtgoldencore.machines.api.item;

import net.minecraft.item.ItemInstance;

public interface ItemWithPowerStorage {
    int getMaxPower(ItemInstance itemInstance);

    default int getDefaultPower(ItemInstance itemInstance) {
        return 0;
    }

    default String getPowerStorageTag(ItemInstance itemInstance) {
        return "gt-golden-core:power";
    }

    default int getPower(ItemInstance itemInstance) {
        if (!itemInstance.getStationNBT().containsKey(getPowerStorageTag(itemInstance))) itemInstance.getStationNBT().put(getPowerStorageTag(itemInstance), getDefaultPower(itemInstance));
        return itemInstance.getStationNBT().getInt(getPowerStorageTag(itemInstance));
    }

    default int getMissingPower(ItemInstance itemInstance) {
        return getMaxPower(itemInstance) - getPower(itemInstance);
    }

    default int charge(ItemInstance itemInstance, int amount) {
        int charge = Math.min(getMissingPower(itemInstance), amount);
        itemInstance.getStationNBT().put(getPowerStorageTag(itemInstance), getPower(itemInstance) + charge);
        return charge;
    }

    default int discharge(ItemInstance itemInstance, int amount) {
        int discharge = Math.min(getPower(itemInstance), amount);
        itemInstance.getStationNBT().put(getPowerStorageTag(itemInstance), getPower(itemInstance) - discharge);
        return discharge;
    }
}
