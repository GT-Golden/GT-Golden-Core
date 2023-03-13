package com.github.gtgolden.gtgoldencore.transmission;

import com.github.gtgolden.gtgoldencore.transmission.power.TilePowerStorage;
import org.jetbrains.annotations.Nullable;

public interface HasCapabilities {
    TileCapabilities getCapabilities();

    default public @Nullable TilePowerStorage getPowerStorage() {
        return getCapabilities().getPowerStorage();
    }
}
