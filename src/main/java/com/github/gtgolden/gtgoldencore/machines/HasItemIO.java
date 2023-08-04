package com.github.gtgolden.gtgoldencore.machines;

public interface HasItemIO extends ItemIO {
    default boolean isItemInput(int side) {
        return true;
    }

    default boolean isItemOutput(int side) {
        return false;
    }
}
