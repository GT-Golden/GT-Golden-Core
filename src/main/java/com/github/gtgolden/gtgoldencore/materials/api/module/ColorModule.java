package com.github.gtgolden.gtgoldencore.materials.api.module;

import java.awt.*;

public class ColorModule implements Module {
    public static final ColorModule defaultModule = new ColorModule(Color.WHITE);

    protected final Color color;

    public ColorModule(Color color) {
        this.color = color;
    }

    @Override
    public Class<? extends Module> getModuleType() {
        return ColorModule.class;
    }

    public Color getColor() {
        return color;
    }

    public Color getColor(int index) {
        return color;
    }
}