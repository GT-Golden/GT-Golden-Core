package com.github.gtgolden.gtgoldencore.materials.impl;

import com.github.gtgolden.gtgoldencore.materials.api.module.Module;
import net.mine_diver.unsafeevents.Event;
import net.modificationstation.stationapi.api.registry.ModID;

public class MaterialRegistryEvent extends Event {
    public void registerModules(String materialName, Module... modules) {
        for (Module module : modules) {
            MaterialRegistry.registerModule(materialName, module);
        }
    }

    public void registerTranslationProvider(String materialName, ModID modID) {
        MaterialRegistry.getMaterial(materialName).ifPresent(material -> material.registerTranslationProvider(modID));
    }
}
