package com.github.gtgolden.gtgoldencore.machines;

import net.minecraft.inventory.InventoryBase;
import net.minecraft.item.ItemInstance;

public interface ItemIO extends InventoryBase {
    int getInventorySize(SlotType type);
    ItemInstance getInventoryItem(SlotType type, int slot);
    ItemInstance takeInventoryItem(SlotType type, int slot, int count);
    void setInventoryItem(SlotType type, int slot, ItemInstance itemInstance);
}
