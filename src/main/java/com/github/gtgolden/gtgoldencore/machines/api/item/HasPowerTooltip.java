package com.github.gtgolden.gtgoldencore.machines.api.item;

import net.minecraft.item.ItemInstance;
import net.modificationstation.stationapi.api.client.gui.CustomTooltipProvider;
import net.modificationstation.stationapi.api.util.Colours;

public interface HasPowerTooltip extends CustomTooltipProvider, ItemWithPowerStorage {
    @Override
    default String[] getTooltip(ItemInstance itemInstance, String originalTooltip) {
        return new String[]{
                originalTooltip,
                Colours.RED + "" + getPower(itemInstance) + Colours.WHITE + "/" + Colours.DARK_AQUA + getMaxPower(itemInstance) + Colours.WHITE + " power stored"
        };
    }
}
