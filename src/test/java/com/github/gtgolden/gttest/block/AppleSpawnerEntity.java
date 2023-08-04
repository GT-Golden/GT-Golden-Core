package com.github.gtgolden.gttest.block;

import com.github.gtgolden.gtgoldencore.machines.HasPowerStorage;
import com.github.gtgolden.gtgoldencore.machines.PowerStorage;
import net.minecraft.entity.Item;
import net.minecraft.item.ItemBase;
import net.minecraft.item.ItemInstance;
import net.minecraft.tileentity.TileEntityBase;

public class AppleSpawnerEntity extends TileEntityBase implements HasPowerStorage {
    PowerStorage powerStorage = new PowerStorage(128, 0);
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
}
