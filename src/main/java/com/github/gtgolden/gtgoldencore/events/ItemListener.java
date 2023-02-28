package com.github.gtgolden.gtgoldencore.events;

import com.github.gtgolden.gtgoldencore.item.MetaItem;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.modificationstation.stationapi.api.event.registry.ItemRegistryEvent;

import static com.github.gtgolden.gtgoldencore.GTGoldenCore.MOD_ID;
import static com.github.gtgolden.gtgoldencore.item.MetaItem.NULL;

public class ItemListener {
    @EventListener
    public void registerItems(ItemRegistryEvent event) {
        NULL = new MetaItem(MOD_ID.id("NULL"));
    }
}
