package com.github.gtgolden.gtgoldencore.materials.api.module;

import net.minecraft.item.ItemInstance;

public class ItemForm {
    protected final ItemInstance itemInstance;

    public ItemForm(ItemInstance itemInstance) {
        this.itemInstance = itemInstance;
    }

    public ItemInstance getForm() {
        return itemInstance;
    }
}
