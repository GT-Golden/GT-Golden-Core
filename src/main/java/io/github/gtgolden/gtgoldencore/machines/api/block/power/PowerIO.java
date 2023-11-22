package io.github.gtgolden.gtgoldencore.machines.api.block.power;

public interface PowerIO {
    int getMaxPower();

    int getPower();

    int getMissingPower();

    int charge(int amount);

    int discharge(int amount);
}
