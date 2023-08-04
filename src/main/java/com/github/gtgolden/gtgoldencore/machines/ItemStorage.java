package com.github.gtgolden.gtgoldencore.machines;

import net.minecraft.entity.player.PlayerBase;
import net.minecraft.item.ItemInstance;
import net.minecraft.util.io.CompoundTag;
import net.minecraft.util.io.ListTag;
import uk.co.benjiweber.expressions.tuple.BiTuple;

import java.util.EnumMap;

public class ItemStorage implements ItemIO, HasSavableData {
    protected final EnumMap<SlotType, Integer> slotTypeSizes = new EnumMap<>(SlotType.class);
    protected final EnumMap<SlotType, Integer> slotTypeIndex = new EnumMap<>(SlotType.class);
    protected ItemInstance[] inventory;
    private final String name;

    public ItemStorage(String name, int size) {
        inventory = new ItemInstance[size];
        this.name = name;
        for (SlotType type :
                SlotType.values()) {
            slotTypeSizes.put(type, type == SlotType.MISC ? size : 0);
        }
        slotTypeIndex.put(SlotType.MISC, 0);
    }

    public ItemStorage(String name, BiTuple<SlotType, Integer>... storageTypes) {
        this.name = name;
        var size = 0;
        for (BiTuple<SlotType, Integer> storageType : storageTypes) {
            slotTypeIndex.put(storageType.one(), size);
            slotTypeSizes.put(storageType.one(), storageType.two());
            size += storageType.two();
        }
        inventory = new ItemInstance[size];
    }

    @Override
    public int getInventorySize() {
        return inventory.length;
    }

    public int getInventorySize(SlotType type) {
        return slotTypeIndex.containsKey(type) ? slotTypeSizes.get(type) : 0;
    }

    public ItemInstance getInventoryItem(int slot) {
        return inventory[slot];
    }

    public ItemInstance getInventoryItem(SlotType type, int slot) {
        if (!slotTypeIndex.containsKey(type) || slot > slotTypeSizes.get(type)) return null;
        return inventory[slotTypeIndex.get(type) + slot];
    }

    @Override
    public ItemInstance takeInventoryItem(int slot, int count) {
        if (inventory[slot] != null) {
            ItemInstance var3;
            if (inventory[slot].count <= count) {
                var3 = inventory[slot];
                inventory[slot] = null;
                this.markDirty();
                return var3;
            } else {
                var3 = inventory[slot].split(count);
                if (inventory[slot].count == 0) {
                    inventory[slot] = null;
                }

                this.markDirty();
                return var3;
            }
        } else {
            return null;
        }
    }

    public ItemInstance takeInventoryItem(SlotType type, int slot, int count) {
        if (!slotTypeIndex.containsKey(type) || slot > slotTypeSizes.get(type)) return null;
        return inventory[slotTypeIndex.get(type) + slot];
    }

    public void setInventoryItem(int slot, ItemInstance itemInstance) {
        inventory[slot] = itemInstance;
        if (itemInstance != null && itemInstance.count > this.getMaxItemCount()) {
            itemInstance.count = this.getMaxItemCount();
        }

        this.markDirty();
    }

    @Override
    public void setInventoryItem(SlotType type, int slot, ItemInstance itemInstance) {
        if (!slotTypeIndex.containsKey(type) || slot > slotTypeSizes.get(type)) return;

        inventory[slotTypeIndex.get(type) + slot] = itemInstance;
        if (itemInstance != null && itemInstance.count > this.getMaxItemCount()) {
            itemInstance.count = this.getMaxItemCount();
        }

        this.markDirty();
    }

    public String getContainerName() {
        return name;
    }

    public void readData(CompoundTag tag) {
        ListTag var2 = tag.getListTag(name);
        inventory = new ItemInstance[this.getInventorySize()];

        for (int var3 = 0; var3 < var2.size(); ++var3) {
            CompoundTag var4 = (CompoundTag) var2.get(var3);
            int var5 = var4.getByte("Slot") & 255;
            if (var5 < inventory.length) {
                inventory[var5] = new ItemInstance(var4);
            }
        }
    }

    public void writeData(CompoundTag tag) {
        ListTag var2 = new ListTag();

        for (int var3 = 0; var3 < inventory.length; ++var3) {
            if (inventory[var3] != null) {
                CompoundTag var4 = new CompoundTag();
                var4.put("Slot", (byte) var3);
                inventory[var3].toTag(var4);
                var2.add(var4);
            }
        }

        tag.put(name, var2);
    }

    public int getMaxItemCount() {
        return 64;
    }

    @Override
    public void markDirty() {

    }

    // This method is junk.
    @Override
    public boolean canPlayerUse(PlayerBase player) {
        throw new Error("ItemStorage.canPlayerUse() should never be called.");
    }
}
