package com.github.gtgolden.gtgoldencore.machines;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntityBase;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.template.block.TemplateBlockWithEntity;

public class ChargerBlock extends TemplateBlockWithEntity {
    public ChargerBlock(Identifier identifier, Material material) {
        super(identifier, material);
    }

    @Override
    protected TileEntityBase createTileEntity() {
        return new ChargerTileEntity();
    }
}
