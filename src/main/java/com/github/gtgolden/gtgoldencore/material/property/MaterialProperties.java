package com.github.gtgolden.gtgoldencore.material.property;

import com.github.gtgolden.gtgoldencore.material.GTMaterial;
import com.google.common.collect.ImmutableList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;

public class MaterialProperties {
    private final HashMap<String, MaterialProperty<?>> properties;
    public final List<String> states;

    public MaterialProperties(HashMap<String, MaterialProperty<?>> properties, List<String> states) {
        this.states = states;
        this.properties = properties;

        for(MaterialProperty<?> property: properties.values())
            property.verifyProperty(this);
    }
    @Nullable
    public MaterialProperty<?> get(String name) {
        return properties.get(name);
    }
}
