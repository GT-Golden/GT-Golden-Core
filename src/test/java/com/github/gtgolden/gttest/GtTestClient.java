package com.github.gtgolden.gttest;

import com.github.gtgolden.gtgoldencore.materials.api.MetaItemUtils;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.modificationstation.stationapi.api.client.event.color.item.ItemColorsRegisterEvent;
import net.modificationstation.stationapi.api.client.event.texture.TextureRegisterEvent;

public class GtTestClient {
    @EventListener
    public void registerTextures(TextureRegisterEvent event) {
        GtTest.REDSTONE_PICKAXE.setTexture(GtTest.MOD_ID.id("item/redstone_pickaxe"));
    }

    @EventListener
    public void registerItemColors(ItemColorsRegisterEvent event) {
        MetaItemUtils.registerMaterialColor(event, GtTest.TEST_META_ITEM);
    }
}
