package com.github.gtgolden.gtgoldencore.machines;

public interface HasPowerStorage {
    default boolean isPowerInput(int side) {
        return true;
    }

    default boolean isPowerOutput(int side) {
        return false;
    }

    PowerStorage getPowerStorage();
}
