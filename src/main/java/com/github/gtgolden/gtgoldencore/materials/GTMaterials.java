package com.github.gtgolden.gtgoldencore.materials;

import com.github.gtgolden.gtgoldencore.GTGoldenCore;
import com.github.gtgolden.gtgoldencore.materials.api.MetaItem;
import com.github.gtgolden.gtgoldencore.materials.impl.GTMaterialRegistry;
import com.github.gtgolden.gtgoldencore.materials.impl.GTMaterialRegistryEvent;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.modificationstation.stationapi.api.StationAPI;
import net.modificationstation.stationapi.api.event.mod.PostInitEvent;
import net.modificationstation.stationapi.api.event.registry.ItemRegistryEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.util.Null;
import org.apache.logging.log4j.Logger;

public class GTMaterials {
    @Entrypoint.Logger("GT-Golden-Materials")
    public static final Logger LOGGER = Null.get();

    @EventListener
    public void registerItems(ItemRegistryEvent event) {
        GTGoldenCore.LOGGER.info("Registering material items");
        new MetaItem(GTGoldenCore.MOD_ID.id("missing"));
    }

    @EventListener
    public void addEvents(PostInitEvent event) {
        StationAPI.EVENT_BUS.post(new GTMaterialRegistryEvent());
    }

    @EventListener
    public void registerMaterials(GTMaterialRegistryEvent event) {
        GTMaterialRegistry.register(GTMaterialRegistry.MISSING_MATERIAL);
    }
}
