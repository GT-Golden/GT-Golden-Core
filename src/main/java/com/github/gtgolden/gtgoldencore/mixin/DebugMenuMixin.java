package com.github.gtgolden.gtgoldencore.mixin;

import com.github.gtgolden.gtgoldencore.GTGoldenCore;
import com.github.gtgolden.gtgoldencore.machines.api.block.items.HasItemIO;
import com.github.gtgolden.gtgoldencore.machines.api.block.items.SlotType;
import com.github.gtgolden.gtgoldencore.machines.api.block.power.HasPowerIO;
import com.github.gtgolden.gtgoldencore.machines.api.block.power.HasPowerStorage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.InGame;
import net.minecraft.client.render.TextRenderer;
import net.minecraft.client.util.ScreenScaler;
import net.minecraft.util.hit.HitResult;
import net.modificationstation.stationapi.api.util.Colours;
import net.modificationstation.stationapi.api.util.math.Direction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(InGame.class)
public class DebugMenuMixin {
    @Unique
    private static final int HORIZONTAL_OFFSET = 2;
    @Unique
    private static final int INDENT_OFFSET = 4;
    @Unique
    private static final int VERTICAL_OFFSET = 112;
    @Unique
    private static final int TEXT_SPACING = 12;
    @Unique
    private static int y = 0;
    @Shadow
    private Minecraft minecraft;

    @Inject(
            method = "renderHud(FZII)V",
            at = @At("RETURN"),
            locals = LocalCapture.CAPTURE_FAILHARD
    )
    private void renderGTDebugInfo(float f, boolean flag, int i, int j, CallbackInfo ci, ScreenScaler screenScaler, int screenWidth, int screenHeight, TextRenderer textRenderer) {
        if (!GTGoldenCore.config.debugMenuInfo || !minecraft.options.debugHud) return;

        HitResult hit = minecraft.hitResult;
        if (hit == null) return;
        y = VERTICAL_OFFSET;
        var tileEntity = minecraft.level.getTileEntity(hit.x, hit.y, hit.z);
        if (!(tileEntity instanceof HasItemIO) && !(tileEntity instanceof HasPowerIO)) return;

        printGTText(textRenderer, 0, Colours.GOLD + "GT-Golden Data:");

        var side = Direction.values()[hit.field_1987];
        printGTText(textRenderer, 1, Colours.GREEN + "Looking at side: " + Colours.WHITE + side + "(" + hit.field_1987 + ")");
        if (tileEntity instanceof HasPowerStorage powerStorage) {
            printGTText(textRenderer, 1, Colours.GREEN + "Power storage found:");
            if (powerStorage.isPowerInput(side)) printGTText(textRenderer, 2, Colours.RED + "Is power input");
            if (powerStorage.isPowerOutput(side)) printGTText(textRenderer, 2, Colours.RED + "Is power output");

            printGTText(textRenderer, 2, Colours.BLUE + "Max power level: " + Colours.WHITE + powerStorage.getMaxPower());
            printGTText(textRenderer, 2, Colours.BLUE + "Power level: " + Colours.WHITE + powerStorage.getPower());
        }
        if (tileEntity instanceof HasItemIO itemStorage) {
            printGTText(textRenderer, 1, Colours.GREEN + " Item storage found:");
            if (itemStorage.isItemInput(side)) printGTText(textRenderer, 2, Colours.RED + "Is item input");
            if (itemStorage.isItemOutput(side)) printGTText(textRenderer, 2, Colours.RED + "Is item output");

            for (SlotType type : SlotType.values()) {
                int size = itemStorage.getInventorySize(type);
                if (size <= 0) continue;
                printGTText(textRenderer, 2, Colours.BLUE + type.name() + Colours.DARK_PURPLE + " (" + size + ")" + ":");
                for (int slot = 0; slot < size; slot++) {
                    var item = itemStorage.getInventoryItem(type, slot);
                    if (item != null) {
                        printGTText(textRenderer, 3, Colours.LIGHT_PURPLE.toString() + slot + ". " + Colours.WHITE + item.count + "x" + item.getTranslationKey());
                    }
                }
            }
        }
    }

    @Unique
    private void printGTText(TextRenderer textRenderer, int indent, String text) {
        textRenderer.drawTextWithShadow(text, HORIZONTAL_OFFSET + (indent * INDENT_OFFSET), y, 0);
        y += TEXT_SPACING;
    }
}
