package com.github.gtgolden.gtgoldencore.transmission.power;

import com.github.gtgolden.gtgoldencore.transmission.Storage;

// TODO Voltage/Amperage
public class TilePowerStorage implements Storage {
    private final int maxPower;
    int power;

    public TilePowerStorage(int maxPower, int defaultPower) {
        this.maxPower = maxPower;
        this.power = defaultPower;
    }

    public int getCurrentPower() {
        return power;
    }

    public int getMaxPower() {
        return maxPower;
    }

    public int charge(PowerPacket powerPacket, int side) {
        int chargeAmount = Math.min(powerPacket.voltage(), maxPower - power);
        if (!powerPacket.simulated()) {
            power += chargeAmount;
        }
        return chargeAmount;
    }

    public int consume(PowerPacket powerPacket, int side) {
        int consumeAmount = Math.min(power, powerPacket.voltage());
        System.out.println("Consuming " + consumeAmount);
        if (!powerPacket.simulated()) {
            power -= consumeAmount;
        }
        return consumeAmount;
    }

    public static class Builder {
        private static final int DEFAULT_MAX = 800;

        private int maxPower = DEFAULT_MAX;
        private int defaultPower = 0;

        public Builder setMaxPower(int maxPower) {
            this.maxPower = maxPower;
            return this;
        }

        public Builder setDefaultPower(int defaultPower) {
            this.defaultPower = defaultPower;
            return this;
        }

        public TilePowerStorage build() {
            return new TilePowerStorage(maxPower, defaultPower);
        }

    }
}
