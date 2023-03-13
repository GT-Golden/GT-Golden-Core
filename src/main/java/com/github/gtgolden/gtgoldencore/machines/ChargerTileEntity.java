package com.github.gtgolden.gtgoldencore.machines;

import com.github.gtgolden.gtgoldencore.transmission.TileCapabilities;
import com.github.gtgolden.gtgoldencore.transmission.TileEntityWithCapabilities;
import com.github.gtgolden.gtgoldencore.transmission.power.PowerPacket;

public class ChargerTileEntity extends TileEntityWithCapabilities {
    public ChargerTileEntity() {
        super(new TileCapabilities.Builder().setupPower(200, 200).build());
    }

    @Override
    public void tick() {
        System.out.println("Charging..");
        var powerStorage = getPowerStorage();
        if (powerStorage != null) {
            System.out.println("Charger power: " + powerStorage.getCurrentPower());

            var aboveEntity = level.getTileEntity(x, y + 1, z);
            if (aboveEntity instanceof TileEntityWithCapabilities foundCapabilities) {
                var foundPower = foundCapabilities.getCapabilities().getPowerStorage();
                if (foundPower != null) {
                    int consumeAmount = foundPower.charge(new PowerPacket(Math.min(32, powerStorage.getCurrentPower()), 1, false), 0);
                    System.out.println("Consumed " + consumeAmount);
                    int consumedAmount = powerStorage.consume(new PowerPacket(consumeAmount, 1, false), 0);
                    System.out.println("Actually consumed " + consumedAmount);
                }
            }
        }
    }
}
