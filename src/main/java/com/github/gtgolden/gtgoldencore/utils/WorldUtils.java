package com.github.gtgolden.gtgoldencore.utils;

import com.github.gtgolden.gtgoldencore.transmission.Connection;
import com.github.gtgolden.gtgoldencore.transmission.ConnectionType;
import com.github.gtgolden.gtgoldencore.transmission.TileCapabilities;
import com.github.gtgolden.gtgoldencore.transmission.HasCapabilities;
import net.minecraft.block.BlockBase;
import net.minecraft.level.BlockView;
import net.minecraft.level.Level;
import net.minecraft.tileentity.TileEntityBase;
import net.minecraft.util.maths.Vec3i;
import net.modificationstation.stationapi.api.template.block.TemplateBlockBase;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class WorldUtils {
    public static @Nullable BlockBase getBlock(@NotNull BlockView tileView, @NotNull Vec3i pos) {
        return getBlock(tileView, pos.x, pos.y, pos.z);
    }

    public static @Nullable BlockBase getBlock(@NotNull BlockView tileView, int x, int y, int z) {
        return TemplateBlockBase.BY_ID[tileView.getTileId(x, y, z)];
    }

    public static @Nullable TileCapabilities getBlockCapabilities(@NotNull BlockView tileView, @NotNull Vec3i pos) {
        return getBlockCapabilities(tileView, pos.x, pos.y, pos.z);
    }

    public static @Nullable TileCapabilities getBlockCapabilities(@NotNull BlockView tileView, int x, int y, int z) {
        BlockBase block = getBlock(tileView, x, y, z);
        if (block instanceof HasCapabilities) {
            return ((HasCapabilities) block).getCapabilities();
        }
        return null;
    }

    public static @Nullable TileEntityBase getTileEntity(@NotNull BlockView tileView, @NotNull Vec3i pos) {
        return getTileEntity(tileView, pos.x, pos.y, pos.z);
    }

    public static @Nullable TileEntityBase getTileEntity(@NotNull BlockView tileView, int x, int y, int z) {
        return tileView.getTileEntity(x, y, z);
    }

    public static @NotNull List<Vec3Facing> checkSurroundingForConnection(@NotNull Level level, @NotNull ConnectionType connectionType,  @NotNull Vec3i pos) {
        List<Vec3Facing> foundBlocks = new ArrayList<>();
        Vec3i check;

        check = new Vec3i(pos.x + 1, pos.y, pos.z);
        if (checkSide(level, check, connectionType, 4))
            foundBlocks.add(new Vec3Facing(check, 4));

        check = new Vec3i(pos.x - 1, pos.y, pos.z);
        if (checkSide(level, check, connectionType, 5))
            foundBlocks.add(new Vec3Facing(check, 5));

        check = new Vec3i(pos.x, pos.y + 1, pos.z);
        if (checkSide(level, check, connectionType, 0))
            foundBlocks.add(new Vec3Facing(check, 0));

        check = new Vec3i(pos.x, pos.y - 1, pos.z);
        if (checkSide(level, check, connectionType, 1))
            foundBlocks.add(new Vec3Facing(check, 1));

        check = new Vec3i(pos.x, pos.y, pos.z + 1);
        if (checkSide(level, check, connectionType, 2))
            foundBlocks.add(new Vec3Facing(check, 2));

        check = new Vec3i(pos.x, pos.y, pos.z - 1);
        if (checkSide(level, check, connectionType, 3))
            foundBlocks.add(new Vec3Facing(check, 3));

        return foundBlocks;
    }

    public static boolean checkSide(@NotNull Level level, @NotNull Vec3i pos, @NotNull ConnectionType type, int side) {
        TileCapabilities tileCapabilities = WorldUtils.getBlockCapabilities(level, pos);
        if (tileCapabilities == null) return false;
        Connection connection = tileCapabilities.getConnectionCapability(type);
        return connection != null && connection.canConnect(level, pos, side);
    }
}