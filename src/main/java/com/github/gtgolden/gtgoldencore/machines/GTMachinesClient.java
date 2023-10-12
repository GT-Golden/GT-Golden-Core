package com.github.gtgolden.gtgoldencore.machines;

import com.github.gtgolden.gtgoldencore.machines.api.block.items.HasItemStorage;
import com.github.gtgolden.gtgoldencore.machines.api.block.items.ItemStorage;
import com.github.gtgolden.gtgoldencore.machines.api.gui.SimpleGTGui;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.client.gui.screen.ScreenBase;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.inventory.InventoryBase;
import net.modificationstation.stationapi.api.event.registry.GuiHandlerRegistryEvent;
import net.modificationstation.stationapi.api.registry.GuiHandlerRegistry;
import uk.co.benjiweber.expressions.tuple.BiTuple;

public class GTMachinesClient {
    @EventListener
    private void registerGuiHandlers(GuiHandlerRegistryEvent event) {
        GuiHandlerRegistry registry = event.registry;
        registry.registerValueNoMessage(SimpleGTGui.identifier, BiTuple.of(this::openSimpleGUI, ItemStorage::new));
    }

    @Environment(EnvType.CLIENT)
    public ScreenBase openSimpleGUI(PlayerBase player, InventoryBase inventoryBase) {
        return new SimpleGTGui(player.inventory, (ItemStorage) inventoryBase);
    }
}
