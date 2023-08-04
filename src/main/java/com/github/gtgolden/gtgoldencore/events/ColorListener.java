package com.github.gtgolden.gtgoldencore.events;

import com.github.gtgolden.gtgoldencore.item.MetaItem;
import com.github.gtgolden.gtgoldencore.utils.ItemUtil;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.modificationstation.stationapi.api.client.color.block.BlockColors;
import net.modificationstation.stationapi.api.client.color.item.ItemColors;
import net.modificationstation.stationapi.api.client.event.color.block.BlockColorsRegisterEvent;
import net.modificationstation.stationapi.api.client.event.color.item.ItemColorsRegisterEvent;

import static com.github.gtgolden.gtgoldencore.GTGoldenCore.LOGGER;

public class ColorListener {
    @EventListener
    private static void registerBlockColours(BlockColorsRegisterEvent event) {
        LOGGER.info("Registering block colours");
        BlockColors blockColors = event.blockColors;
// TODO: all meta stuff (incl machines, blocks, tools, etc.)
//  must be contained in some sort of map container, for example: MetaItem.metaItems
    }

    @EventListener
    private static void registerItemColours(ItemColorsRegisterEvent event) {
        LOGGER.info("Registering item colours");
        ItemColors itemColors = event.itemColors;
        for (MetaItem item : MetaItem.metaItems.values()) {
            itemColors.register((itemInstance, tintIndex) -> ItemUtil.getUniqueMaterial(itemInstance).getMaterialColor(), item);
        }
    }
}
