package com.github.gtgolden.gttest;

import com.github.gtgolden.gtgoldencore.machines.api.item.ItemInventoryBase;
import com.github.gtgolden.gtgoldencore.materials.api.MetaItemUtils;
import com.github.gtgolden.gttest.gui.GuiPowerTool;
import com.github.gtgolden.gttest.item.TestPowerTool;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.client.gui.screen.ScreenBase;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.inventory.InventoryBase;
import net.modificationstation.stationapi.api.client.event.color.item.ItemColorsRegisterEvent;
import net.modificationstation.stationapi.api.client.event.render.model.ItemModelPredicateProviderRegistryEvent;
import net.modificationstation.stationapi.api.client.event.texture.TextureRegisterEvent;
import net.modificationstation.stationapi.api.event.registry.GuiHandlerRegistryEvent;
import uk.co.benjiweber.expressions.tuple.BiTuple;

public class GtTestClient {
    @EventListener
    public void registerTextures(TextureRegisterEvent event) {
        GtTest.REDSTONE_PICKAXE.setTexture(GtTest.MOD_ID.id("item/redstone_pickaxe"));
    }

    @EventListener
    public void registerItemColors(ItemColorsRegisterEvent event) {
        MetaItemUtils.registerMaterialColor(event, GtTest.TEST_META_ITEM);
        MetaItemUtils.registerMaterialColor(event, GtTest.TEST_POWER_TOOL, 0);
    }

    @EventListener
    public void registerItemModelPredicates(ItemModelPredicateProviderRegistryEvent event) {
        event.registry.register(GtTest.TEST_POWER_TOOL, GtTest.MOD_ID.id("tool"),
                (itemInstance, world, entity, seed) ->
                        TestPowerTool.getSelectedTool(itemInstance).equals("drill") ? 0 : 1);
    }

    @Environment(EnvType.CLIENT)
    @EventListener
    public void registerGuiHandler(GuiHandlerRegistryEvent event) {
        event.registry.registerValueNoMessage(GtTest.MOD_ID.id("openPowerTool"), BiTuple.of(this::openPowerTool, ItemInventoryBase::new));
    }

    @Environment(EnvType.CLIENT)
    public ScreenBase openPowerTool(PlayerBase player, InventoryBase inventoryBase) {
        return new GuiPowerTool(player.inventory, (ItemInventoryBase) inventoryBase);
    }
}
