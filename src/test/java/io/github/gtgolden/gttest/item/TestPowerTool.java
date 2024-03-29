package io.github.gtgolden.gttest.item;

import io.github.gtgolden.gtgoldencore.machines.api.item.*;
import io.github.gtgolden.gtgoldencore.machines.api.slot.GTSlot;
import io.github.gtgolden.gtgoldencore.machines.api.slot.GTSlotBuilder;
import io.github.gtgolden.gtgoldencore.materials.GTMaterials;
import io.github.gtgolden.gtgoldencore.materials.api.HasGTMaterial;
import io.github.gtgolden.gtgoldencore.materials.api.Material;
import io.github.gtgolden.gtgoldencore.materials.api.module.ToolMaterialModule;
import io.github.gtgolden.gtgoldencore.materials.impl.MaterialRegistry;
import io.github.gtgolden.gttest.GtTest;
import io.github.gtgolden.gttest.container.ContainerPowerTool;
import net.minecraft.block.BlockBase;
import net.minecraft.entity.EntityBase;
import net.minecraft.entity.Living;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.item.ItemBase;
import net.minecraft.item.ItemInstance;
import net.minecraft.item.tool.ToolMaterial;
import net.minecraft.level.Level;
import net.modificationstation.stationapi.api.block.BlockState;
import net.modificationstation.stationapi.api.gui.screen.container.GuiHelper;
import net.modificationstation.stationapi.api.registry.BlockRegistry;
import net.modificationstation.stationapi.api.tag.TagKey;
import net.modificationstation.stationapi.api.template.item.TemplateToolItem;
import net.modificationstation.stationapi.api.util.Identifier;
import org.jetbrains.annotations.Nullable;

public class TestPowerTool extends TemplateToolItem implements HasGTMaterial, ItemWithPowerStorage, HasPowerBar, HasPowerTooltip, ItemWithItemStorage {
    private static final GTSlot[] slots = {
            new GTSlotBuilder(GTSlot::new).build(),
            new GTSlotBuilder(GTSlot::new).build(),
            new GTSlotBuilder(GTSlot::new).build()
    };
    private static final int THROUGHPUT = 10;
    private final int powerPerUse = 1;
    private final TagKey<BlockBase> drillEffectiveOn;
    private final TagKey<BlockBase> sawEffectiveOn;

    public TestPowerTool(Identifier identifier) {
        super(identifier, 0, GTMaterials.MISSING_TOOL_MATERIAL, new BlockBase[]{});
        setTranslationKey(identifier);
        drillEffectiveOn = TagKey.of(BlockRegistry.INSTANCE.getKey(), GtTest.NAMESPACE.id("mineable/drill"));
        sawEffectiveOn = TagKey.of(BlockRegistry.INSTANCE.getKey(), GtTest.NAMESPACE.id("mineable/chainsaw"));
        setEffectiveBlocks(drillEffectiveOn);
    }

    public static String getSelectedTool(ItemInstance itemInstance) {
        var nbt = itemInstance.getStationNbt();
        if (!nbt.containsKey("power_tool")) nbt.put("power_tool", "drill");
        return nbt.getString("power_tool");
    }

    @Override
    public TagKey<BlockBase> getEffectiveBlocks(ItemInstance itemInstance) {
        return getSelectedTool(itemInstance).equals("drill") ? drillEffectiveOn : sawEffectiveOn;
    }

    @Override
    public @Nullable Material getGTMaterial(ItemInstance itemInstance) {
        var materialItem = getInventoryItem(itemInstance, 0);
        if (materialItem == null) return null;
        var type = materialItem.getType();
        if (type == ItemBase.ironIngot) {
            return MaterialRegistry.getMaterial("iron");
        } else if (type == ItemBase.goldIngot) {
            return MaterialRegistry.getMaterial("gold");
        } else if (materialItem.itemId == BlockBase.COBBLESTONE.id || materialItem.itemId == BlockBase.STONE.id) {
            return MaterialRegistry.getMaterial("stone");
        } else if (materialItem.itemId == BlockBase.WOOD.id) {
            return MaterialRegistry.getMaterial("wood");
        } else {
            return null;
        }
    }

    @Override
    public ToolMaterial getMaterial(ItemInstance itemInstance) {
        if (getPower(itemInstance) < powerPerUse) return GTMaterials.MISSING_TOOL_MATERIAL;
        var material = getGTMaterial(itemInstance);
        if (material == null) return GTMaterials.MISSING_TOOL_MATERIAL;
        var toolMaterialModule = material.getModule(ToolMaterialModule.class);
        if (toolMaterialModule == null) return GTMaterials.MISSING_TOOL_MATERIAL;
        return toolMaterialModule.material;
    }

    @Override
    public float getMiningSpeedMultiplier(ItemInstance itemStack, BlockState state) {
        return isSuitableFor(itemStack, state) ? 1.2f : 1.0f;
    }

    @Override
    public void inventoryTick(ItemInstance itemInstance, Level level, EntityBase entityBase, int i, boolean bl) {
        var item = getInventoryItem(itemInstance, 1);
        if (item != null && item.getType() instanceof Battery battery) {
            var available = battery.getPower(item);
            var taken = battery.discharge(item, Math.min(Math.min(THROUGHPUT, available), getMissingPower(itemInstance)));
            markInventoryDirty(itemInstance);
            charge(itemInstance, taken);
        }
        super.inventoryTick(itemInstance, level, entityBase, i, bl);
    }

    @Override
    public ItemInstance use(ItemInstance itemInstance, Level level, PlayerBase player) {
        if (player.method_1373()) {
            var inventory = getInventory(itemInstance);
            GuiHelper.openGUI(
                    player,
                    GtTest.NAMESPACE.id("openPowerTool"),
                    inventory,
                    new ContainerPowerTool(player.inventory, (ItemInventoryBase) inventory)
            );
        }

        return super.use(itemInstance, level, player);
    }

    @Override
    public GTSlot[] getSlots(ItemInstance itemInstance) {
        return slots;
    }

    @Override
    public int getMaxPower(ItemInstance itemInstance) {
        return 512;
    }

    @Override
    public String[] getTooltip(ItemInstance itemInstance, String originalTooltip) {
        return HasPowerTooltip.super.getTooltip(itemInstance, originalTooltip);
    }

    @Override
    public boolean postMine(ItemInstance itemInstance, int i, int j, int k, int l, Living arg2) {
        if (getPower(itemInstance) >= powerPerUse) {
            discharge(itemInstance, powerPerUse);
        }
        return super.postMine(itemInstance, i, j, k, l, arg2);
    }
}
