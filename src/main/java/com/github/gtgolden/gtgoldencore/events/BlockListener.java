package com.github.gtgolden.gtgoldencore.events;

import com.github.gtgolden.gtgoldencore.machines.ChargerBlock;
import com.github.gtgolden.gtgoldencore.machines.TestPowerBlock;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.block.BlockBase;
import net.minecraft.block.material.Material;
import net.modificationstation.stationapi.api.event.registry.BlockRegistryEvent;

import static com.github.gtgolden.gtgoldencore.GTGoldenCore.MOD_ID;

public class BlockListener {
    public static BlockBase TEST_POWER_BLOCK;
    public static BlockBase CHARGER_BLOCK;
    @EventListener
    public void registerBlocks(BlockRegistryEvent event) {
        TEST_POWER_BLOCK = new TestPowerBlock(MOD_ID.id("testPowerBlock"), Material.DIRT);
        CHARGER_BLOCK = new ChargerBlock(MOD_ID.id("chargerBlock"), Material.DIRT);
    }
}
