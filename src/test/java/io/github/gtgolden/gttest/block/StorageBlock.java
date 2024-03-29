package io.github.gtgolden.gttest.block;

import io.github.gtgolden.gtgoldencore.machines.api.gui.SimpleGTGui;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.level.Level;
import net.minecraft.tileentity.TileEntityBase;
import net.modificationstation.stationapi.api.template.block.TemplateBlockWithEntity;
import net.modificationstation.stationapi.api.util.Identifier;

public class StorageBlock extends TemplateBlockWithEntity {
    public StorageBlock(Identifier identifier, Material material) {
        super(identifier, material);
        setTranslationKey(identifier);
    }

    @Override
    protected TileEntityBase createTileEntity() {
        return new StorageEntity();
    }

    @Override
    public boolean canUse(Level level, int x, int y, int z, PlayerBase player) {
        SimpleGTGui.openGui(level, x, y, z, player);
        return true;
    }
}
