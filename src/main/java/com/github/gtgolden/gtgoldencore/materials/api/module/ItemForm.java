package com.github.gtgolden.gtgoldencore.materials.api.module;

import net.minecraft.item.ItemInstance;

// TODO: Figure out how units will work (ex: a nugget is 1/9 of an ingot)
public class ItemForm {
    protected final ItemInstance itemInstance;

    public ItemForm(ItemInstance itemInstance) {
        this.itemInstance = itemInstance;
    }

    public ItemInstance getForm() {
        return itemInstance;
    }
}
