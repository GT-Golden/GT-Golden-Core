package com.github.gtgolden.gttest.block;

import com.github.gtgolden.gtgoldencore.machines.HasPowerStorage;
import com.github.gtgolden.gtgoldencore.machines.PowerStorage;
import net.minecraft.entity.Item;
import net.minecraft.item.ItemBase;
import net.minecraft.item.ItemInstance;
import net.minecraft.tileentity.TileEntityBase;
import net.minecraft.util.io.CompoundTag;

public class AppleSpawnerEntity extends TileEntityBase implements HasPowerStorage {
    PowerStorage powerStorage = new PowerStorage("appleSpawnerPower", 128, 0);

    @Override
    public PowerStorage getPowerStorage() {
        return powerStorage;
    }

    @Override
    public void tick() {
        if (powerStorage.getPower() >= 32) {
            var item = new Item(level, x + .5, y + 1.5, z + .5, new ItemInstance(ItemBase.apple));
            level.spawnEntity(item);
            powerStorage.discharge(32);
        }
    }

    @Override
    public void readIdentifyingData(CompoundTag tag) {
        super.writeIdentifyingData(tag);
        powerStorage.readData(tag);
    }

    @Override
    public void writeIdentifyingData(CompoundTag tag) {
        super.writeIdentifyingData(tag);
        powerStorage.writeData(tag);
    }
}
