package com.github.gtgolden.gtgoldencore.materials.impl;

import com.github.gtgolden.gtgoldencore.materials.api.module.Module;
import net.mine_diver.unsafeevents.Event;

public class MaterialRegistryEvent extends Event {
    public void registerModules(String materialName, Module... modules) {
        for (Module module : modules) {
            MaterialRegistry.registerModule(materialName, module);
        }
    }
}
