package com.github.gtgolden.gtgoldencore.mixin;

import net.minecraft.container.slot.Slot;
import net.minecraft.inventory.InventoryBase;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(Slot.class)
public interface SlotAccessor {
    @Accessor
    int getInvSlot();

    @Mutable
    @Accessor
    void setInvSlot(int invSlot);

    @Accessor
    InventoryBase getInventory();

    @Mutable
    @Accessor
    void setInventory(InventoryBase inventory);
}
