package com.github.gtgolden.gtgoldencore;

import blue.endless.jankson.Comment;
import com.github.gtgolden.gtgoldencore.item.DebugMonitorTool;
import com.github.gtgolden.gtgoldencore.materials.api.GTMaterialBuilder;
import com.github.gtgolden.gtgoldencore.materials.api.MetaItem;
import net.glasslauncher.mods.api.gcapi.api.ConfigName;
import net.glasslauncher.mods.api.gcapi.api.GConfig;
import net.glasslauncher.mods.api.gcapi.api.MultiplayerSynced;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.item.ItemBase;
import net.modificationstation.stationapi.api.event.mod.InitEvent;
import net.modificationstation.stationapi.api.event.registry.ItemRegistryEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.registry.ModID;
import net.modificationstation.stationapi.api.util.Null;
import org.apache.logging.log4j.Logger;

public class GTGoldenCore {
    @Entrypoint.ModID
    public static final ModID MOD_ID = Null.get();
    @Entrypoint.Logger("GT-Golden-Core")
    public static final Logger LOGGER = Null.get();

    @GConfig(value = "config", visibleName = "GregTech Golden Core Config")
    public static ConfigFields config = new ConfigFields();

    @EventListener
    public void init(InitEvent event) {
        LOGGER.info("Preparing sauce of golden ages...");
    }

    public static ItemBase DEBUG_MONITOR;

    @EventListener
    public void registerItems(ItemRegistryEvent event) {
        LOGGER.info("Registering technical stuff");

        if (config.debugMonitorEnabled) {
            LOGGER.info("Registering debug monitor");
            DEBUG_MONITOR = new DebugMonitorTool(MOD_ID.id("debugMonitor"));
        }
    }

    public static class ConfigFields {
        @MultiplayerSynced
        @ConfigName("Debug Monitor Enabled")
        @Comment("You must restart after applying this.")
        public Boolean debugMonitorEnabled = false;
    }
}
