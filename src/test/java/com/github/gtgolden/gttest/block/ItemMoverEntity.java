package com.github.gtgolden.gttest.block;

import com.github.gtgolden.gtgoldencore.machines.api.items.HasItemIO;
import com.github.gtgolden.gtgoldencore.machines.api.items.SlotType;
import net.minecraft.tileentity.TileEntityBase;

public class ItemMoverEntity extends TileEntityBase {
    int timer = 0;
    @Override
    public void tick() {
        if (timer < 20) {
            timer++;
            return;
        }
        timer = 0;
        var aboveEntity = level.getTileEntity(x, y + 1, z);
        var belowEntity = level.getTileEntity(x, y - 1, z);
        if (aboveEntity instanceof HasItemIO aboveStorage && belowEntity instanceof HasItemIO belowStorage) {
            for (int i = 0; i < belowStorage.getInventorySize(SlotType.OUTPUT); i++) {
                var item = belowStorage.getInventoryItem(SlotType.OUTPUT, i);
                if (item == null) continue;
                var result = aboveStorage.attemptSend(item, 5, SlotType.INPUT, SlotType.MIXED);
                if (result.one()) {
                    belowStorage.setInventoryItem(SlotType.OUTPUT, i, result.two());
                    return;
                } else {
                    result = aboveStorage.attemptSend(item, SlotType.MIXED, SlotType.MIXED);
                    if (result.one()) {
                        belowStorage.setInventoryItem(SlotType.OUTPUT, i, result.two());
                        return;
                    }
                }
            }
            for (int i = 0; i < belowStorage.getInventorySize(SlotType.MIXED); i++) {
                var item = belowStorage.getInventoryItem(SlotType.MIXED, i);
                if (item == null) continue;
                var result = aboveStorage.attemptSend(item, 5, SlotType.INPUT, SlotType.MIXED);
                if (result.one()) {
                    belowStorage.setInventoryItem(SlotType.MIXED, i, result.two());
                    return;
                } else {
                    result = aboveStorage.attemptSend(item, SlotType.MIXED, SlotType.MIXED);
                    if (result.one()) {
                        belowStorage.setInventoryItem(SlotType.MIXED, i, result.two());
                        return;
                    }
                }
            }
        }
    }
}
