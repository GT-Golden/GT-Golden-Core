package io.github.gtgolden.gtgoldencore.materials.api.module;

import net.minecraft.client.resource.language.I18n;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.util.Namespace;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import java.util.Optional;

public class TranslationModule implements Module {
    private final Identifier identifier;
    protected Namespace[] namespaces;
    protected String materialName;

    public TranslationModule(Identifier identifier) {
        namespaces = new Namespace[]{identifier.namespace};
        materialName = identifier.path;
        this.identifier = identifier;
    }

    public void addNamespace(Namespace namespace) {
        var list = new ArrayList<>(Arrays.stream(namespaces).toList());
        list.add(namespace);
        namespaces = list.toArray(Namespace[]::new);
    }

    // TODO find a better way to do this. Same with the unique translated name stuff.
    @Override
    public Module combine(Module existingModule) {
        assert existingModule instanceof TranslationModule;

        var list = new ArrayList<>(Arrays.stream(((TranslationModule) existingModule).namespaces).toList());
        if (!list.contains(namespaces[0])) list.add(namespaces[0]);
        namespaces = list.toArray(Namespace[]::new);

        return this;
    }

    @Override
    public Class<? extends Module> getModuleType() {
        return TranslationModule.class;
    }

    public String getTranslationKey() {
        return "material." + identifier + ".name";
    }

    public String getTranslatedName() {
        return I18n.translate(getTranslationKey());
    }

    public String getAffix() {
        var translation = I18n.translate("material." + identifier + ".affix");
        if (Objects.equals(translation, "material." + identifier + ".affix")) {
            return getTranslatedName();
        } else {
            return translation;
        }
    }

    public String getTranslatedName(String originalTooltip, String form) {
        var uniqueForm = getUniqueTranslatedName(form);
        return uniqueForm.orElseGet(() -> originalTooltip.formatted(getAffix()));
    }

    /**
     * @return Returns empty if there is no unique translated name for this form. Present optional represents a full translated string.
     */
    public Optional<String> getUniqueTranslatedName(String form) {
        for (Namespace namespace : namespaces) {
            var searchingFor = "item." + namespace + ":" + materialName + "_" + form + ".name";
            var uniqueName = I18n.translate(searchingFor);
            if (!uniqueName.equals(searchingFor)) {
                return Optional.of(uniqueName);
            }
        }
        return Optional.empty();
    }
}
