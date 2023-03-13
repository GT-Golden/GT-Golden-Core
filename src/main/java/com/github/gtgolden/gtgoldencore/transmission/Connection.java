package com.github.gtgolden.gtgoldencore.transmission;

import net.minecraft.level.BlockView;
import net.minecraft.util.maths.Vec3i;
import org.jetbrains.annotations.NotNull;

public interface Connection {
    default boolean canConnect(@NotNull BlockView tileView, @NotNull Vec3i pos, int side) {
        return canConnect(tileView, pos.x, pos.y, pos.z, side);
    }

    default boolean canConnect(@NotNull BlockView tileView, int x, int y, int z, int side) {
        return true;
    }
}
