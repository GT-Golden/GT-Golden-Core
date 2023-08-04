package com.github.gtgolden.gtgoldencore.mixin;

import com.github.gtgolden.gtgoldencore.machines.HasItemIO;
import com.github.gtgolden.gtgoldencore.machines.SlotType;
import net.minecraft.item.ItemInstance;
import net.minecraft.tileentity.TileEntityFurnace;
import net.modificationstation.stationapi.api.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(TileEntityFurnace.class)
public abstract class FurnaceItemStorageMixin implements HasItemIO {
    @Override
    public boolean isItemInput(Direction side) {
        return side != Direction.DOWN;
    }

    @Override
    public boolean isItemOutput(Direction side) {
        return side == Direction.DOWN;
    }

    @Shadow
    public abstract int getInventorySize();

    @Shadow
    public abstract ItemInstance getInventoryItem(int i);

    @Shadow
    public abstract ItemInstance takeInventoryItem(int i, int j);

    @Shadow
    public abstract void setInventoryItem(int i, ItemInstance arg);

    @Unique
    private static boolean isValidType(SlotType type) {
        return type == SlotType.INPUT || type == SlotType.FUEL_INPUT || type == SlotType.OUTPUT;
    }

    @Unique
    private static int getTranslatedSlot(SlotType type) {
        return switch (type) {
            case INPUT:
                yield 0;
            case FUEL_INPUT:
                yield 1;
            case OUTPUT:
                yield 2;
            default:
                yield -1;
        };
    }

    @Override
    public int getInventorySize(SlotType type) {
        if (isValidType(type)) {
            return 1;
        } else {
            return 0;
        }
    }

    @Override
    public ItemInstance getInventoryItem(SlotType type, int slot) {
        if (!isValidType(type) || slot != 0) return null;
        return getInventoryItem(getTranslatedSlot(type));
    }

    @Override
    public ItemInstance takeInventoryItem(SlotType type, int slot, int count) {
        if (!isValidType(type) || slot != 0) return null;
        return takeInventoryItem(getTranslatedSlot(type), count);
    }

    @Override
    public void setInventoryItem(SlotType type, int slot, ItemInstance itemInstance) {
        if (!isValidType(type) || slot != 0) return;
        setInventoryItem(getTranslatedSlot(type), itemInstance);
    }
}
