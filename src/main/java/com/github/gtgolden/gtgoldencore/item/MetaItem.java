package com.github.gtgolden.gtgoldencore.item;

import net.minecraft.client.resource.language.I18n;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.item.ItemBase;
import net.minecraft.item.ItemInstance;
import net.minecraft.level.Level;
import net.minecraft.util.io.CompoundTag;
import net.modificationstation.stationapi.api.client.gui.CustomTooltipProvider;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.registry.ItemRegistry;
import net.modificationstation.stationapi.api.template.item.TemplateItemBase;

import java.util.HashMap;
import java.util.Objects;

public class MetaItem extends TemplateItemBase implements CustomTooltipProvider {

    public static HashMap<String, MetaItem> metaItems = new HashMap<>();
    public static MetaItem MISSING;

    public MetaItem(Identifier identifier) {
        super(identifier);
        System.out.println(identifier.toString());
        setTranslationKey(identifier.toString());
        metaItems.put(identifier.id, this);
    }

    @Override
    public void onCreation(ItemInstance item, Level arg1, PlayerBase arg2) {
        super.onCreation(item, arg1, arg2);
        updateStats(item);
    }

    public void updateStats(ItemInstance item) {
        CompoundTag nbt = item.getStationNBT();
        if (!nbt.containsKey("material")) {
            nbt.put("material", "missingMaterial");
        }
    }

    public static ItemInstance convert(String itemName, String material) {
        return convert(MetaItem.get(itemName), material);
    }
    public static ItemInstance convert(String itemName, String material, int count) {
        return convert(MetaItem.get(itemName), material, count);
    }
    public static ItemInstance convert(ItemBase itemBase, String material) {
        return convert(new ItemInstance(itemBase), material);
    }
    public static ItemInstance convert(ItemBase itemBase, String material, int count) {
        return convert(new ItemInstance(itemBase), material, count);
    }
    public static ItemInstance convert(ItemInstance item, String material) {
        item.getStationNBT().put("material", material);
        return item;
    }
    public static ItemInstance convert(ItemInstance item, String material, int count) {
        item.getStationNBT().put("material", material);
        item.count = count;
        return item;
    }

    public static MetaItem get(String name) {
        MetaItem item = metaItems.get(name);
        if (item != null) return item;
        return MISSING;
    }

    @Override
    public String[] getTooltip(ItemInstance item, String originalTooltip) {
        CompoundTag nbt = item.getStationNBT();
        String untranslatedName = getTranslationKey();
        untranslatedName = untranslatedName.substring(untranslatedName.indexOf(":") + 1).replace("%s ", "");
        String modId = Objects.requireNonNull(ItemRegistry.INSTANCE.getId(item.getType())).modID.toString();
        String material = nbt.getString("material");
        String materialName = I18n.translate(String.format("material.%s:%s.name", modId, material));
        String itemName = I18n.translate(String.format("item.%s:%s_%s.name", modId, material, untranslatedName));
        if (!itemName.contains(".name")) {
            return new String[] {
                    itemName
            };
        }

        return new String[] {
                String.format(originalTooltip, materialName)
        };
    }
}
