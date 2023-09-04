package com.github.gtgolden.gtgoldencore.materials.api.module;

import java.awt.*;

public class ColorModule implements Module {
    private final Color color;

    public ColorModule(Color color) {
        this.color = color;
    }

    @Override
    public String getModuleType() {
        return "color";
    }

    public Color getColor() {
        return color;
    }

    public int getRGB() {
        return color.getRGB();
    }

    public int getR() {
        return color.getRed();
    }

    public int getG() {
        return color.getGreen();
    }
    public int getB() {
        return color.getBlue();
    }
}
