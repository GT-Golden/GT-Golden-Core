package io.github.gtgolden.gtgoldencore.materials.api;

import io.github.gtgolden.gtgoldencore.GTGoldenCore;
import io.github.gtgolden.gtgoldencore.materials.api.module.ColorModule;
import net.minecraft.item.ItemBase;
import net.minecraft.item.ItemInstance;
import net.modificationstation.stationapi.api.client.event.color.item.ItemColorsRegisterEvent;

public class MetaItemUtils {
    public static void registerMaterialColor(ItemColorsRegisterEvent event, ItemBase item) {
        if (!(item instanceof HasGTMaterial)) {
            GTGoldenCore.LOGGER.error("Attempted to register material color for " + item.getTranslationKey() + " despite not implementing HasGTMaterial");
            return;
        }
        event.itemColors.register((itemInstance, tintIndex) -> getMaterialColor((HasGTMaterial) item, itemInstance, tintIndex), item);
    }

    public static void registerMaterialColor(ItemColorsRegisterEvent event, ItemBase item, int... layers) {
        if (!(item instanceof HasGTMaterial)) {
            GTGoldenCore.LOGGER.error("Attempted to register material color for " + item.getTranslationKey() + " despite not implementing HasGTMaterial");
            return;
        }

        event.itemColors.register((itemInstance, tintIndex) -> {
            for (int layer : layers) {
                if (layer == tintIndex) {
                    return getMaterialColor((HasGTMaterial) item, itemInstance, tintIndex);
                }
            }
            return 0xFFFFFF;
        }, item);
    }

    private static int getMaterialColor(HasGTMaterial item, ItemInstance itemInstance, int tintIndex) {
        var material = item.getGTMaterial(itemInstance);
        var module = material != null ? material.getModule(ColorModule.class) : null;
        if (module == null) {
            return ColorModule.defaultModule.getColor().getRGB();
        } else {
            return module.getColor(tintIndex).getRGB();
        }
    }
}
