package com.github.gtgolden.gtgoldencore.events;

import com.github.gtgolden.gtgoldencore.item.MetaItem;
import com.github.gtgolden.gtgoldencore.material.GTMaterial;
import com.github.gtgolden.gtgoldencore.material.Materials;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.modificationstation.stationapi.api.event.registry.ItemRegistryEvent;

import static com.github.gtgolden.gtgoldencore.GTGoldenCore.LOGGER;
import static com.github.gtgolden.gtgoldencore.GTGoldenCore.MOD_ID;
import static com.github.gtgolden.gtgoldencore.item.MetaItem.MISSING;

public class ItemListener {
    @EventListener
    public void registerItems(ItemRegistryEvent event) {
        LOGGER.info("Registering technical stuff");
        MISSING = new MetaItem(MOD_ID.id("missing"));

        Materials.useModID(MOD_ID);

        new GTMaterial.Builder("").build();
        new GTMaterial.Builder("copper")
                .element("Cu")
                .build();
        new GTMaterial.Builder("oxygen")
                .element("O")
                .build();
        new GTMaterial.Builder("hydrogen")
                .element("H")
                .build();
        new GTMaterial.Builder("cuprous_oxide")
                .components("Cu2O")
                .build();
        new GTMaterial.Builder("copper2_hydroxide")
                .components("Cu(OH)2")
                .build();
    }
}
