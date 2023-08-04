package com.github.gtgolden.gtgoldencore.machines;

public interface PowerIO {
    int getMaxPower();

    int getPower();

    int getMissingPower();

    int charge(int amount);

    int discharge(int amount);
}
