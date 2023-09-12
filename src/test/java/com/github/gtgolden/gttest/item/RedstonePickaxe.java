package com.github.gtgolden.gttest.item;

import com.github.gtgolden.gtgoldencore.materials.GTMaterials;
import com.github.gtgolden.gtgoldencore.materials.api.HasGTMaterial;
import com.github.gtgolden.gtgoldencore.materials.api.Material;
import com.github.gtgolden.gtgoldencore.materials.api.module.ToolMaterialModule;
import com.github.gtgolden.gtgoldencore.materials.impl.MaterialRegistry;
import net.minecraft.entity.EntityBase;
import net.minecraft.item.ItemInstance;
import net.minecraft.item.tool.ToolMaterial;
import net.minecraft.level.Level;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.template.item.tool.TemplatePickaxe;

import java.util.Optional;

public class RedstonePickaxe extends TemplatePickaxe implements HasGTMaterial {
    private ToolMaterial material;

    public RedstonePickaxe(Identifier identifier) {
        super(identifier, GTMaterials.MISSING_TOOL_MATERIAL);
        setTranslationKey(identifier);
    }

    private void checkMaterial() {
        if (material == null) {
            material = MaterialRegistry.getMaterialModule("redstone", ToolMaterialModule.class).get().material;
            setDurability(material.getDurability());
        }
    }

    @Override
    public void inventoryTick(ItemInstance arg, Level arg2, EntityBase arg3, int i, boolean bl) {
        checkMaterial();
        super.inventoryTick(arg, arg2, arg3, i, bl);
    }

    @Override
    public ToolMaterial getMaterial(ItemInstance itemInstance) {
        checkMaterial();
        return material;
    }

    @Override
    public Optional<Material> getGTMaterial(ItemInstance itemInstance) {
        return MaterialRegistry.getMaterial("redstone");
    }
}
