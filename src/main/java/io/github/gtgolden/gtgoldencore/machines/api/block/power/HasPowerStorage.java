package io.github.gtgolden.gtgoldencore.machines.api.block.power;

public interface HasPowerStorage extends HasPowerIO {
    PowerStorage getPowerStorage();

    default int getMaxPower() {
        return getPowerStorage().getMaxPower();
    }

    default int getPower() {
        return getPowerStorage().getPower();
    }

    default int getMissingPower() {
        return getPowerStorage().getMissingPower();
    }

    default int charge(int amount) {
        return getPowerStorage().charge(amount);
    }

    default int discharge(int amount) {
        return getPowerStorage().discharge(amount);
    }
}
