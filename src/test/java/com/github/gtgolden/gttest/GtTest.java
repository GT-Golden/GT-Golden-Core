package com.github.gtgolden.gttest;

import com.github.gtgolden.gttest.block.*;
import com.github.gtgolden.gttest.item.DebugMonitorTool;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.block.BlockBase;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBase;
import net.modificationstation.stationapi.api.event.registry.BlockRegistryEvent;
import net.modificationstation.stationapi.api.event.registry.ItemRegistryEvent;
import net.modificationstation.stationapi.api.event.tileentity.TileEntityRegisterEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.registry.ModID;
import net.modificationstation.stationapi.api.util.Null;

public class GtTest {
    @Entrypoint.ModID
    public static final ModID MOD_ID = Null.get();

    public static ItemBase DEBUG_MONITOR;
    @EventListener
    public void registerItems(ItemRegistryEvent event) {
        DEBUG_MONITOR = new DebugMonitorTool(MOD_ID.id("debugMonitor"));
    }

    public static BlockBase GENERATOR;
    public static BlockBase APPLE_SPAWNER;
    public static BlockBase COBBLE_GEN;
    @EventListener
    public void registerBlocks(BlockRegistryEvent event) {
        GENERATOR = new Generator(MOD_ID.id("generator"), Material.STONE);
        APPLE_SPAWNER = new AppleSpawner(MOD_ID.id("appleSpawner"), Material.STONE);
        COBBLE_GEN = new CobbleGenerator(MOD_ID.id("cobbleGenerator"), Material.STONE);
    }

    @EventListener
    public void registerTileEntities(TileEntityRegisterEvent event) {
        event.register(GeneratorEntity.class, MOD_ID.id("generator").toString());
        event.register(AppleSpawnerEntity.class, MOD_ID.id("appleSpawner").toString());
        event.register(CobbleGeneratorEntity.class, MOD_ID.id("cobbleGenerator").toString());
    }
}
