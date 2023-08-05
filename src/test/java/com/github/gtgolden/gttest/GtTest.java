package com.github.gtgolden.gttest;

import com.github.gtgolden.gtgoldencore.materials.api.MetaItem;
import com.github.gtgolden.gttest.block.*;
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
import org.apache.logging.log4j.Logger;

public class GtTest {
    @Entrypoint.ModID
    public static final ModID MOD_ID = Null.get();

    @Entrypoint.Logger("GT-TEST")
    public static final Logger LOGGER = Null.get();

    @EventListener
    public void registerItems(ItemRegistryEvent event) {
        LOGGER.info("Registering items");
        new MetaItem(MOD_ID.id("testMetaItem"));
    }

    public static BlockBase GENERATOR;
    public static BlockBase APPLE_SPAWNER;
    public static BlockBase COBBLE_GEN;
    public static BlockBase ITEM_MOVER;

    @EventListener
    public void registerBlocks(BlockRegistryEvent event) {
        LOGGER.info("Registering blocks");
        GENERATOR = new Generator(MOD_ID.id("generator"), Material.STONE);
        APPLE_SPAWNER = new AppleSpawner(MOD_ID.id("appleSpawner"), Material.STONE);
        COBBLE_GEN = new CobbleGenerator(MOD_ID.id("cobbleGenerator"), Material.STONE);
        ITEM_MOVER = new ItemMover(MOD_ID.id("itemMover"), Material.STONE);
    }

    @EventListener
    public void registerTileEntities(TileEntityRegisterEvent event) {
        LOGGER.info("Registering tile entities");
        event.register(GeneratorEntity.class, MOD_ID.id("generator").toString());
        event.register(AppleSpawnerEntity.class, MOD_ID.id("appleSpawner").toString());
        event.register(CobbleGeneratorEntity.class, MOD_ID.id("cobbleGenerator").toString());
        event.register(ItemMoverEntity.class, MOD_ID.id("itemMover").toString());
    }
}
