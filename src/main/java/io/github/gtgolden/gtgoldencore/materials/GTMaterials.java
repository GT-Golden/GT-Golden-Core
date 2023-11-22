package io.github.gtgolden.gtgoldencore.materials;

import io.github.gtgolden.gtgoldencore.GTGoldenCore;
import io.github.gtgolden.gtgoldencore.materials.impl.MaterialRegistry;
import io.github.gtgolden.gtgoldencore.materials.impl.MaterialRegistryEvent;
import net.mine_diver.unsafeevents.event.EventPhases;
import net.mine_diver.unsafeevents.event.PhaseOrdering;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.item.tool.ToolMaterial;
import net.modificationstation.stationapi.api.StationAPI;
import net.modificationstation.stationapi.api.event.mod.PreInitEvent;
import net.modificationstation.stationapi.api.event.registry.ItemRegistryEvent;
import net.modificationstation.stationapi.api.item.tool.ToolMaterialFactory;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.util.Null;
import org.apache.logging.log4j.Logger;

public class GTMaterials {
    public static final String MATERIAL_NBT_KEY = "gt-golden-core:material";
    @Entrypoint.Logger("GT-Golden-Materials")
    public static final Logger LOGGER = Null.get();
    public static ToolMaterial MISSING_TOOL_MATERIAL;

    public GTMaterials() {
        PhaseOrdering.of(ItemRegistryEvent.class).addPhaseOrdering(EventPhases.DEFAULT_PHASE, "gt-golden-core:materials_phase");
    }

    @EventListener
    public void earlyInit(PreInitEvent event) {
        MISSING_TOOL_MATERIAL = ToolMaterialFactory.create(GTGoldenCore.NAMESPACE.id("missingMaterial").toString(),
                0,
                0,
                0,
                0);
    }

    @EventListener(phase = "gt-golden-core:materials_phase")
    public void registerItems(ItemRegistryEvent event) {
        GTGoldenCore.LOGGER.info("Registering default material items");
        MaterialRegistry.initDefaults();
        StationAPI.EVENT_BUS.post(new MaterialRegistryEvent());
    }
}
