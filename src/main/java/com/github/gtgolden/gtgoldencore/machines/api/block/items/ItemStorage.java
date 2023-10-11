package com.github.gtgolden.gtgoldencore.machines.api.block.items;

import com.github.gtgolden.gtgoldencore.machines.api.slot.GTSlot;
import com.github.gtgolden.gtgoldencore.machines.impl.HasSavableData;
import com.github.gtgolden.gtgoldencore.mixin.SlotAccessor;
import net.minecraft.entity.Item;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.item.ItemInstance;
import net.minecraft.level.Level;
import net.minecraft.util.io.CompoundTag;
import net.minecraft.util.io.ListTag;


import java.util.Random;

public class ItemStorage implements ItemIO, HasSavableData {
    private static final Random rand = new Random();
    protected GTSlot[] slots;
    private final String name;
    protected ItemInstance[] inventory;

    public ItemStorage(String name, GTSlot... slots) {
        this.name = name;

        for (int i = 0; i < slots.length; i++) {
            var slot = ((SlotAccessor) slots[i]);
            if (slot.getInventory() != null) continue;
            slot.setInvSlot(i);
            slot.setInventory(this);
        }
        this.slots = slots;

        inventory = new ItemInstance[slots.length];
    }

    @Override
    public GTSlot[] getSlots() {
        return slots;
    }

    @Override
    public ItemInstance takeInventoryItem(int slot, int count) {
        if (inventory[slot] != null) {
            ItemInstance var3;
            if (inventory[slot].count <= count) {
                var3 = inventory[slot];
                inventory[slot] = null;
                markDirty();
                return var3;
            } else {
                var3 = inventory[slot].split(count);
                if (inventory[slot].count == 0) {
                    inventory[slot] = null;
                }

                markDirty();
                return var3;
            }
        } else {
            return null;
        }
    }

    @Override
    public ItemInstance getInventoryItem(int i) {
        return inventory[i];
    }

    public void setInventoryItem(int slot, ItemInstance itemInstance) {
        inventory[slot] = itemInstance;
        if (itemInstance != null && itemInstance.count > getMaxItemCount()) {
            itemInstance.count = getMaxItemCount();
        }

        markDirty();
    }

    public String getContainerName() {
        return name;
    }

    public void dropContents(Level level, int x, int y, int z) {
        for (int i = 0; i < getInventorySize(); ++i) {
            ItemInstance itemInstance = getInventoryItem(i);
            if (itemInstance == null) continue;
            float f = rand.nextFloat() * 0.8f + 0.1f;
            float f2 = rand.nextFloat() * 0.8f + 0.1f;
            float f3 = rand.nextFloat() * 0.8f + 0.1f;
            while (itemInstance.count > 0) {
                int n = rand.nextInt(21) + 10;
                if (n > itemInstance.count) {
                    n = itemInstance.count;
                }
                itemInstance.count -= n;
                Item item = new Item(level, (float) x + f, (float) y + f2, (float) z + f3, new ItemInstance(itemInstance.itemId, n, itemInstance.getDamage()));
                float f4 = 0.05f;
                item.velocityX = (float) rand.nextGaussian() * f4;
                item.velocityY = (float) rand.nextGaussian() * f4 + 0.2f;
                item.velocityZ = (float) rand.nextGaussian() * f4;
                level.spawnEntity(item);
            }
        }
    }

    public void readData(CompoundTag tag) {
        ListTag listTag = tag.getListTag(name);
        inventory = new ItemInstance[getInventorySize()];

        for (int i = 0; i < listTag.size(); ++i) {
            CompoundTag compoundTag = (CompoundTag) listTag.get(i);
            int slot = compoundTag.getByte("Slot") & 255;
            if (slot < inventory.length) {
                inventory[slot] = new ItemInstance(compoundTag);
            }
        }
    }

    public void writeData(CompoundTag tag) {
        ListTag listTag = new ListTag();

        for (int i = 0; i < inventory.length; ++i) {
            if (inventory[i] != null) {
                CompoundTag compoundTag = new CompoundTag();
                compoundTag.put("Slot", (byte) i);
                inventory[i].toTag(compoundTag);
                listTag.add(compoundTag);
            }
        }

        tag.put(name, listTag);
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
