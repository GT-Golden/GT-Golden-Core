package com.github.gtgolden.gtgoldencore.materials.api.module;

import net.minecraft.client.resource.language.I18n;
import net.modificationstation.stationapi.api.registry.Identifier;

public class TranslationModule implements Module {
    private final String translationKey;
    public TranslationModule(Identifier identifier) {
        translationKey = "material." + identifier.toString() + ".name";
    }

    @Override
    public String getModuleType() {
        return "translation";
    }

    public String getTranslationKey() {
        return translationKey;
    }

    public String getTranslatedName() {
        return I18n.translate(getTranslationKey());
    }
}
