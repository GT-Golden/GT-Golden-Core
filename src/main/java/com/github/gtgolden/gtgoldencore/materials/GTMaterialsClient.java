package com.github.gtgolden.gtgoldencore.materials;

import com.github.gtgolden.gtgoldencore.materials.api.MetaItem;
import com.github.gtgolden.gtgoldencore.materials.api.MaterialUtil;
import com.github.gtgolden.gtgoldencore.materials.impl.MetaItemRegistry;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.modificationstation.stationapi.api.client.color.block.BlockColors;
import net.modificationstation.stationapi.api.client.color.item.ItemColors;
import net.modificationstation.stationapi.api.client.event.color.block.BlockColorsRegisterEvent;
import net.modificationstation.stationapi.api.client.event.color.item.ItemColorsRegisterEvent;
import net.modificationstation.stationapi.api.client.event.render.model.LoadUnbakedModelEvent;

import static com.github.gtgolden.gtgoldencore.GTGoldenCore.LOGGER;

public class GTMaterialsClient {
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
        for (MetaItem item : MetaItemRegistry.getAllMetaItems().values()) {
            itemColors.register((itemInstance, tintIndex) -> MaterialUtil.getUniqueMaterial(itemInstance).getMaterialColor().getRGB(), item);
        }
    }
}
