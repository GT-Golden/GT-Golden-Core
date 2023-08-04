package com.github.gtgolden.gttest.item;

import net.minecraft.item.ItemInstance;
import net.modificationstation.stationapi.api.client.gui.CustomTooltipProvider;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.template.item.TemplateItemBase;
import net.modificationstation.stationapi.api.util.Colours;

public class DebugMonitorTool extends TemplateItemBase implements CustomTooltipProvider {
    public DebugMonitorTool(Identifier identifier) {
        super(identifier);
        setTranslationKey(identifier.toString());
        setMaxStackSize(1);
    }

    @Override
    public String[] getTooltip(ItemInstance itemInstance, String originalTooltip) {
        return new String[] {
                originalTooltip,
                "Point at an object and all",
                Colours.GOLD + "   Golden Core " + Colours.WHITE + "related details will be shown."
        };
    }
}
