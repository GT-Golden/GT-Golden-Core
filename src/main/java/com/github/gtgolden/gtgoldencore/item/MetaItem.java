package com.github.gtgolden.gtgoldencore.item;

import com.github.gtgolden.gtgoldencore.material.GTMaterial;
import com.github.gtgolden.gtgoldencore.material.Materials;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.item.ItemBase;
import net.minecraft.item.ItemInstance;
import net.minecraft.level.Level;
import net.minecraft.util.io.CompoundTag;
import net.modificationstation.stationapi.api.client.color.item.ItemColorProvider;
import net.modificationstation.stationapi.api.client.gui.CustomTooltipProvider;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.registry.ItemRegistry;
import net.modificationstation.stationapi.api.registry.ModID;
import net.modificationstation.stationapi.api.template.item.TemplateItemBase;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

import static net.modificationstation.stationapi.api.util.Colours.*;

public class MetaItem extends TemplateItemBase implements CustomTooltipProvider, ItemColorProvider {

    public static HashMap<String, MetaItem> metaItems = new HashMap<>();
    public static MetaItem MISSING;

    public MetaItem(Identifier identifier) {
        super(identifier);
        setTranslationKey(identifier.toString());
        metaItems.put(identifier.id, this);
    }

    @Override
    public void onCreation(ItemInstance item, Level arg1, PlayerBase arg2) {
        super.onCreation(item, arg1, arg2);
        updateStats(item);
    }

    public void updateStats(@NotNull ItemInstance item) {
        CompoundTag nbt = item.getStationNBT();
        if (!nbt.containsKey("material")) {
            nbt.put("material", "missingMaterial");
        }
    }

    public static ItemInstance convert(String itemName, String material) {
        return convert(MetaItem.get(itemName), material);
    }
    public static ItemInstance convert(ItemBase itemBase, String material) {
        return convert(new ItemInstance(itemBase), material);
    }
    public static ItemInstance convert(@NotNull ItemInstance item, String material) {
        item.getStationNBT().put("material", material);
        return item;
    }

    public static MetaItem get(String name) {
        MetaItem item = metaItems.get(name);
        if (item == null) return MISSING;
        return item;
    }

    @Override
    public String[] getTooltip(@NotNull ItemInstance item, String originalTooltip) {
        CompoundTag nbt = item.getStationNBT();
        String untranslatedName = getTranslationKey();
        untranslatedName = untranslatedName.substring(untranslatedName.indexOf(":") + 1).replace("%s ", "");
        ModID modId = Objects.requireNonNull(ItemRegistry.INSTANCE.getId(item.getType())).modID;
        String material = nbt.getString("material");
        GTMaterial gtMaterial = Materials.get(material);
        String itemName = I18n.translate(String.format("item.%s:%s_%s.name", modId.toString(), material, untranslatedName));
        String materialName = I18n.translate(String.format("material.%s:%s.name", gtMaterial.getSourceMod(), material));

        List<String> output = new ArrayList<>();

        output.add(!itemName.contains(".name") ?
                itemName :
                String.format(originalTooltip, materialName));

        output.add(YELLOW + gtMaterial.getFormula());

        if (!Objects.equals(gtMaterial.getSourceMod(), modId.getName()))
            output.add(GRAY + "Added by: " + RED + gtMaterial.getSourceMod());
        output.add(BLUE + modId.getName());

        return output.toArray(new String[0]);
    }

    @Override
    public int getColor(@NotNull ItemInstance item, int tint) {
        return Materials.get(item.getStationNBT().getString("material")).getMaterialColor();
    }
}
