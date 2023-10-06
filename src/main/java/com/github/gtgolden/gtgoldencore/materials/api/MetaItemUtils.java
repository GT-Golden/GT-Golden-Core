package com.github.gtgolden.gtgoldencore.materials.api;

import com.github.gtgolden.gtgoldencore.GTGoldenCore;
import com.github.gtgolden.gtgoldencore.materials.api.module.ColorModule;
import net.minecraft.item.ItemBase;
import net.minecraft.item.ItemInstance;
import net.modificationstation.stationapi.api.client.color.item.ItemColorProvider;
import net.modificationstation.stationapi.api.client.event.color.item.ItemColorsRegisterEvent;

public class MetaItemUtils {
    public static void registerMaterialColor(ItemColorsRegisterEvent event, ItemBase item) {
        if (!(item instanceof HasGTMaterial)) {
            GTGoldenCore.LOGGER.error("Attempted to register material color for " + item.getTranslationKey() + " despite not implementing HasGTMaterial");
            return;
        }
        event.itemColors.register((itemInstance, tintIndex) ->
                        (
                                ((HasGTMaterial) item)
                                        .getGTMaterial(itemInstance)
                                        .flatMap(material -> material.getModule(ColorModule.class))
                                        .orElse(ColorModule.defaultModule))
                                .getColor(tintIndex)
                                .getRGB()
                , item);
    }

    public static void registerMaterialColor(ItemColorsRegisterEvent event, ItemBase item, int... layers) {
        if (!(item instanceof HasGTMaterial)) {
            GTGoldenCore.LOGGER.error("Attempted to register material color for " + item.getTranslationKey() + " despite not implementing HasGTMaterial");
            return;
        }

        event.itemColors.register((itemInstance, tintIndex) -> {
            for (int i = 0; i < layers.length; i++) {
                if (layers[i] == tintIndex) {
                    return (((HasGTMaterial) item)
                            .getGTMaterial(itemInstance)
                            .flatMap(material -> material.getModule(ColorModule.class))
                            .orElse(ColorModule.defaultModule)).getColor(tintIndex).getRGB();
                }
            }
            return 0xFFFFFF;
        }, item);

    }
}
