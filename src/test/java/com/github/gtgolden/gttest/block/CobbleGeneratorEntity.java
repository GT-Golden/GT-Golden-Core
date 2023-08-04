package com.github.gtgolden.gttest.block;

import com.github.gtgolden.gtgoldencore.machines.*;
import net.minecraft.block.BlockBase;
import net.minecraft.item.ItemBase;
import net.minecraft.item.ItemInstance;
import net.minecraft.tileentity.TileEntityBase;
import net.minecraft.tileentity.TileEntityChest;
import uk.co.benjiweber.expressions.tuple.BiTuple;

public class CobbleGeneratorEntity extends TileEntityBase implements HasPowerStorage, HasItemStorage {
    ItemStorage itemStorage = new ItemStorage("Cobble Gen", BiTuple.of(SlotType.INPUT, 2), BiTuple.of(SlotType.OUTPUT, 1));
    PowerStorage powerStorage = new PowerStorage(256, 0);
    int timer = 0;
    static final int COOK_TIME = 15;
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
            for (int i = 0; i < chest.getInventorySize(); i++) {
                var itemSlot = chest.getInventoryItem(i);
                if (itemSlot == null) {
                    chest.setInventoryItem(i, cobbleSlot);
                    setInventoryItem(SlotType.OUTPUT, 0, null);
                    break;
                } else if (itemSlot.itemId == BlockBase.COBBLESTONE.id && itemSlot.count < 64) {
                    int increment = Math.min(chest.getMaxItemCount() - itemSlot.count, cobbleSlot.count);
                    itemSlot.count += increment;
                    if (increment >= cobbleSlot.count) {
                        setInventoryItem(SlotType.OUTPUT, 0, null);
                    } else {
                        cobbleSlot.count -= increment;
                    }
                    break;
                }
            }
        }
    }
}