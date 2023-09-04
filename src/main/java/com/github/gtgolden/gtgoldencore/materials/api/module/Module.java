package com.github.gtgolden.gtgoldencore.materials.api.module;

public interface Module {
    String getModuleType();
    default Module combine(Module existingModule) {
        return this;
    }
}
