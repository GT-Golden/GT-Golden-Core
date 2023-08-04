package com.github.gtgolden.gttest.block;

import com.github.gtgolden.gtgoldencore.machines.HasPowerStorage;
import com.github.gtgolden.gtgoldencore.machines.PowerStorage;
import net.minecraft.block.BlockBase;
import net.minecraft.tileentity.TileEntityBase;
import net.modificationstation.stationapi.api.util.math.Direction;

public class GeneratorEntity extends TileEntityBase implements HasPowerStorage {
    PowerStorage powerStorage = new PowerStorage(512, 0);
    @Override
    public PowerStorage getPowerStorage() {
        return powerStorage;
    }

    @Override
    public void tick() {
        if (level.getTileId(x, y - 1, z) == BlockBase.DIAMOND_BLOCK.id) {
            powerStorage.charge(1);
            level.setBlockState(x, y, z, level.getBlockState(x, y, z).with(Generator.LIT_PROPERTY, true));
        } else {
            level.setBlockState(x, y, z, level.getBlockState(x, y, z).with(Generator.LIT_PROPERTY, false));
        }

        var tileEntity = level.getTileEntity(x, y + 1, z);
        if (tileEntity != null) {
            if (tileEntity instanceof HasPowerStorage foundPowerStorage && foundPowerStorage.isPowerInput(Direction.DOWN.getId())) {
                powerStorage.discharge(foundPowerStorage.getPowerStorage().charge(Math.min(10, powerStorage.getPower())));
            }
        }
    }
}
