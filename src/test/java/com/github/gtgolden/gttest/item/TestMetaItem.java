package com.github.gtgolden.gttest.item;

import com.github.gtgolden.gtgoldencore.materials.api.HasNBTBasedGTMaterial;
import com.github.gtgolden.gtgoldencore.materials.api.Material;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.item.ItemInstance;
import net.minecraft.level.Level;
import net.modificationstation.stationapi.api.client.gui.CustomTooltipProvider;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.template.item.TemplateItemBase;

import java.util.Objects;

public class TestMetaItem extends TemplateItemBase implements HasNBTBasedGTMaterial, CustomTooltipProvider {
    public TestMetaItem(Identifier identifier) {
        super(identifier);
        setTranslationKey(identifier);
    }

    @Override
    public ItemInstance use(ItemInstance itemInstance, Level level, PlayerBase player) {
        var material = getGTMaterial(itemInstance);
        if (material.isEmpty() || Objects.equals(material.get().name, "dirt")) {
            player.swingHand();
            setGTMaterial(itemInstance, "diamond");
        } else if (Objects.equals(material.get().name, "diamond")) {
            player.swingHand();
            setGTMaterial(itemInstance, "redstone");
        } else if (Objects.equals(material.get().name, "redstone")) {
            player.swingHand();
            setGTMaterial(itemInstance, "dirt");
        }
        return super.use(itemInstance, level, player);
    }

    @Override
    public String[] getTooltip(ItemInstance itemInstance, String originalTooltip) {
        return new String[] {
                getGTMaterial(itemInstance).map(material ->
                        material.getTranslatedName(originalTooltip, "test_meta_item")
                ).orElseGet(() ->
                        String.format(originalTooltip, "(NULL)")
                )
        };
    }
}
