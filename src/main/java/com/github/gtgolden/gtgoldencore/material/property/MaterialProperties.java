package com.github.gtgolden.gtgoldencore.material.property;

import com.github.gtgolden.gtgoldencore.material.GTMaterial;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

public class MaterialProperties {
    private HashMap<String, MaterialProperty<?>> properties;
    protected final GTMaterial.Builder builder;

    public MaterialProperties(GTMaterial.Builder builder) {
        this.builder = builder;
    }
    public void set(String name, @NotNull MaterialProperty<?> property) {
        property.verifyProperty(this);
        properties.put(name, property);
    }
    public void ensureStates(String... name) {
        builder.states(name);
    }
    @Nullable
    public MaterialProperty<?> get(String name) {
        return properties.get(name);
    }
}
