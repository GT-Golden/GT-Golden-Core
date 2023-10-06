package com.github.gtgolden.gtgoldencore.machines.api.item;

import com.github.gtgolden.gtgoldencore.GTGoldenCore;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.inventory.InventoryBase;
import net.minecraft.item.ItemInstance;
import net.minecraft.util.io.CompoundTag;
import net.minecraft.util.io.ListTag;

public class ItemInventoryBase implements InventoryBase {
    public ItemInstance itemInstance;
    String name;
    ItemInstance[] inventory;

    public ItemInventoryBase() {
        GTGoldenCore.LOGGER.error("Created an empty item inventory without any data. This should never be ran!");
    }

    public ItemInventoryBase(String name, int inventorySize, ItemInstance itemInstance) {
        this.name = name;
        inventory = new ItemInstance[inventorySize];

        this.itemInstance = itemInstance;
        ListTag tag = itemInstance.getStationNBT().getListTag(name);

        for (int i = 0; i < tag.size(); ++i) {
            CompoundTag compoundTag = (CompoundTag) tag.get(i);
            int slot = compoundTag.getByte("Slot") & 255;
            if (slot < inventory.length) {
                inventory[slot] = new ItemInstance(compoundTag);
            }
        }
    }

    public void writeData() {
        ListTag listTag = new ListTag();

        for (int i = 0; i < inventory.length; ++i) {
            if (inventory[i] != null) {
                CompoundTag compoundTag = new CompoundTag();
                compoundTag.put("Slot", (byte) i);
                inventory[i].toTag(compoundTag);
                listTag.add(compoundTag);
            }
        }

        itemInstance.getStationNBT().put(name, listTag);
    }

    @Override
    public int getInventorySize() {
        return inventory.length;
    }

    @Override
    public ItemInstance getInventoryItem(int slot) {
        return inventory[slot];
    }

    @Override
    public ItemInstance takeInventoryItem(int slot, int count) {
        if (inventory[slot] != null) {
            ItemInstance var3;
            if (inventory[slot].count <= count) {
                var3 = inventory[slot];
                inventory[slot] = null;
                return var3;
            } else {
                var3 = inventory[slot].split(count);
                if (inventory[slot].count == 0) {
                    inventory[slot] = null;
                }
                return var3;
            }
        } else {
            return null;
        }
    }

    @Override
    public void setInventoryItem(int slot, ItemInstance itemInstance) {
        inventory[slot] = itemInstance;
        if (itemInstance != null && itemInstance.count > getMaxItemCount()) {
            itemInstance.count = getMaxItemCount();
        }
    }

    @Override
    public String getContainerName() {
        return name;
    }

    @Override
    public int getMaxItemCount() {
        return 64;
    }

    @Override
    public void markDirty() {
        writeData();
    }

    @Override
    public boolean canPlayerUse(PlayerBase player) {
        return true;
    }
}
