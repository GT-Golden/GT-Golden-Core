package com.github.gtgolden.gtgoldencore.machines.api.items;

import net.minecraft.entity.player.PlayerBase;
import net.minecraft.item.ItemInstance;
import net.minecraft.tileentity.TileEntityBase;
import uk.co.benjiweber.expressions.tuple.BiTuple;

public interface HasItemStorage extends HasItemIO {
    ItemStorage getItemStorage();

    default int getInventorySize() {
        return getItemStorage().getInventorySize();
    }

    @Override
    default int getInventorySize(SlotType type) {
        return getItemStorage().getInventorySize(type);
    }

    default ItemInstance getInventoryItem(int slot) {
        return getItemStorage().getInventoryItem(slot);
    }

    @Override
    default ItemInstance getInventoryItem(SlotType type, int slot) {
        return getItemStorage().getInventoryItem(type, slot);
    }

    default ItemInstance takeInventoryItem(int slot, int count) {
        return getItemStorage().takeInventoryItem(slot, count);
    }

    @Override
    default ItemInstance takeInventoryItem(SlotType type, int slot, int count) {
        return getItemStorage().takeInventoryItem(type, slot, count);
    }

    default void setInventoryItem(int slot, ItemInstance itemInstance) {
        getItemStorage().setInventoryItem(slot, itemInstance);
    }

    @Override
    default void setInventoryItem(SlotType type, int slot, ItemInstance itemInstance) {
        getItemStorage().setInventoryItem(type, slot, itemInstance);
    }

    @Override
    default boolean hasItem(ItemInstance inputItem, SlotType... typesToCheck) {
        return getItemStorage().hasItem(inputItem, typesToCheck);
    }

    @Override
    default BiTuple<Boolean, ItemInstance> attemptSend(ItemInstance inputItem, SlotType... permittedInputTypes) {
        return getItemStorage().attemptSend(inputItem, permittedInputTypes);
    }

    @Override
    default ItemInstance attemptTake(ItemInstance desiredItem, SlotType... typesToCheck) {
        return getItemStorage().attemptTake(desiredItem, typesToCheck);
    }

    default int getMaxItemCount() {
        return getItemStorage().getMaxItemCount();
    }

    default void markDirty() {
        getItemStorage().getMaxItemCount();
    }

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
