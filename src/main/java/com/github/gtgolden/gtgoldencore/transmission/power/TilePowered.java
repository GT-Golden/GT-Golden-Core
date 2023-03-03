package com.github.gtgolden.gtgoldencore.transmission.power;

public interface TilePowered {
    void updateAllConnections();

    void markPowerDirty();

    int charge(int min, int i, boolean b);
}
