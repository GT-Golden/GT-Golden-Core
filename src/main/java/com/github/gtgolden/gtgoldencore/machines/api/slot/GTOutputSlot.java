package com.github.gtgolden.gtgoldencore.machines.api.slot;

import net.minecraft.inventory.InventoryBase;
import net.minecraft.item.ItemInstance;

public class GTOutputSlot extends GTSlot {
    public GTOutputSlot(String label, InventoryBase arg, int i, int j, int k) {
        super(label, arg, i, j, k);
    }

    @Override
    public boolean canPlayerInsert(ItemInstance item) {
        return false;
    }
}
