package io.github.gtgolden.gttest.container;

import io.github.gtgolden.gtgoldencore.machines.api.item.ItemInventoryBase;
import net.minecraft.container.ContainerBase;
import net.minecraft.container.slot.Slot;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.entity.player.PlayerInventory;

public class ContainerPowerTool extends ContainerBase {
    private final ItemInventoryBase powerToolInventory;

    public ContainerPowerTool(PlayerInventory playerInventory, ItemInventoryBase powerToolInventory) {
        this.powerToolInventory = powerToolInventory;

        addSlot(new Slot(powerToolInventory, 0, 42, 35));
        addSlot(new Slot(powerToolInventory, 1, 80, 35));
        addSlot(new Slot(powerToolInventory, 2, 118, 35));

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
    public boolean canUse(PlayerBase player) {
        return powerToolInventory.canPlayerUse(player);
    }
}
