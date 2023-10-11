package com.github.gtgolden.gtgoldencore.machines.api.slot;

import net.minecraft.inventory.InventoryBase;

@FunctionalInterface
public interface GTSlotFactory {
    GTSlot createSlot(String label, InventoryBase arg, int i, int j, int k);
}
