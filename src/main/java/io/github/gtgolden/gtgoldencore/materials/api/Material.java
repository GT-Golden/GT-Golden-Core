package io.github.gtgolden.gtgoldencore.materials.api;

import io.github.gtgolden.gtgoldencore.GTGoldenCore;
import io.github.gtgolden.gtgoldencore.materials.GTMaterials;
import io.github.gtgolden.gtgoldencore.materials.api.module.Module;
import net.minecraft.client.resource.language.I18n;
import net.modificationstation.stationapi.api.util.Identifier;
import net.modificationstation.stationapi.api.util.Namespace;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class Material {
    @NotNull
    public String name;
    public HashMap<Class<? extends Module>, Module> modules = new HashMap<>();

    public Material(@NotNull String name) {
        this.name = name;
        identifier = GTGoldenCore.NAMESPACE.id(name);
    }

    public Material addModule(Module module) {
        if (modules.containsKey(module.getModuleType())) {
            modules.put(module.getModuleType(), module.combine(modules.get(module.getModuleType())));
        } else {
            modules.put(module.getModuleType(), module);
        }

        return this;
    }

    public <T extends Module> boolean hasModule(Class<T> module) {
        return modules.containsKey(module);
    }

    public <T extends Module> @Nullable T getModule(Class<T> module) {
        //noinspection unchecked
        return (T) modules.get(module);
    }

    protected Namespace[] translationProviders = new Namespace[]{};
    protected Identifier identifier;

    public @NotNull Material registerTranslationProvider(Namespace namespace) {
        var newTranslationProviders = new ArrayList<>(List.of(translationProviders));
        if (!newTranslationProviders.contains(namespace)) {
            newTranslationProviders.add(namespace);
            translationProviders = newTranslationProviders.toArray(Namespace[]::new);
        }
        return this;
    }

    public @NotNull Material overrideIdentifier(Identifier identifier) {
        GTMaterials.LOGGER.info("Material " + name + "'s identifier has been overriden to " + identifier.toString());
        this.identifier = identifier;
        return this;
    }

    public @NotNull Identifier getIdentifier() {
        return identifier;
    }

    public @NotNull String getTranslationKey() {
        return "material." + identifier + ".name";
    }

    public @NotNull String getTranslatedName() {
        return I18n.translate(getTranslationKey());
    }

    /**
     * @return Returns null if there isn't a unique translated name for this form.
     */
    public @Nullable String getUniqueTranslatedName(String form) {
        for (Namespace namespace : translationProviders) {
            var searchingFor = "item." + namespace + ":" + name + "_" + form + ".name";
            var uniqueName = I18n.translate(searchingFor);
            if (!uniqueName.equals(searchingFor)) {
                return uniqueName;
            }
        }
        return null;
    }

    public @NotNull String getTranslatedName(String originalTooltip, String form) {
        var uniqueForm = getUniqueTranslatedName(form);
        if (uniqueForm == null) {
            return originalTooltip.formatted(getAffix());
        } else {
            return uniqueForm;
        }
    }

    public @NotNull String getAffix() {
        var translation = I18n.translate("material." + identifier + ".affix");
        if (Objects.equals(translation, "material." + identifier + ".affix")) {
            return getTranslatedName();
        } else {
            return translation;
        }
    }
}