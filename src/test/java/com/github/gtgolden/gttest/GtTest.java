package com.github.gtgolden.gttest;

import com.github.gtgolden.gttest.block.AppleSpawner;
import com.github.gtgolden.gttest.block.AppleSpawnerEntity;
import com.github.gtgolden.gttest.block.Generator;
import com.github.gtgolden.gttest.block.GeneratorEntity;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.block.BlockBase;
import net.minecraft.block.material.Material;
import net.modificationstation.stationapi.api.event.registry.BlockRegistryEvent;
import net.modificationstation.stationapi.api.event.tileentity.TileEntityRegisterEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.registry.ModID;
import net.modificationstation.stationapi.api.util.Null;
import org.apache.logging.log4j.Logger;

public class GtTest {
    @Entrypoint.ModID
    public static final ModID MOD_ID = Null.get();

    public static BlockBase GENERATOR;
    public static BlockBase APPLE_SPAWNER;
    @EventListener
    public void registerBlocks(BlockRegistryEvent event) {
        GENERATOR = new Generator(MOD_ID.id("generator"), Material.STONE);
        APPLE_SPAWNER = new AppleSpawner(MOD_ID.id("appleSpawner"), Material.STONE);
    }

    @EventListener
    public void registerTileEntities(TileEntityRegisterEvent event) {
        event.register(GeneratorEntity.class, MOD_ID.id("generator").toString());
        event.register(AppleSpawnerEntity.class, MOD_ID.id("appleSpawner").toString());
    }
}
