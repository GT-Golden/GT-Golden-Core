package com.github.gtgolden.gtgoldencore.materials.api.module;

import net.minecraft.client.resource.language.I18n;
import net.modificationstation.stationapi.api.registry.Identifier;
import net.modificationstation.stationapi.api.registry.ModID;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Optional;

public class TranslationModule implements Module {
    protected ModID[] modIds;
    protected String materialName;
    private final String translationKey;

    public TranslationModule(Identifier identifier) {
        modIds = new ModID[]{identifier.modID};
        materialName = identifier.id;
        translationKey = "material." + identifier + ".name";
    }

    public void addModID(ModID modID) {
        var list = new ArrayList<>(Arrays.stream(modIds).toList());
        list.add(modID);
        modIds = list.toArray(ModID[]::new);
    }

    // TODO find a better way to do this. Same with the unique translated name stuff.
    @Override
    public Module combine(Module existingModule) {
        assert existingModule instanceof TranslationModule;

        var list = new ArrayList<>(Arrays.stream(((TranslationModule) existingModule).modIds).toList());
        if (!list.contains(modIds[0])) list.add(modIds[0]);
        modIds = list.toArray(ModID[]::new);

        return this;
    }

    @Override
    public Class<? extends Module> getModuleType() {
        return TranslationModule.class;
    }

    public String getTranslationKey() {
        return translationKey;
    }

    public String getTranslatedName() {
        return I18n.translate(getTranslationKey());
    }

    public String getTranslatedName(String originalTooltip, String form) {
        var uniqueForm = getUniqueTranslatedName(form);
        return uniqueForm.orElseGet(() -> String.format(originalTooltip, getTranslatedName()));
    }

    /**
     * @return Returns empty if there is no unique translated name for this form. Present optional represents a full translated string.
     */
    public Optional<String> getUniqueTranslatedName(String form) {
        for (ModID modId : modIds) {
            var searchingFor = "item." + modId + ":" + materialName + "_" + form + ".name";
            var uniqueName = I18n.translate(searchingFor);
            if (!uniqueName.equals(searchingFor)) {
                return Optional.of(uniqueName);
            }
        }
        return Optional.empty();
    }
}
