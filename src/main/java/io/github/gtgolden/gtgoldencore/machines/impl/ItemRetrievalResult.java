package io.github.gtgolden.gtgoldencore.machines.impl;

import net.minecraft.item.ItemInstance;

public record ItemRetrievalResult(boolean successful, ItemInstance foundItem) { }
