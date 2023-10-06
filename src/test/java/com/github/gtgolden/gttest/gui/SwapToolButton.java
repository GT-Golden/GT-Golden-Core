package com.github.gtgolden.gttest.gui;

import net.minecraft.client.gui.widgets.Button;

public class SwapToolButton extends Button {
    public SwapToolButton(int i, int j, int k, String string) {
        super(i, j, k, string);
        height = height / 2;
        width = width / 2;
    }
}
