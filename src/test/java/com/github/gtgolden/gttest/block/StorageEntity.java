package com.github.gtgolden.gttest.block;

import com.github.gtgolden.gtgoldencore.machines.api.block.items.HasItemStorage;
import com.github.gtgolden.gtgoldencore.machines.api.block.items.ItemConnection;
import com.github.gtgolden.gtgoldencore.machines.api.block.items.ItemStorage;
import com.github.gtgolden.gtgoldencore.machines.api.slot.GTSlot;
import com.github.gtgolden.gtgoldencore.machines.api.slot.GTSlotBuilder;
import net.minecraft.tileentity.TileEntityBase;
import net.minecraft.util.io.CompoundTag;
import net.modificationstation.stationapi.api.util.math.Direction;

public class StorageEntity extends TileEntityBase implements HasItemStorage, ItemConnection {
    ItemStorage itemStorage;
    public StorageEntity() {
        GTSlot[] slots = new GTSlot[27];
        int offsetX = 7;
        int offsetY = 17;
        int slot = 0;
        for (int row = 0; row < 3; row++) {
            for (int column = 0; column < 9; column++) {
                slots[slot] = new GTSlotBuilder().withCoordinates(offsetX + column * 18, offsetY + row * 18).build();
                slot++;
            }
        }
        itemStorage = new ItemStorage("storage_entity_storage", slots);
    }
    @Override
    public ItemStorage getItemStorage() {
        return itemStorage;
    }

    @Override
    public String getContainerName() {
        return "Storage Entity";
    }

    @Override
    public boolean isItemOutput(Direction side) {
        return true;
    }

    @Override
    public void readIdentifyingData(CompoundTag arg) {
        super.readIdentifyingData(arg);
        itemStorage.readData(arg);
    }

    @Override
    public void writeIdentifyingData(CompoundTag arg) {
        super.writeIdentifyingData(arg);
        itemStorage.writeData(arg);
    }
}
