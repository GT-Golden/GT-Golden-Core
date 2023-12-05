package io.github.gtgolden.gttest.block;

import io.github.gtgolden.gtgoldencore.machines.api.block.items.HasItemStorage;
import io.github.gtgolden.gtgoldencore.machines.api.block.items.ItemIO;
import io.github.gtgolden.gtgoldencore.machines.api.block.items.ItemStorage;
import io.github.gtgolden.gtgoldencore.machines.api.block.power.HasPowerStorage;
import io.github.gtgolden.gtgoldencore.machines.api.block.power.PowerStorage;
import io.github.gtgolden.gtgoldencore.machines.api.slot.GTSlot;
import io.github.gtgolden.gtgoldencore.machines.api.slot.GTSlotBuilder;
import net.minecraft.block.BlockBase;
import net.minecraft.item.ItemBase;
import net.minecraft.item.ItemInstance;
import net.minecraft.tileentity.TileEntityBase;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.io.CompoundTag;

public class CobbleGeneratorEntity extends TileEntityBase implements HasPowerStorage, HasItemStorage {
    static final int COOK_TIME = 15;
    GTSlot cobbleSlot = new GTSlotBuilder().withLabel("cobble").build();
    GTSlot waterSlot = new GTSlotBuilder().withLabel("water").build();
    GTSlot lavaSlot = new GTSlotBuilder().withLabel("lava").build();
    ItemStorage itemStorage = new ItemStorage("cobbleGenItems",
            cobbleSlot,
            waterSlot,
            lavaSlot
    );
    PowerStorage powerStorage = new PowerStorage("cobbleGenPower", 256, 0);
    int timer = 0;

    @Override
    public ItemStorage getItemStorage() {
        return itemStorage;
    }

    @Override
    public PowerStorage getPowerStorage() {
        return powerStorage;
    }

    @Override
    public void tick() {
        var cobbleSlotItem = cobbleSlot.getItem();
        var waterSlotItem = waterSlot.getItem();
        var lavaSlotItem = lavaSlot.getItem();
        if (getPower() > 0 &&
                waterSlotItem != null && waterSlotItem.itemId == ItemBase.waterBucket.id &&
                lavaSlotItem != null && lavaSlotItem.itemId == ItemBase.lavaBucket.id &&
                (cobbleSlotItem == null || (cobbleSlotItem.itemId == BlockBase.COBBLESTONE.id && cobbleSlotItem.count < 64))) {
            if (timer >= COOK_TIME) {
                timer = 0;
                cobbleSlot.attemptSendItem(new ItemInstance(BlockBase.COBBLESTONE));
            } else {
                timer++;
            }
        } else {
            timer = 0;
        }
        if (cobbleSlotItem != null && level.getTileEntity(x, y + 1, z) instanceof TileEntityChest chest) {
            cobbleSlot.setStack(((ItemIO) chest).attemptSendItem(cobbleSlotItem));
        }
    }

    @Override
    public String getContainerName() {
        return "Cobble Gen";
    }

    @Override
    public void readIdentifyingData(CompoundTag tag) {
        super.readIdentifyingData(tag);
        itemStorage.readData(tag);
        powerStorage.readData(tag);
    }

    @Override
    public void writeIdentifyingData(CompoundTag tag) {
        super.writeIdentifyingData(tag);
        itemStorage.writeData(tag);
        powerStorage.writeData(tag);
    }
}
