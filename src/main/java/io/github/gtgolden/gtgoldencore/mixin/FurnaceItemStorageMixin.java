package io.github.gtgolden.gtgoldencore.mixin;

import io.github.gtgolden.gtgoldencore.machines.api.block.items.ItemConnection;
import io.github.gtgolden.gtgoldencore.machines.api.block.items.ItemIO;
import io.github.gtgolden.gtgoldencore.machines.api.slot.*;
import net.minecraft.item.ItemInstance;
import net.minecraft.tileentity.TileEntityFurnace;
import net.modificationstation.stationapi.api.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(TileEntityFurnace.class)
public abstract class FurnaceItemStorageMixin implements ItemIO, ItemConnection {
    @Unique
    GTSlot[] slots;

    @Inject(
            method = "<init>()V",
            at = @At("RETURN"),
            locals = LocalCapture.CAPTURE_FAILHARD
    )
    private void addSlots(CallbackInfo ci) {
        slots = new GTSlot[]{
                new GTSlotBuilder(GTInputSlot::new)
                        .withLabel("input")
                        .withInventory(this, 0)
                        .withCoordinates(56, 17)
                        .build(),
                new GTSlotBuilder(GTFuelInputSlot::new)
                        .withLabel("fuel_input")
                        .withInventory(this, 1)
                        .withCoordinates(56, 53)
                        .build(),
                new GTSlotBuilder(GTOutputSlot::new)
                        .withLabel("output")
                        .withInventory(this, 2)
                        .withCoordinates(116, 35)
                        .build()
        };
    }

    @Override
    public ItemInstance attemptSendItem(Direction side, ItemInstance inputItem, int maxThroughput) {
        return switch (side) {
            case DOWN:
                yield inputItem;
            case UP:
                yield getSlot("input").get().attemptSendItem(inputItem, maxThroughput);
            default:
                yield getSlot("fuel_input").get().attemptSendItem(inputItem, maxThroughput);
        };
    }

    @Override
    public boolean isItemInput(Direction side) {
        return side != Direction.DOWN;
    }

    @Override
    public boolean isItemOutput(Direction side) {
        return side == Direction.DOWN;
    }

    @Shadow
    public abstract int getInventorySize();

    @Override
    public GTSlot[] getSlots() {
        return slots;
    }
}
