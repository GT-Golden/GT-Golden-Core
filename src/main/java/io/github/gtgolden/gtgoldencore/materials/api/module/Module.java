package io.github.gtgolden.gtgoldencore.materials.api.module;

public interface Module {
    Class<? extends Module> getModuleType();

    default Module combine(Module existingModule) {
        return this;
    }
}
