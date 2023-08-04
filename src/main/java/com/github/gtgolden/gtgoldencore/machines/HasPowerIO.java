package com.github.gtgolden.gtgoldencore.machines;

import net.modificationstation.stationapi.api.util.math.Direction;

public interface HasPowerIO extends PowerIO {
    default boolean isPowerInput(Direction side) {
        return true;
    }

    default boolean isPowerOutput(Direction side) {
        return false;
    }
}
