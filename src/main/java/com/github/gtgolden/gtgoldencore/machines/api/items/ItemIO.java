package com.github.gtgolden.gtgoldencore.machines.api.items;

import net.minecraft.inventory.InventoryBase;
import net.minecraft.item.ItemInstance;
import uk.co.benjiweber.expressions.tuple.BiTuple;

public interface ItemIO extends InventoryBase {
    SlotType[] getAcceptedTypes();

    int getInventorySize(SlotType type);

    ItemInstance getInventoryItem(SlotType type, int slot);

    ItemInstance takeInventoryItem(SlotType type, int slot, int count);

    void setInventoryItem(SlotType type, int slot, ItemInstance itemInstance);

    default boolean hasItem(ItemInstance inputItem, SlotType... typesToCheck) {
        for (SlotType type : typesToCheck.length != 0 ? typesToCheck : SlotType.values()) {
            for (int i = 0; i < getInventorySize(type); i++) {
                var existingItem = getInventoryItem(type, i);
                if (existingItem == null) continue;
                if (existingItem.isDamageAndIDIdentical(inputItem)) {
                    return true;
                }
            }
        }
        return false;
    }

    default BiTuple<Boolean, ItemInstance> attemptSend(ItemInstance inputItem, int maxThroughput, SlotType... permittedInputTypes) {
        if (inputItem == null) return BiTuple.of(false, null);
        boolean succeeded = false;
        int amountLeft = maxThroughput;
        int maxStackSize = Math.min(getMaxItemCount(), inputItem.getMaxStackSize());
        for (SlotType type : permittedInputTypes.length != 0 ? permittedInputTypes : SlotType.values()) {
            for (int i = 0; i < getInventorySize(type); i++) {
                var existingItem = getInventoryItem(type, i);
                if (existingItem == null) continue;

                if (existingItem.isDamageAndIDIdentical(inputItem)) {
                    if (existingItem.count < maxStackSize) {
                        int increment = Math.min(amountLeft, Math.min(maxStackSize - existingItem.count, inputItem.count));
                        existingItem.count += increment;
                        amountLeft -= increment;
                        succeeded = true;
                        if (increment >= inputItem.count) {
                            return BiTuple.of(true, null);
                        } else {
                            inputItem.count -= increment;
                        }
                    }
                }
                if (amountLeft <= 0) return BiTuple.of(true, inputItem);
            }
        }
        for (SlotType type : permittedInputTypes.length != 0 ? permittedInputTypes : SlotType.values()) {
            for (int i = 0; i < getInventorySize(type); i++) {
                var existingItem = getInventoryItem(type, i);
                if (existingItem == null) {
                    int toMove = Math.min(amountLeft, inputItem.count);
                    amountLeft -= toMove;
                    if (toMove >= inputItem.count) {
                        setInventoryItem(type, i, inputItem);
                        return BiTuple.of(true, null);
                    } else {
                        var newItem = inputItem.copy();
                        newItem.count = toMove;
                        setInventoryItem(type, i, newItem);
                        inputItem.count -= toMove;
                    }
                }
                if (amountLeft <= 0) return BiTuple.of(true, inputItem);
            }
        }
        return BiTuple.of(succeeded, inputItem);
    }

    default BiTuple<Boolean, ItemInstance> attemptSend(ItemInstance inputItem, SlotType... permittedInputTypes) {
        return attemptSend(inputItem, Integer.MAX_VALUE, permittedInputTypes);
    }

    default ItemInstance attemptTake(ItemInstance desiredItem, SlotType... typesToCheck) {
        if (desiredItem == null) return null;
        var response = desiredItem.copy();
        response.count = 0;
        int maxStackSize = response.getMaxStackSize();
        slotTypeLoop:
        for (SlotType type : typesToCheck.length != 0 ? typesToCheck : SlotType.values()) {
            for (int i = 0; i < getInventorySize(type); i++) {
                var existingItem = getInventoryItem(type, i);
                if (existingItem == null) continue;

                if (existingItem.isDamageAndIDIdentical(response)) {
                    if (existingItem.count < maxStackSize) {
                        int increment = Math.min(maxStackSize - response.count, existingItem.count);

                        response.count += increment;
                        if (increment >= existingItem.count) {
                            setInventoryItem(type, i, null);
                        } else {
                            existingItem.count -= increment;
                        }
                    }
                }
                if (response.count >= maxStackSize) break slotTypeLoop;
            }
        }
        return response.count != 0 ? response : null;
    }
}
