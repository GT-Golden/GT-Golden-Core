package com.github.gtgolden.gtgoldencore.machines;

import net.minecraft.util.io.CompoundTag;

public interface HasSavableData {
    void readData(CompoundTag tag);

    void writeData(CompoundTag tag);
}
