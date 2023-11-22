package io.github.gtgolden.gtgoldencore.machines.api.block.items;

import net.modificationstation.stationapi.api.util.math.Direction;

public interface ItemConnection {
    default boolean isItemInput(Direction side) {
        return true;
    }

    default boolean isItemOutput(Direction side) {
        return false;
    }
}
