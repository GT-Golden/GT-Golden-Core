package com.github.gtgolden.gtgoldencore.machines.api.item;

import net.minecraft.client.render.Tessellator;
import net.minecraft.client.render.TextRenderer;
import net.minecraft.client.render.entity.ItemRenderer;
import net.minecraft.client.texture.TextureManager;
import net.minecraft.item.ItemInstance;
import net.minecraft.util.io.CompoundTag;
import net.modificationstation.stationapi.api.client.gui.CustomItemOverlay;
import org.lwjgl.opengl.GL11;

public interface HasPowerBar extends CustomItemOverlay, ItemWithPowerStorage {
    @Override
    default void renderItemOverlay(ItemRenderer itemRenderer, int itemX, int itemY, ItemInstance item, TextRenderer textRenderer, TextureManager textureManager) {
        CompoundTag nbt = item.getStationNBT();
        int barOffset = nbt.getInt("damage") > 0 ? 2 : 0;

        int barLength = (int) Math.round((((double) getPower(item) / (double) getMaxPower(item)) * 13));
        int colorOffset = 255 - (int) Math.round((((double) getPower(item) / (double) getMaxPower(item)) * 225));
        GL11.glDisable(2896);
        GL11.glDisable(2929);
        GL11.glDisable(3553);
        Tessellator var8 = Tessellator.INSTANCE;
        int barColour = Math.max((colorOffset / 8) - 130, 100) << 16 | (233 - colorOffset) << 8 | 255 - (colorOffset / 4);
        int backgroundColour = (255 - colorOffset) / 4 << 16 | 16128;
        method_1485(var8, itemX + 2, itemY + 13 - barOffset, 13, 2, 0);
        method_1485(var8, itemX + 2, itemY + 13 - barOffset, 12, 1, backgroundColour);
        method_1485(var8, itemX + 2, itemY + 13 - barOffset, barLength, 1, barColour);
        GL11.glEnable(3553);
        GL11.glEnable(2896);
        GL11.glEnable(2929);
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
    }

    default void method_1485(Tessellator arg, int i, int j, int k, int i1, int i2) {
        arg.start();
        arg.colour(i2);
        arg.addVertex(i, j, 0.0D);
        arg.addVertex(i, j + i1, 0.0D);
        arg.addVertex(i + k, j + i1, 0.0D);
        arg.addVertex(i + k, j, 0.0D);
        arg.draw();
    }
}
