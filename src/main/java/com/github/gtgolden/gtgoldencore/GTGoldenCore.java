package com.github.gtgolden.gtgoldencore;

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

    public static class ConfigFields {
        @MultiplayerSynced
        @ConfigName("Show GT: Golden Info in the F3 menu.")
        public Boolean debugMenuInfo = true;
    }
}
