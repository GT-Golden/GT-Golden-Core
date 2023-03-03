package com.github.gtgolden.gtgoldencore.utils;

import net.minecraft.util.maths.Vec3i;

public record Vec3Facing(Vec3i pos, int side) {
    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Vec3Facing) {
            return this.pos.equals(((Vec3Facing) obj).pos);
        }
        return false;
    }
}
