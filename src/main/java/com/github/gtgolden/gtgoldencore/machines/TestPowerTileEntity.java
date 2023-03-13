package com.github.gtgolden.gtgoldencore.machines;

import com.github.gtgolden.gtgoldencore.transmission.StorageType;
import com.github.gtgolden.gtgoldencore.transmission.TileCapabilities;
import com.github.gtgolden.gtgoldencore.transmission.TileEntityWithCapabilities;
import com.github.gtgolden.gtgoldencore.transmission.power.TilePowerStorage;

public class TestPowerTileEntity extends TileEntityWithCapabilities {
    public TestPowerTileEntity() {
        super(new TileCapabilities.Builder().setupPower(800).build());
    }

    @Override
    public void tick() {
        System.out.println("Updating Powered Entity..");
        TilePowerStorage powerStorage = (TilePowerStorage) getCapabilities().getStorage(StorageType.power);
        if (powerStorage != null) {
            System.out.println("Power: " + powerStorage.getCurrentPower());
        }
    }
}
