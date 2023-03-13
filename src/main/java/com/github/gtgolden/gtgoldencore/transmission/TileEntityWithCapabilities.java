package com.github.gtgolden.gtgoldencore.transmission;

import net.minecraft.tileentity.TileEntityBase;

public class TileEntityWithCapabilities extends TileEntityBase implements HasCapabilities {
    private final TileCapabilities capabilities;

    public TileEntityWithCapabilities(TileCapabilities capabilities) {
        this.capabilities = capabilities;
    }

    @Override
    public TileCapabilities getCapabilities() {
        return capabilities;
    }
}
