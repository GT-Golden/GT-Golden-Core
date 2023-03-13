package com.github.gtgolden.gtgoldencore.machines;

import com.github.gtgolden.gtgoldencore.transmission.TileCapabilities;
import com.github.gtgolden.gtgoldencore.transmission.TileEntityWithCapabilities;

public class TestPowerTileEntity extends TileEntityWithCapabilities {
    public TestPowerTileEntity() {
        super(new TileCapabilities.Builder().setupPower(800).build());
    }

    @Override
    public void tick() {
        System.out.println("Updating Powered Entity..");
        var powerStorage = getCapabilities().getPowerStorage();
        if (powerStorage != null) {
            System.out.println("Power: " + powerStorage.getCurrentPower());
        }
    }
}
