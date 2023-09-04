package com.github.gtgolden.gttest;

import com.github.gtgolden.gtgoldencore.materials.api.module.ColorModule;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.modificationstation.stationapi.api.client.color.item.ItemColors;
import net.modificationstation.stationapi.api.client.event.color.item.ItemColorsRegisterEvent;
import net.modificationstation.stationapi.api.client.event.texture.TextureRegisterEvent;

public class GtTestClient {
    @EventListener
    public void registerTextures(TextureRegisterEvent event) {
        GtTest.REDSTONE_PICKAXE.setTexture(GtTest.MOD_ID.id("item/redstone_pickaxe"));
    }

    @EventListener
    public void registerItemColors(ItemColorsRegisterEvent event) {
        ItemColors itemColors = event.itemColors;
        itemColors.register((itemInstance, tintIndex) -> GtTest.TEST_META_ITEM.getGTMaterial(itemInstance).map(material -> ((ColorModule) material.getModule("color").get()).getRGB()).orElse(0), GtTest.TEST_META_ITEM);
    }
}
