package com.github.gtgolden.gttest.block;

import com.github.gtgolden.gtgoldencore.machines.api.block.items.HasItemStorage;
import com.github.gtgolden.gtgoldencore.machines.api.block.items.ItemIO;
import com.github.gtgolden.gtgoldencore.machines.api.block.items.ItemStorage;
import com.github.gtgolden.gtgoldencore.machines.api.block.power.HasPowerStorage;
import com.github.gtgolden.gtgoldencore.machines.api.block.power.PowerStorage;
import com.github.gtgolden.gtgoldencore.machines.api.slot.GTSlotBuilder;
import net.minecraft.block.BlockBase;
import net.minecraft.item.ItemBase;
import net.minecraft.item.ItemInstance;
import net.minecraft.tileentity.TileEntityBase;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.io.CompoundTag;

public class CobbleGeneratorEntity extends TileEntityBase implements HasPowerStorage, HasItemStorage {
    static final int COOK_TIME = 15;
    ItemStorage itemStorage = new ItemStorage("cobbleGenItems",
            new GTSlotBuilder().withLabel("cobble").build(),
            new GTSlotBuilder().withLabel("water").build(),
            new GTSlotBuilder().withLabel("lava").build());
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
        var cobbleSlot = getInventoryItem("cobble").foundItem();
        var waterBucketSlot = getInventoryItem("water").foundItem();
        var lavaBucketSlot = getInventoryItem("lava").foundItem();
        if (getPower() > 0 &&
                waterBucketSlot != null && waterBucketSlot.itemId == ItemBase.waterBucket.id &&
                lavaBucketSlot != null && lavaBucketSlot.itemId == ItemBase.lavaBucket.id &&
                (cobbleSlot == null || (cobbleSlot.itemId == BlockBase.COBBLESTONE.id && cobbleSlot.count < 64))) {
            if (timer >= COOK_TIME) {
                timer = 0;
                getSlot("cobble").get().attemptSendItem(new ItemInstance(BlockBase.COBBLESTONE));
            } else {
                timer++;
            }
        } else {
            timer = 0;
        }
        if (cobbleSlot != null && level.getTileEntity(x, y + 1, z) instanceof TileEntityChest chest) {
            setInventoryItem("cobble", ((ItemIO) chest).attemptSendItem(cobbleSlot));
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
