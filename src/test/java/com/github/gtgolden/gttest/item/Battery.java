package com.github.gtgolden.gttest.item;

import com.github.gtgolden.gtgoldencore.machines.api.item.HasPowerBar;
import com.github.gtgolden.gtgoldencore.machines.api.item.HasPowerTooltip;
import com.github.gtgolden.gtgoldencore.machines.api.item.ItemWithPowerStorage;
import net.minecraft.item.ItemInstance;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.template.item.TemplateItemBase;

public class Battery extends TemplateItemBase implements ItemWithPowerStorage, HasPowerBar, HasPowerTooltip {
    public Battery(Identifier identifier) {
        super(identifier);
        setTranslationKey(identifier);
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
