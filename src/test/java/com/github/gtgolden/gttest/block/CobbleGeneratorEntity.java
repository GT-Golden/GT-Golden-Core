package com.github.gtgolden.gttest.block;

import com.github.gtgolden.gtgoldencore.machines.api.block.items.HasItemIO;
import com.github.gtgolden.gtgoldencore.machines.api.block.items.HasItemStorage;
import com.github.gtgolden.gtgoldencore.machines.api.block.items.ItemStorage;
import com.github.gtgolden.gtgoldencore.machines.api.block.items.SlotType;
import com.github.gtgolden.gtgoldencore.machines.api.block.power.HasPowerStorage;
import com.github.gtgolden.gtgoldencore.machines.api.block.power.PowerStorage;
import net.minecraft.block.BlockBase;
import net.minecraft.item.ItemBase;
import net.minecraft.item.ItemInstance;
import net.minecraft.tileentity.TileEntityBase;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.io.CompoundTag;
import uk.co.benjiweber.expressions.tuple.BiTuple;

public class CobbleGeneratorEntity extends TileEntityBase implements HasPowerStorage, HasItemStorage {
    static final int COOK_TIME = 15;
    ItemStorage itemStorage = new ItemStorage("cobbleGenItems", BiTuple.of(SlotType.INPUT, 2), BiTuple.of(SlotType.OUTPUT, 1));
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
        var cobbleSlot = getInventoryItem(SlotType.OUTPUT, 0);
        var waterBucketSlot = getInventoryItem(SlotType.INPUT, 0);
        var lavaBucketSlot = getInventoryItem(SlotType.INPUT, 1);
        if (getPower() > 0 &&
                waterBucketSlot != null && waterBucketSlot.itemId == ItemBase.waterBucket.id &&
                lavaBucketSlot != null && lavaBucketSlot.itemId == ItemBase.lavaBucket.id &&
                (cobbleSlot == null || (cobbleSlot.itemId == BlockBase.COBBLESTONE.id && cobbleSlot.count < 64))) {
            if (timer >= COOK_TIME) {
                timer = 0;
                if (cobbleSlot == null) {
                    setInventoryItem(SlotType.OUTPUT, 0, new ItemInstance(BlockBase.COBBLESTONE));
                } else {
                    cobbleSlot.count++;
                }
            } else {
                timer++;
            }
        } else {
            timer = 0;
        }
        if (cobbleSlot != null && level.getTileEntity(x, y + 1, z) instanceof TileEntityChest chest) {
            setInventoryItem(SlotType.OUTPUT, 0, ((HasItemIO) chest).attemptSend(cobbleSlot, SlotType.MIXED).two());
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
