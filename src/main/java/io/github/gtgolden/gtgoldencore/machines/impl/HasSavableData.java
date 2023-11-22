package io.github.gtgolden.gtgoldencore.machines.impl;

import net.minecraft.util.io.CompoundTag;

public interface HasSavableData {
    void readData(CompoundTag tag);

    void writeData(CompoundTag tag);
}
