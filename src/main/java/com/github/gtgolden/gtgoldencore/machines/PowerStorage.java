package com.github.gtgolden.gtgoldencore.machines;

public class PowerStorage {
    final int maxPower;
    int power;

    public PowerStorage(int maxPower, int startingPower) {
        this.maxPower = maxPower;
        this.power = startingPower;
    }

    public int getMaxPower() {
        return maxPower;
    }

    public int getPower() {
        return power;
    }

    public int getMissingPower() {
        return maxPower - power;
    }

    public int charge(int amount) {
        int charge = Math.min(getMissingPower(), amount);
        power += charge;
        return charge;
    }

    public int discharge(int amount) {
        int discharge = Math.min(getPower(), amount);
        power -= discharge;
        return discharge;
    }
}
