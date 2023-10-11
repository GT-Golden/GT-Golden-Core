package com.github.gtgolden.gtgoldencore.machines.api.slot;

import net.minecraft.container.slot.Slot;
import net.minecraft.inventory.InventoryBase;
import net.minecraft.item.ItemInstance;

import java.util.Optional;

public class GTSlot extends Slot {
    String label;

    public GTSlot(String label, InventoryBase arg, int i, int j, int k) {
        super(arg, i, j, k);
        this.label = label;
    }
    public GTSlot(InventoryBase arg, int i, int j, int k) {
        super(arg, i, j, k);
    }

    public GTSlot(int x, int y) {
        super(null, -1, x, y);
    }

    public GTSlot() {
        super(null, -1, 0, 0);
    }

    public Optional<String> getLabel() {
        return Optional.ofNullable(label);
    }

    public boolean canTake() {
        return canTake(getItem());
    }

    public boolean canTake(ItemInstance inputItem) {
        return true;
    }

    public ItemInstance attemptSendItem(ItemInstance inputItem) {
        return attemptSendItem(inputItem, inputItem.count);
    }

    public ItemInstance attemptSendItem(ItemInstance inputItem, int maxSend) {
        if (!canInsert(inputItem)) return inputItem;

        var existingItem = getItem();
        if (existingItem == null) {
            if (inputItem.count > maxSend) {
                var transferred = inputItem.copy();
                transferred.count = maxSend;
                setStack(transferred);
                inputItem.count -= maxSend;
                return inputItem;
            } else {
                setStack(inputItem);
                return null;
            }
        }

        if (existingItem.isDamageAndIDIdentical(inputItem)) {
            if (existingItem.count < getMaxStackCount()) {
                int increment = Math.min(maxSend, Math.min(getMaxStackCount() - existingItem.count, inputItem.count));
                existingItem.count += increment;
                if (increment >= inputItem.count) {
                    return null;
                } else {
                    inputItem.count -= increment;
                }
            }
        }

        return inputItem;
    }
}
