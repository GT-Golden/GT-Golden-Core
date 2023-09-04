package com.github.gtgolden.gtgoldencore.materials.api;

import com.github.gtgolden.gtgoldencore.materials.api.module.Module;
import com.github.gtgolden.gtgoldencore.materials.api.module.TranslationModule;

import java.util.HashMap;
import java.util.Optional;

public class Material {
    public String name;
    public HashMap<String, Module> modules = new HashMap<>();

    public Material(String name) {
        this.name = name;
    }

    public Material addModule(Module module) {
        if (modules.containsKey(module.getModuleType())) {
            modules.put(module.getModuleType(), module.combine(modules.get(module.getModuleType())));
        } else {
            modules.put(module.getModuleType(), module);
        }

        return this;
    }

    public Optional<Module> getModule(String moduleName) {
        return modules.containsKey(moduleName) ? Optional.of(modules.get(moduleName)) : Optional.empty();
    }

    public Optional<String> getTranslationKey() {
        return getModule("translation").map(module -> ((TranslationModule)module).getTranslationKey());
    }

    public String getTranslatedName() {
        return getModule("translation").map(module -> ((TranslationModule)module).getTranslatedName()).orElse(name);
    }
}
