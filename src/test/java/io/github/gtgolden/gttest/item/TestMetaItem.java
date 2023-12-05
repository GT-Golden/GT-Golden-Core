package io.github.gtgolden.gttest.item;

import io.github.gtgolden.gtgoldencore.materials.api.HasNBTBasedGTMaterial;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.item.ItemInstance;
import net.minecraft.level.Level;
import net.modificationstation.stationapi.api.client.item.CustomTooltipProvider;
import net.modificationstation.stationapi.api.template.item.TemplateItem;
import net.modificationstation.stationapi.api.util.Identifier;

import java.util.Objects;

public class TestMetaItem extends TemplateItem implements HasNBTBasedGTMaterial, CustomTooltipProvider {
    public TestMetaItem(Identifier identifier) {
        super(identifier);
        setTranslationKey(identifier);
    }

    @Override
    public ItemInstance use(ItemInstance itemInstance, Level level, PlayerBase player) {
        var material = getGTMaterial(itemInstance);
        if (material == null || Objects.equals(material.name, "wood")) {
            player.swingHand();
            setGTMaterial(itemInstance, "diamond");
        } else if (Objects.equals(material.name, "diamond")) {
            player.swingHand();
            setGTMaterial(itemInstance, "redstone");
        } else if (Objects.equals(material.name, "redstone")) {
            player.swingHand();
            setGTMaterial(itemInstance, "dirt");
        } else if (Objects.equals(material.name, "dirt")) {
            player.swingHand();
            setGTMaterial(itemInstance, "wood");
        }
        return super.use(itemInstance, level, player);
    }

    @Override
    public String[] getTooltip(ItemInstance itemInstance, String originalTooltip) {
        var material = getGTMaterial(itemInstance);
        if (material == null) return new String[]{String.format(originalTooltip, "(NULL)")};
        return new String[]{material.getTranslatedName(originalTooltip, "test_meta_item")};
    }
}
