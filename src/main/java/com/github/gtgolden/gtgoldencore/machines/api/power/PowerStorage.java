package com.github.gtgolden.gtgoldencore.machines.api.power;

import com.github.gtgolden.gtgoldencore.machines.impl.HasSavableData;
import net.minecraft.util.io.CompoundTag;

public class PowerStorage implements PowerIO, HasSavableData {
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

    public void readData(CompoundTag tag) {
        power = tag.getInt(name + "_power");
    }

    public void writeData(CompoundTag tag) {
        tag.put(name + "_power", power);
    }
}
