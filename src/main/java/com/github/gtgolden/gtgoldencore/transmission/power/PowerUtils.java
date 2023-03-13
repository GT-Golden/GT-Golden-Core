package com.github.gtgolden.gtgoldencore.transmission.power;

import com.github.gtgolden.gtgoldencore.transmission.ConnectionType;
import com.github.gtgolden.gtgoldencore.transmission.TileEntityWithCapabilities;
import com.github.gtgolden.gtgoldencore.utils.Vec3Facing;
import com.github.gtgolden.gtgoldencore.utils.WorldUtils;
import net.minecraft.item.ItemInstance;
import net.minecraft.level.Level;
import net.minecraft.tileentity.TileEntityBase;
import net.minecraft.util.maths.Vec3i;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class PowerUtils {
    public static int attemptChargeItem(@NotNull ItemInstance item, int availablePower, int drainPower) {
        if (!(item.getType() instanceof ItemPowerStorage itemPowerStorage)) {
            return 0;
        }
        if (itemPowerStorage.charge(item, Math.min(availablePower, drainPower), true) > 0) {
            return itemPowerStorage.charge(item, Math.min(availablePower, drainPower), false);
        } else {
            return 0;
        }
    }

    public static int attemptConsumeItemPower(@NotNull ItemInstance item, int consumeAmount) {
        if (!(item.getType() instanceof ItemPowerStorage itemPowerStorage)) {
            return 0;
        }
        if (itemPowerStorage.consume(item, consumeAmount, true) > 0) {
            return itemPowerStorage.consume(item, consumeAmount, false);
        } else {
            return 0;
        }
    }

    public static void updateConnectedMachines(@NotNull Level level, @NotNull Vec3i pos) {
        System.out.println("Machine updates pushed from " + pos.x + ", " + pos.y + ", " + pos.z + ".");
        List<TileEntityWithCapabilities> connectedMachines = PowerUtils.findMachineConnections(level, pos);
        for (TileEntityWithCapabilities machine : connectedMachines) {
            machine.markDirty();
        }
    }

    public static int sendPowerToConnections(@NotNull List<TilePowered> machines, int availablePower, int drainPower) {
        int powerDrained = 0;

        for (TilePowered machine : machines) {
            if (availablePower - powerDrained > 0) {
                if (machine.charge(Math.min(availablePower, drainPower), 6, true) > 0) {
                    powerDrained += machine.charge(Math.min(availablePower, drainPower), 6, false);
                }
            }
        }

        return powerDrained;
    }

    public static @NotNull List<TileEntityWithCapabilities> findMachineConnections(@NotNull Level level, @NotNull Vec3i pos) {
        List<Vec3Facing> allBlockConnections = findPowerConnections(level, pos);
        allBlockConnections.removeIf(connection -> !WorldUtils.checkSide(level, connection.pos(), ConnectionType.power, connection.side()));

        List<TileEntityWithCapabilities> allConnections = new ArrayList<>();

        for (Vec3Facing block : allBlockConnections) {
            allConnections.add((TileEntityWithCapabilities) level.getTileEntity(block.pos().x, block.pos().y, block.pos().z));
        }

        return allConnections;
    }

    public static @NotNull List<Vec3Facing> findPowerConnections(@NotNull Level level, @NotNull Vec3i pos) {
        List<Vec3Facing> blocksChecked = new ArrayList<>();
        List<Vec3Facing> blocksToCheck = checkSurroundingPowerConnection(level, pos);
        blocksChecked.add(new Vec3Facing(pos, 0));

        List<Vec3Facing> check;
        while (blocksToCheck.size() > 0) {
            if (WorldUtils.getBlock(level, blocksToCheck.get(0).pos()) instanceof TilePowerConnection) {
                blocksChecked.add(blocksToCheck.get(0));
                blocksToCheck.remove(0);
                continue;
            }

            check = checkSurroundingPowerConnection(level, blocksToCheck.get(0).pos());

            blocksChecked.add(blocksToCheck.get(0));
            blocksToCheck.removeAll(blocksChecked);
            check.removeAll(blocksChecked);

            blocksToCheck.addAll(check);
        }

        blocksChecked.remove(0);

        return blocksChecked;
    }

    public static @NotNull List<Vec3Facing> checkSurroundingPowerConnection(@NotNull Level level, @NotNull Vec3i pos) {
        return WorldUtils.checkSurroundingForConnection(level, ConnectionType.power, pos);
    }
}
