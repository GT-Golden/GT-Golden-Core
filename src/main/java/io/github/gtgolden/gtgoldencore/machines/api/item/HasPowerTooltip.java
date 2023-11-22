package io.github.gtgolden.gtgoldencore.machines.api.item;

import net.minecraft.item.ItemInstance;
import net.modificationstation.stationapi.api.client.item.CustomTooltipProvider;

public interface HasPowerTooltip extends CustomTooltipProvider, ItemWithPowerStorage {
    @Override
    default String[] getTooltip(ItemInstance itemInstance, String originalTooltip) {
        return new String[]{
                originalTooltip,
                "§c" + getPower(itemInstance) + "§f" + "/" + "§3" + getMaxPower(itemInstance) + "§f" + " power stored"
        };
    }
}
