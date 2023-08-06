package com.github.gtgolden.gttest.item;

import com.github.gtgolden.gtgoldencore.materials.api.MetaItem;
import com.github.gtgolden.gtgoldencore.materials.impl.GTMaterialRegistry;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.item.ItemInstance;
import net.minecraft.level.Level;
import net.modificationstation.stationapi.api.registry.Identifier;

public class TestMetaItem extends MetaItem {
    public TestMetaItem(Identifier identifier) {
        super(identifier);
    }

    @Override
    public ItemInstance use(ItemInstance item, Level level, PlayerBase player) {
        if (getMaterial(item) == GTMaterialRegistry.get("redstone")) {
            convert(item, "dirt");
        } else {
            convert(item, "redstone");
        }

        return super.use(item, level, player);
    }
}
