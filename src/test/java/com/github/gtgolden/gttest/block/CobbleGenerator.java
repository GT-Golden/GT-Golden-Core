package com.github.gtgolden.gttest.block;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.item.ItemBase;
import net.minecraft.level.Level;
import net.minecraft.tileentity.TileEntityBase;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.template.block.TemplateBlockWithEntity;

public class CobbleGenerator extends TemplateBlockWithEntity {
    public CobbleGenerator(Identifier identifier, Material material) {
        super(identifier, material);
        setTranslationKey(identifier.toString());
    }

    @Override
    protected TileEntityBase createTileEntity() {
        return new CobbleGeneratorEntity();
    }

    @Override
    public boolean canUse(Level level, int x, int y, int z, PlayerBase player) {
        var entity = (CobbleGeneratorEntity) level.getTileEntity(x, y, z);
        if (player.getHeldItem() == null) {
            var cobblestoneSlot = entity.getInventoryItem("cobble").foundItem();
            if (cobblestoneSlot == null) return super.canUse(level, x, y, z, player);
            player.inventory.setInventoryItem(player.inventory.selectedHotbarSlot, cobblestoneSlot);
            entity.setInventoryItem("cobble", null);
            return true;
        } else if (player.getHeldItem().itemId == ItemBase.waterBucket.id) {
            if (entity.getInventoryItem("water").foundItem() == null) {
                entity.setInventoryItem("water", player.getHeldItem());
                player.inventory.setInventoryItem(player.inventory.selectedHotbarSlot, null);
                return true;
            }
        } else if (player.getHeldItem().itemId == ItemBase.lavaBucket.id) {
            if (entity.getInventoryItem("lava").foundItem() == null) {
                entity.setInventoryItem("lava", player.getHeldItem());
                player.inventory.setInventoryItem(player.inventory.selectedHotbarSlot, null);
                return true;
            }
        }
//        else if (player.getHeldItem().itemId == ItemBase.bucket.id) {
//            var entity = (CobbleGeneratorEntity) level.getTileEntity(x, y, z);
//            if (entity.getInventoryItem(SlotType.INPUT, 1) == null) {
//                entity.itemStorage.setInventoryItem(SlotType.INPUT, 0, player.getHeldItem());
//                player.inventory.setInventoryItem(player.inventory.selectedHotbarSlot, new ItemInstance(ItemBase.bucket));
//            }
//        }
        return super.canUse(level, x, y, z, player);
    }

    @Override
    public void onBlockRemoved(Level level, int x, int y, int z) {
        ((CobbleGeneratorEntity) level.getTileEntity(x, y, z)).dropContents(level, x, y, z);
        super.onBlockRemoved(level, x, y, z);
    }
}
