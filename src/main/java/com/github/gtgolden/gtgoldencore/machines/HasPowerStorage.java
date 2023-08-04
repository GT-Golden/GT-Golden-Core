package com.github.gtgolden.gtgoldencore.machines;

public interface HasPowerStorage {
    default boolean isPowerInput(int side) {
        return true;
    }

    default boolean isPowerOutput(int side) {
        return false;
    }

    PowerStorage getPowerStorage();

    default int getMaxPower() {
        return getPowerStorage().getMaxPower();
    }

    default int getPower() {
        return getPowerStorage().getPower();
    }

    default int getMissingPower() {
        return getPowerStorage().getMissingPower();
    }

    default int charge(int amount) {
        return getPowerStorage().charge(amount);
    }

    default int discharge(int amount) {
        return getPowerStorage().discharge(amount);
    }
}
