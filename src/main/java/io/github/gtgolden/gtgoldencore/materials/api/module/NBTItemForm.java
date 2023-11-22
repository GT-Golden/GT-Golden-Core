package io.github.gtgolden.gtgoldencore.materials.api.module;

import io.github.gtgolden.gtgoldencore.materials.GTMaterials;
import net.minecraft.item.ItemBase;
import net.minecraft.item.ItemInstance;

public class NBTItemForm extends ItemForm {
    public NBTItemForm(ItemBase itemBase, String material) {
        super(new ItemInstance(itemBase));
        itemInstance.getStationNbt().put(GTMaterials.MATERIAL_NBT_KEY, material);
    }
}
