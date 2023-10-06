package com.github.gtgolden.gtgoldencore.machines.api.item;

import net.minecraft.inventory.InventoryBase;
import net.minecraft.item.ItemInstance;
import net.minecraft.util.io.ListTag;

public interface ItemWithItemStorage {
    int getInventorySize(ItemInstance itemInstance);

    default String getItemStorageTag(ItemInstance itemInstance) {
        return "gt-golden-core:inventory";
    }

    default ListTag getInventoryTag(ItemInstance itemInstance) {
        return itemInstance.getStationNBT().getListTag("gt-golden-core:inventory");
    }

    default InventoryBase getInventory(ItemInstance itemInstance) {
        return new ItemInventoryBase(getItemStorageTag(itemInstance), getInventorySize(itemInstance), itemInstance);
    }

    default ItemInstance getInventoryItem(ItemInstance itemInstance, int i) {
        return getInventory(itemInstance).getInventoryItem(i);
    }

    default ItemInstance takeInventoryItem(ItemInstance itemInstance, int slot, int count) {
        var inventory = getInventory(itemInstance);
        var item = inventory.takeInventoryItem(slot, count);
        inventory.markDirty();
        return item;
    }

    default void setInventoryItem(ItemInstance itemInstance, int slot, ItemInstance inputItem) {
        var inventory = getInventory(itemInstance);
        inventory.setInventoryItem(slot, inputItem);
        inventory.markDirty();
    }

    default int getMaxItemCount(ItemInstance itemInstance) {
        return 64;
    }

    default void markInventoryDirty(ItemInstance itemInstance) {
        getInventory(itemInstance).markDirty();
    }
}
