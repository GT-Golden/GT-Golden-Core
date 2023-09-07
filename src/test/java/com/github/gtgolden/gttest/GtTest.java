package com.github.gtgolden.gttest;

import com.github.gtgolden.gtgoldencore.materials.api.module.ItemFormsModule;
import com.github.gtgolden.gtgoldencore.materials.api.module.ToolMaterialModule;
import com.github.gtgolden.gtgoldencore.materials.impl.MaterialRegistry;
import com.github.gtgolden.gtgoldencore.materials.impl.MaterialRegistryEvent;
import com.github.gtgolden.gttest.block.*;
import com.github.gtgolden.gttest.item.RedstonePickaxe;
import com.github.gtgolden.gttest.item.TestMetaItem;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.block.BlockBase;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBase;
import net.minecraft.item.ItemInstance;
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

    public static BlockBase GENERATOR;
    public static BlockBase APPLE_SPAWNER;
    public static BlockBase COBBLE_GEN;
    public static BlockBase ITEM_MOVER;

    @EventListener
    public void registerBlocks(BlockRegistryEvent event) {
        LOGGER.info("Registering blocks");
        GENERATOR = new Generator(MOD_ID.id("generator"), Material.STONE);
        APPLE_SPAWNER = new AppleSpawner(MOD_ID.id("apple_spawner"), Material.STONE);
        COBBLE_GEN = new CobbleGenerator(MOD_ID.id("cobble_generator"), Material.STONE);
        ITEM_MOVER = new ItemMover(MOD_ID.id("item_mover"), Material.STONE);
    }

    @EventListener
    public void registerTileEntities(TileEntityRegisterEvent event) {
        LOGGER.info("Registering tile entities");
        event.register(GeneratorEntity.class, MOD_ID.id("generator").toString());
        event.register(AppleSpawnerEntity.class, MOD_ID.id("apple_spawner").toString());
        event.register(CobbleGeneratorEntity.class, MOD_ID.id("cobble_generator").toString());
        event.register(ItemMoverEntity.class, MOD_ID.id("item_mover").toString());
    }

    public static ItemBase REDSTONE_PICKAXE;
    public static TestMetaItem TEST_META_ITEM;

    @EventListener
    public void registerItems(ItemRegistryEvent event) {
        LOGGER.info("Registering items");
        REDSTONE_PICKAXE = new RedstonePickaxe(MOD_ID.id("redstone_pickaxe"));
        TEST_META_ITEM = new TestMetaItem(MOD_ID.id("test_meta_item"));
    }

    @EventListener
    public void registerMaterials(MaterialRegistryEvent event) {
        LOGGER.info("Registering materials");
//        event.registerModules("dirt", new FormsModule("block", new ItemInstance(BlockBase.DIRT)));
//        var redstoneMaterial = ;
        event.registerModules(
                "redstone",
                new ToolMaterialModule("redstone", 3, 4, 14.0F, 0),
                new ItemFormsModule("pickaxe", new ItemInstance(REDSTONE_PICKAXE))
        );
        MaterialRegistry.getMaterial("redstone").get().registerTranslationProvider(MOD_ID);
    }
}
