package com.github.gtgolden.gtgoldencore.machines;

import net.minecraft.util.io.CompoundTag;

public class PowerStorage {
    final int maxPower;
    int power;
    String name;

    public PowerStorage(String name, int maxPower, int startingPower) {
        this.name = name;
        this.maxPower = maxPower;
        this.power = startingPower;
    }

    public PowerStorage(String name, int maxPower) {
        this(name, maxPower, 0);
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

    public void readIdentifyingData(CompoundTag tag) {
        power = tag.getInt(name + "_power");
    }

    public void writeIdentifyingData(CompoundTag tag) {
        tag.put(name + "_power", power);
    }
}
