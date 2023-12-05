package io.github.gtgolden.gttest;

import io.github.gtgolden.gtgoldencore.materials.api.module.ColorModule;
import io.github.gtgolden.gtgoldencore.materials.api.module.ItemFormsModule;
import io.github.gtgolden.gtgoldencore.materials.api.module.ToolMaterialModule;
import io.github.gtgolden.gtgoldencore.materials.impl.MaterialRegistryEvent;
import io.github.gtgolden.gttest.block.*;
import io.github.gtgolden.gttest.item.Battery;
import io.github.gtgolden.gttest.item.RedstonePickaxe;
import io.github.gtgolden.gttest.item.TestMetaItem;
import io.github.gtgolden.gttest.item.TestPowerTool;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.block.BlockBase;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBase;
import net.minecraft.item.ItemInstance;
import net.modificationstation.stationapi.api.event.block.entity.BlockEntityRegisterEvent;
import net.modificationstation.stationapi.api.event.registry.BlockRegistryEvent;
import net.modificationstation.stationapi.api.event.registry.ItemRegistryEvent;
import net.modificationstation.stationapi.api.mod.entrypoint.Entrypoint;
import net.modificationstation.stationapi.api.util.Namespace;
import net.modificationstation.stationapi.api.util.Null;
import org.apache.logging.log4j.Logger;

import java.awt.*;

public class GtTest {
    @Entrypoint.Namespace
    public static final Namespace NAMESPACE = Null.get();

    @Entrypoint.Logger("GT-TEST")
    public static final Logger LOGGER = Null.get();

    public static BlockBase generator;
    public static BlockBase appleSpawner;
    public static BlockBase cobbleGen;
    public static BlockBase itemMover;
    public static BlockBase storageBlock;

    @EventListener
    public void registerBlocks(BlockRegistryEvent event) {
        LOGGER.info("Registering blocks");
        generator = new Generator(NAMESPACE.id("generator"), Material.STONE);
        appleSpawner = new AppleSpawner(NAMESPACE.id("apple_spawner"), Material.STONE);
        cobbleGen = new CobbleGenerator(NAMESPACE.id("cobble_generator"), Material.STONE);
        itemMover = new ItemMover(NAMESPACE.id("item_mover"), Material.STONE);
        storageBlock = new StorageBlock(NAMESPACE.id("storage_block"), Material.METAL);
    }

    @EventListener
    public void registerTileEntities(BlockEntityRegisterEvent event) {
        LOGGER.info("Registering tile entities");
        event.register(GeneratorEntity.class, NAMESPACE.id("generator").toString());
        event.register(AppleSpawnerEntity.class, NAMESPACE.id("apple_spawner").toString());
        event.register(CobbleGeneratorEntity.class, NAMESPACE.id("cobble_generator").toString());
        event.register(ItemMoverEntity.class, NAMESPACE.id("item_mover").toString());
        event.register(StorageEntity.class, NAMESPACE.id("storage_block").toString());
    }

    public static ItemBase redstonePickaxe;
    public static TestMetaItem testMetaItem;
    public static ItemBase testPowerTool;
    public static ItemBase battery;

    @EventListener
    public void registerItems(ItemRegistryEvent event) {
        LOGGER.info("Registering items");
        redstonePickaxe = new RedstonePickaxe(NAMESPACE.id("redstone_pickaxe"));
        testMetaItem = new TestMetaItem(NAMESPACE.id("test_meta_item"));
        testPowerTool = new TestPowerTool(NAMESPACE.id("test_power_tool"));
        battery = new Battery(NAMESPACE.id("battery"));
    }

    @EventListener
    public void registerMaterials(MaterialRegistryEvent event) {
        LOGGER.info("Registering materials");
        event.registerModules("dirt", new ColorModule(new Color(0x332008)));
        event.registerTranslationProvider("dirt", GtTest.NAMESPACE);
        event.registerModules(
                "redstone",
                new ToolMaterialModule("redstone", 3, 4, 14.0F, 0),
                new ItemFormsModule("pickaxe", new ItemInstance(redstonePickaxe))
        );
        event.registerTranslationProvider("redstone", NAMESPACE);
    }
}
