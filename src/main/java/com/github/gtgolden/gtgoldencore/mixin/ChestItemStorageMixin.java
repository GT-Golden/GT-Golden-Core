package com.github.gtgolden.gtgoldencore.mixin;

import com.github.gtgolden.gtgoldencore.machines.HasItemIO;
import com.github.gtgolden.gtgoldencore.machines.SlotType;
import net.minecraft.item.ItemInstance;
import net.minecraft.tileentity.TileEntityChest;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(TileEntityChest.class)
public abstract class ChestItemStorageMixin implements HasItemIO {
    @Shadow public abstract int getInventorySize();

    @Shadow public abstract ItemInstance getInventoryItem(int i);

    @Shadow public abstract ItemInstance takeInventoryItem(int i, int j);

    @Shadow public abstract void setInventoryItem(int i, ItemInstance arg);

    @Override
    public int getInventorySize(SlotType type) {
        if (type != SlotType.MIXED) {
            return 0;
        } else {
            return getInventorySize();
        }
    }

    @Override
    public ItemInstance getInventoryItem(SlotType type, int slot) {
        if (type != SlotType.MIXED) {
            return null;
        } else {
            return getInventoryItem(slot);
        }
    }

    @Override
    public ItemInstance takeInventoryItem(SlotType type, int slot, int count) {
        if (type != SlotType.MIXED) {
            return null;
        } else {
            return takeInventoryItem(type, slot, count);
        }
    }

    @Override
    public void setInventoryItem(SlotType type, int slot, ItemInstance itemInstance) {
        if (type == SlotType.MIXED) {
            setInventoryItem(slot, itemInstance);
        }
    }
}
