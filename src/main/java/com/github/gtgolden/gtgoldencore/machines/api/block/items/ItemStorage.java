package com.github.gtgolden.gtgoldencore.machines.api.block.items;

import com.github.gtgolden.gtgoldencore.machines.impl.HasSavableData;
import net.minecraft.entity.Item;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.item.ItemInstance;
import net.minecraft.level.Level;
import net.minecraft.util.io.CompoundTag;
import net.minecraft.util.io.ListTag;
import uk.co.benjiweber.expressions.tuple.BiTuple;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.Random;

public class ItemStorage implements ItemIO, HasSavableData {
    private static final Random rand = new Random();
    protected final EnumMap<SlotType, Integer> slotTypeSizes = new EnumMap<>(SlotType.class);
    protected final EnumMap<SlotType, Integer> slotTypeIndex = new EnumMap<>(SlotType.class);
    protected final SlotType[] acceptedTypes;
    private final String name;
    protected ItemInstance[] inventory;

    public ItemStorage(String name, int size) {
        inventory = new ItemInstance[size];
        this.name = name;
        for (SlotType type :
                SlotType.values()) {
            slotTypeSizes.put(type, type == SlotType.MISC ? size : 0);
        }
        slotTypeIndex.put(SlotType.MISC, 0);
        acceptedTypes = new SlotType[]{SlotType.MISC};
    }

    public ItemStorage(String name, BiTuple<SlotType, Integer>... storageTypes) {
        this.name = name;
        var size = 0;
        var acceptedTypesList = new ArrayList<SlotType>(storageTypes.length);
        for (int i = 0; i < storageTypes.length; i++) {
            BiTuple<SlotType, Integer> storageType = storageTypes[i];
            slotTypeIndex.put(storageType.one(), size);
            slotTypeSizes.put(storageType.one(), storageType.two());
            size += storageType.two();
            acceptedTypesList.add(storageType.one());
        }
        inventory = new ItemInstance[size];
        acceptedTypes = acceptedTypesList.toArray(new SlotType[storageTypes.length]);
    }

    @Override
    public SlotType[] getAcceptedTypes() {
        return acceptedTypes;
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

    public ItemInstance takeInventoryItem(SlotType type, int slot, int count) {
        if (!slotTypeIndex.containsKey(type) || slot > slotTypeSizes.get(type)) return null;
        return inventory[slotTypeIndex.get(type) + slot];
    }

    public void setInventoryItem(int slot, ItemInstance itemInstance) {
        inventory[slot] = itemInstance;
        if (itemInstance != null && itemInstance.count > getMaxItemCount()) {
            itemInstance.count = getMaxItemCount();
        }

        markDirty();
    }

    @Override
    public void setInventoryItem(SlotType type, int slot, ItemInstance itemInstance) {
        if (!slotTypeIndex.containsKey(type) || slot > slotTypeSizes.get(type)) return;

        inventory[slotTypeIndex.get(type) + slot] = itemInstance;
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
