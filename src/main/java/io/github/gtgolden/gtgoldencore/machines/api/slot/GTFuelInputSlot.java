package io.github.gtgolden.gtgoldencore.machines.api.slot;

import net.minecraft.inventory.InventoryBase;
import net.minecraft.item.ItemInstance;
import net.modificationstation.stationapi.api.recipe.FuelRegistry;

public class GTFuelInputSlot extends GTSlot {
    public GTFuelInputSlot(String label, InventoryBase arg, int i, int j, int k) {
        super(label, arg, i, j, k);
    }

    @Override
    public boolean canInsert(ItemInstance itemInstance) {
        return FuelRegistry.getFuelTime(itemInstance) > 0;
    }
}
