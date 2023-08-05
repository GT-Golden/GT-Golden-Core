package com.github.gtgolden.gttest.block;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntityBase;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.template.block.TemplateBlockWithEntity;

public class ItemMover extends TemplateBlockWithEntity {
    public ItemMover(Identifier identifier, Material material) {
        super(identifier, material);
        setTranslationKey(identifier.toString());
    }

    @Override
    protected TileEntityBase createTileEntity() {
        return new ItemMoverEntity();
    }
}
