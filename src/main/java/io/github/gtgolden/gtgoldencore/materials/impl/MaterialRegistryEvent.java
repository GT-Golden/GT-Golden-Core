package io.github.gtgolden.gtgoldencore.materials.impl;

import io.github.gtgolden.gtgoldencore.GTGoldenCore;
import io.github.gtgolden.gtgoldencore.materials.api.module.Module;
import net.mine_diver.unsafeevents.Event;
import net.modificationstation.stationapi.api.util.Namespace;

public class MaterialRegistryEvent extends Event {
    public void registerModules(String materialName, Module... modules) {
        for (Module module : modules) {
            MaterialRegistry.registerModule(materialName, module);
        }
    }

    public void registerTranslationProvider(String materialName, Namespace namespace) {
        var material = MaterialRegistry.getMaterial(materialName);
        if (material == null) {
            GTGoldenCore.LOGGER.warn("Mod " + namespace + " attempted to register a translation provider for nonexistant material " + materialName + '.');
            return;
        }
        material.registerTranslationProvider(namespace);
    }
}
