package io.github.gtgolden.gtgoldencore.machines.api.slot;

import net.minecraft.inventory.InventoryBase;
import net.minecraft.item.ItemInstance;

public class GTInputSlot extends GTSlot {
    public GTInputSlot(String label, InventoryBase arg, int i, int j, int k) {
        super(label, arg, i, j, k);
    }

    @Override
    public boolean canMachineTake(ItemInstance item) {
        return false;
    }
}
