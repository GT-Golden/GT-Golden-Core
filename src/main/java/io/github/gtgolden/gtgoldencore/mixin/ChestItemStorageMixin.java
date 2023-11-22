package io.github.gtgolden.gtgoldencore.mixin;

import io.github.gtgolden.gtgoldencore.machines.api.block.items.ItemConnection;
import io.github.gtgolden.gtgoldencore.machines.api.block.items.ItemIO;
import io.github.gtgolden.gtgoldencore.machines.api.slot.GTSlot;
import net.minecraft.tileentity.TileEntityChest;
import net.modificationstation.stationapi.api.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(TileEntityChest.class)
public abstract class ChestItemStorageMixin implements ItemIO, ItemConnection {
    @Unique
    GTSlot[] slots;

    @Shadow
    public abstract int getInventorySize();

    @Inject(
            method = "<init>()V",
            at = @At("RETURN"),
            locals = LocalCapture.CAPTURE_FAILHARD
    )
    private void addSlots(CallbackInfo ci) {
        slots = new GTSlot[getInventorySize()];

        int rows = getInventorySize() / 9;

        int i = 0;
        int var4;
        int var5;
        for (var4 = 0; var4 < rows; ++var4) {
            for (var5 = 0; var5 < 9; ++var5) {
                slots[i] = new GTSlot(this, var5 + var4 * 9, 8 + var5 * 18, 18 + var4 * 18);
                i++;
            }
        }
    }

    @Override
    public boolean isItemInput(Direction side) {
        return true;
    }

    @Override
    public boolean isItemOutput(Direction side) {
        return true;
    }

    @Override
    public GTSlot[] getSlots() {
        return slots;
    }
}
