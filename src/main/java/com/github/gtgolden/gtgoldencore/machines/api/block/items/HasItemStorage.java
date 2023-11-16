package com.github.gtgolden.gtgoldencore.machines.api.block.items;

import com.github.gtgolden.gtgoldencore.machines.api.slot.GTSlot;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.item.ItemInstance;
import net.minecraft.level.Level;
import net.minecraft.tileentity.TileEntityBase;

public interface HasItemStorage extends ItemIO {
    ItemStorage getItemStorage();

    @Override
    default GTSlot[] getSlots() {
        return getItemStorage().getSlots();
    }

    @Override
    default int getInventorySize() {
        return getItemStorage().getInventorySize();
    }

    @Override
    default ItemInstance getInventoryItem(int slot) {
        return getItemStorage().getInventoryItem(slot);
    }

    @Override
    default ItemInstance takeInventoryItem(int slot, int count) {
        return getItemStorage().takeInventoryItem(slot, count);
    }

    default void setInventoryItem(int slot, ItemInstance itemInstance) {
        getItemStorage().setInventoryItem(slot, itemInstance);
    }

    @Override
    default int getMaxItemCount() {
        return getItemStorage().getMaxItemCount();
    }

    default void dropContents(Level level, int x, int y, int z) {
        getItemStorage().dropContents(level, x, y, z);
    }

    @Override
    default boolean canPlayerUse(PlayerBase arg) {
        if (this instanceof TileEntityBase tileEntity) {
            if (tileEntity.level.getTileEntity(tileEntity.x, tileEntity.y, tileEntity.z) != this) {
                return false;
            } else {
                return !(arg.squaredDistanceTo((double) tileEntity.x + 0.5, (double) tileEntity.y + 0.5, (double) tileEntity.z + 0.5) > 64.0);
            }
        }
        return true;
    }
}
