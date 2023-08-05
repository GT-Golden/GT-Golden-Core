package com.github.gtgolden.gtgoldencore.materials.impl;

import com.github.gtgolden.gtgoldencore.materials.api.MetaItem;

import java.util.HashMap;

public class MetaItemRegistry {
    private static final HashMap<String, MetaItem> metaItems = new HashMap<>();

    public static void put(String name, MetaItem item) {
        metaItems.put(name, item);
    }

    public static MetaItem get(String name) {
        return metaItems.getOrDefault(name, metaItems.get("missing"));
    }

    public static HashMap<String, MetaItem> getAllMetaItems() {
        return metaItems;
    }
}
