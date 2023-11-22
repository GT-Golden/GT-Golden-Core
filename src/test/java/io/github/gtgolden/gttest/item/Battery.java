package io.github.gtgolden.gttest.item;

import io.github.gtgolden.gtgoldencore.machines.api.item.HasPowerBar;
import io.github.gtgolden.gtgoldencore.machines.api.item.HasPowerTooltip;
import io.github.gtgolden.gtgoldencore.machines.api.item.ItemWithPowerStorage;
import net.minecraft.item.ItemInstance;
import net.modificationstation.stationapi.api.template.item.TemplateItem;
import net.modificationstation.stationapi.api.util.Identifier;

public class Battery extends TemplateItem implements ItemWithPowerStorage, HasPowerBar, HasPowerTooltip {
    public Battery(Identifier identifier) {
        super(identifier);
        setTranslationKey(identifier);
        setMaxStackSize(1);
    }

    @Override
    public int getMaxPower(ItemInstance itemInstance) {
        return 256;
    }

    @Override
    public int getDefaultPower(ItemInstance itemInstance) {
        return 256;
    }

    @Override
    public String[] getTooltip(ItemInstance itemInstance, String originalTooltip) {
        return HasPowerTooltip.super.getTooltip(itemInstance, originalTooltip);
    }
}
