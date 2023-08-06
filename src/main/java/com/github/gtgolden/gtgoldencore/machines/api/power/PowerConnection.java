package com.github.gtgolden.gtgoldencore.machines.api.power;

import net.modificationstation.stationapi.api.util.math.Direction;

public interface PowerConnection {
    default boolean isPowerInput(Direction side) {
        return true;
    }

    default boolean isPowerOutput(Direction side) {
        return false;
    }
}
