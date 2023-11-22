package io.github.gtgolden.gttest.block;

import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntityBase;
import net.modificationstation.stationapi.api.template.block.TemplateBlockWithEntity;
import net.modificationstation.stationapi.api.util.Identifier;

public class AppleSpawner extends TemplateBlockWithEntity {
    public AppleSpawner(Identifier identifier, Material material) {
        super(identifier, material);
        setTranslationKey(identifier.toString());
    }

    @Override
    protected TileEntityBase createTileEntity() {
        return new AppleSpawnerEntity();
    }
}
