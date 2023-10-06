package com.github.gtgolden.gtgoldencore.machines.api.block.items;

import net.modificationstation.stationapi.api.util.math.Direction;

import java.util.Arrays;

public interface HasItemIO extends ItemIO, ItemConnection {
    @Override
    default boolean isItemInput(Direction side) {
        return Arrays.stream(getAcceptedTypes(side)).anyMatch(type -> type == SlotType.INPUT || type == SlotType.FUEL_INPUT || type == SlotType.MIXED);
    }

    @Override
    default boolean isItemOutput(Direction side) {
        return Arrays.stream(getAcceptedTypes(side)).anyMatch(type -> type == SlotType.OUTPUT || type == SlotType.MIXED);
    }

    default SlotType[] getAcceptedTypes(Direction side) {
        return getAcceptedTypes();
    }
}
