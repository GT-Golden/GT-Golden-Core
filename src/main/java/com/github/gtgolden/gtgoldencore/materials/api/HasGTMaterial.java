package com.github.gtgolden.gtgoldencore.materials.api;

import net.minecraft.item.ItemInstance;

import java.util.Optional;

public interface HasGTMaterial {
    Optional<Material> getGTMaterial(ItemInstance itemInstance);
}
