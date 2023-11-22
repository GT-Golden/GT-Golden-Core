package io.github.gtgolden.gtgoldencore.machines.api.item;

import io.github.gtgolden.gtgoldencore.machines.api.slot.GTSlot;
import net.minecraft.inventory.InventoryBase;
import net.minecraft.item.ItemInstance;
import net.minecraft.util.io.ListTag;

public interface ItemWithItemStorage {
    GTSlot[] getSlots(ItemInstance itemInstance);

    default GTSlot getSlot(ItemInstance itemInstance, int i) {
        return getSlots(itemInstance)[i];
    }

    default int getInventorySize(ItemInstance itemInstance) {
        return getSlots(itemInstance).length;
    }

    default String getItemStorageTag(ItemInstance itemInstance) {
        return "gt-golden-core:inventory";
    }

    default ListTag getInventoryTag(ItemInstance itemInstance) {
        return itemInstance.getStationNbt().getListTag("gt-golden-core:inventory");
    }

    default InventoryBase getInventory(ItemInstance itemInstance) {
        return new ItemInventoryBase(getItemStorageTag(itemInstance), itemInstance, getSlots(itemInstance).clone());
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
