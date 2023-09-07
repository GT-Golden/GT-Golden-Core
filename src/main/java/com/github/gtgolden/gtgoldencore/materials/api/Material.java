package com.github.gtgolden.gtgoldencore.materials.api;

import com.github.gtgolden.gtgoldencore.materials.api.module.Module;
import com.github.gtgolden.gtgoldencore.materials.api.module.TranslationModule;
import net.modificationstation.stationapi.api.registry.ModID;

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

    public void registerTranslationProvider(ModID modID) {
        getModule("translation").ifPresent(
                module -> {
                    assert module instanceof TranslationModule;
                    ((TranslationModule) module).addModID(modID);
                }
        );
    }

    public Optional<String> getTranslationKey() {
        return getModule("translation").map(module -> ((TranslationModule) module).getTranslationKey());
    }

    public String getTranslatedName() {
        return getModule("translation").map(module -> ((TranslationModule) module).getTranslatedName()).orElse(name);
    }

    public String getTranslatedName(String originalTooltip, String form) {
        return getModule("translation").map(module -> ((TranslationModule) module).getTranslatedName(originalTooltip, form)).orElse(name);
    }

    public Optional<String> getUniqueTranslatedName(String form) {
        return getModule("translation").flatMap(module -> ((TranslationModule) module).getUniqueTranslatedName(form));
    }
}
