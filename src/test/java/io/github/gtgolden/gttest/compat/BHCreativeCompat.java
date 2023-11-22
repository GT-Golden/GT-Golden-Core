package io.github.gtgolden.gttest.compat;

import io.github.gtgolden.gttest.GtTest;
import net.mine_diver.unsafeevents.listener.EventListener;
import net.minecraft.item.ItemInstance;
import paulevs.bhcreative.api.CreativeTab;
import paulevs.bhcreative.api.SimpleTab;
import paulevs.bhcreative.registry.TabRegistryEvent;

public class BHCreativeCompat {
    private static CreativeTab tab;

    @EventListener
    private void registerTabs(TabRegistryEvent event) {
        tab = new SimpleTab(GtTest.NAMESPACE.id("items"), new ItemInstance(GtTest.appleSpawner));
        event.register(tab);
        // Blocks
        tab.addItem(new ItemInstance(GtTest.generator));
        tab.addItem(new ItemInstance(GtTest.appleSpawner));
        tab.addItem(new ItemInstance(GtTest.cobbleGen));
        tab.addItem(new ItemInstance(GtTest.itemMover));
        tab.addItem(new ItemInstance(GtTest.storageBlock));
        // Items
        tab.addItem(new ItemInstance(GtTest.redstonePickaxe));
        tab.addItem(new ItemInstance(GtTest.testMetaItem));
        tab.addItem(new ItemInstance(GtTest.testPowerTool));
        tab.addItem(new ItemInstance(GtTest.battery));
    }
}
