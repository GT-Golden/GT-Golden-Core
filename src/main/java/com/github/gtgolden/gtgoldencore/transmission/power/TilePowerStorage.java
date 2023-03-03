package com.github.gtgolden.gtgoldencore.transmission.power;

import com.github.gtgolden.gtgoldencore.transmission.Connection;

public interface TilePowerStorage extends Connection {
    int getCurrentPower();

    int getMaxPower();

    int charge(PowerPacket powerPacket, int side);

    int consume(PowerPacket powerPacket, int side);
}
