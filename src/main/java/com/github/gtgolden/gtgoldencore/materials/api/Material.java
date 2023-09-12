package com.github.gtgolden.gtgoldencore.materials.api;

import com.github.gtgolden.gtgoldencore.materials.api.module.Module;
import com.github.gtgolden.gtgoldencore.materials.api.module.TranslationModule;
import net.modificationstation.stationapi.api.registry.ModID;

import java.util.HashMap;
import java.util.Optional;

public class Material {
    public String name;
    public HashMap<Class<? extends Module>, Module> modules = new HashMap<>();

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

    public <T extends Module> Optional<T> getModule(Class<T> moduleName) {
        return (Optional<T>) Optional.ofNullable(modules.get(moduleName));
    }

    public void registerTranslationProvider(ModID modID) {
        getModule(TranslationModule.class).ifPresent(module -> module.addModID(modID));
    }

    public Optional<String> getTranslationKey() {
        return getModule(TranslationModule.class).map(TranslationModule::getTranslationKey);
    }

    public String getTranslatedName() {
        return getModule(TranslationModule.class).map(TranslationModule::getTranslatedName).orElse(name);
    }

    public String getTranslatedName(String originalTooltip, String form) {
        return getModule(TranslationModule.class).map(module -> module.getTranslatedName(originalTooltip, form)).orElse(name);
    }

    public Optional<String> getUniqueTranslatedName(String form) {
        return getModule(TranslationModule.class).flatMap(module -> module.getUniqueTranslatedName(form));
    }
}
