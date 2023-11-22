package io.github.gtgolden.gtgoldencore.machines.api.container;

import io.github.gtgolden.gtgoldencore.machines.api.block.items.ItemStorage;
import net.minecraft.container.ContainerBase;
import net.minecraft.container.slot.Slot;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.entity.player.PlayerInventory;

public class SimpleGTContainer extends ContainerBase {
    public SimpleGTContainer(PlayerInventory playerInventory, ItemStorage itemStorage) {
        for (Slot slot : itemStorage.getSlots()) {
            addSlot(slot);
        }

        // Player Inventory

        for (int row = 0; row < 3; ++row) {
            for (int column = 0; column < 9; ++column) {
                var slotNum = column + row * 9 + 9;
                this.addSlot(new Slot(playerInventory, slotNum, 8 + column * 18, 84 + row * 18));
            }
        }

        for (int column = 0; column < 9; ++column) {
            this.addSlot(new Slot(playerInventory, column, 8 + column * 18, 142));
        }
    }

    @Override
    public boolean canUse(PlayerBase arg) {
        return true;
    }
}
