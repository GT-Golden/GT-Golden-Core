package io.github.gtgolden.gtgoldencore.materials.api;

import net.minecraft.item.ItemInstance;
import org.jetbrains.annotations.Nullable;

public interface HasGTMaterial {
    @Nullable Material getGTMaterial(ItemInstance itemInstance);
}
