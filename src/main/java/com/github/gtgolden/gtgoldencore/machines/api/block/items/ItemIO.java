package com.github.gtgolden.gtgoldencore.machines.api.block.items;

import com.github.gtgolden.gtgoldencore.machines.api.slot.GTSlot;
import com.github.gtgolden.gtgoldencore.machines.impl.ItemRetrievalResult;
import net.minecraft.inventory.InventoryBase;
import net.minecraft.item.ItemInstance;
import net.modificationstation.stationapi.api.util.math.Direction;

import java.util.Optional;

public interface ItemIO extends InventoryBase {
    GTSlot[] getSlots();

    default GTSlot getSlot(int i) {
        return getSlots()[i];
    }

    default Optional<GTSlot> getSlot(String label) {
        for (GTSlot slot : getSlots()) {
            if (slot.getLabel().isPresent()) {
                if (slot.getLabel().get().equals(label)) return Optional.of(slot);
            }
        }

        return Optional.empty();
    }
    @Override
    default int getInventorySize() {
        return getSlots().length;
    }

    default ItemRetrievalResult getInventoryItem(String label) {
        return getSlot(label)
                .map(
                        slot -> new ItemRetrievalResult(true, slot.getItem()))
                .orElseGet(
                        () -> new ItemRetrievalResult(false, null)
                );
    }

    default boolean setInventoryItem(String label, ItemInstance arg) {
        var slot = getSlot(label);
        if (slot.isEmpty()) return false;
        slot.get().setStack(arg);
        return true;
    }

    default ItemInstance attemptSendItem(Direction side, ItemInstance inputItem) {
        if (inputItem == null) return null;
        return attemptSendItem(side, inputItem, inputItem.count);
    }

    default ItemInstance attemptSendItem(Direction side, ItemInstance inputItem, int maxThroughput) {
        return attemptSendItem(inputItem, maxThroughput);
    }

    default ItemInstance attemptSendItem(ItemInstance inputItem) {
        if (inputItem == null) return null;
        return attemptSendItem(inputItem, inputItem.count);
    }

    default ItemInstance attemptSendItem(ItemInstance itemInstance, int maxThroughput) {
        if (itemInstance == null) return null;
        int startingCount = itemInstance.count;
        int alreadySent = 0;
        ItemInstance currentItem = null;
        for (GTSlot slot : getSlots()) {
            currentItem = slot.attemptSendItem(itemInstance, maxThroughput - alreadySent);

            if (currentItem != null) {
                alreadySent += startingCount - currentItem.count;
            }
            if (currentItem == null || alreadySent >= maxThroughput) break;
        }
        return currentItem;
    }

    default ItemInstance attemptTake(ItemInstance desiredItem) {
        if (desiredItem == null) return null;
        var response = desiredItem.copy();
        response.count = 0;
        int maxCount = Math.min(response.getMaxStackSize(), desiredItem.count);
        for (GTSlot slot : getSlots()) {
            if (!slot.canMachineTake()) continue;
            var existingItem = slot.getItem();
            if (existingItem == null) continue;

            if (existingItem.isDamageAndIDIdentical(response)) {
                if (existingItem.count < maxCount) {
                    int increment = Math.min(maxCount - response.count, existingItem.count);

                    response.count += increment;
                    if (increment >= existingItem.count) {
                        slot.setStack(null);
                    } else {
                        existingItem.count -= increment;
                    }
                }
            }

            if (response.count >= maxCount) break;
        }
        return response.count != 0 ? response : null;
    }
}
