package com.github.gtgolden.gtgoldencore.events;

import net.mine_diver.unsafeevents.listener.EventListener;
import net.modificationstation.stationapi.api.client.event.texture.TextureRegisterEvent;
import net.modificationstation.stationapi.api.client.texture.atlas.Atlases;

import static com.github.gtgolden.gtgoldencore.GTGoldenCore.MOD_ID;

public class TextureListener {
    @EventListener
    public void registerTextures(TextureRegisterEvent event) {
        BlockListener.TEST_POWER_BLOCK.texture = Atlases.getTerrain().addTexture(MOD_ID.id("block/missing")).index;
    }
}
