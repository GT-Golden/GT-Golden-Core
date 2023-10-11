package com.github.gtgolden.gttest.block;

import com.github.gtgolden.gtgoldencore.machines.api.block.items.ItemConnection;
import com.github.gtgolden.gtgoldencore.machines.api.block.items.ItemIO;
import com.github.gtgolden.gtgoldencore.machines.api.slot.GTSlot;
import net.minecraft.tileentity.TileEntityBase;
import net.modificationstation.stationapi.api.util.math.Direction;

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
        if (aboveEntity instanceof ItemIO aboveStorage && belowEntity instanceof ItemIO belowStorage) {
            if (aboveEntity instanceof ItemConnection aboveConnection && belowEntity instanceof ItemConnection belowConnection) {
                if (!aboveConnection.isItemInput(Direction.DOWN)) return;
                if (!belowConnection.isItemOutput(Direction.UP)) return;
                for (GTSlot slot : belowStorage.getSlots()) {
                    if (slot.getItem() == null || !slot.canTake()) continue;
                    var wantingToTake = slot.getItem().copy();
                    wantingToTake.count = 64;
                    var taken = belowStorage.attemptTake(wantingToTake);
                    aboveStorage.attemptSendItem(taken);
                }
            }
        }
    }
}
