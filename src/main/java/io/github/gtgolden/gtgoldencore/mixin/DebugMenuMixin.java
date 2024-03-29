package io.github.gtgolden.gtgoldencore.mixin;

import io.github.gtgolden.gtgoldencore.GTGoldenCore;
import io.github.gtgolden.gtgoldencore.machines.api.block.items.ItemConnection;
import io.github.gtgolden.gtgoldencore.machines.api.block.items.ItemIO;
import io.github.gtgolden.gtgoldencore.machines.api.block.power.HasPowerIO;
import io.github.gtgolden.gtgoldencore.machines.api.block.power.HasPowerStorage;
import io.github.gtgolden.gtgoldencore.machines.api.slot.GTSlot;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.InGame;
import net.minecraft.client.render.TextRenderer;
import net.minecraft.client.util.ScreenScaler;
import net.minecraft.util.hit.HitResult;
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

    @Unique
    private final String GOLD = "§6";

    @Unique
    private final String GREEN = "§a";

    @Unique
    private final String RED = "§c";

    @Unique
    private final String BLUE = "§9";

    @Unique
    private final String WHITE = "§f";

    @Unique
    private final String LIGHT_PURPLE = "§d";

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
        if (!(tileEntity instanceof ItemIO) && !(tileEntity instanceof ItemConnection) && !(tileEntity instanceof HasPowerIO))
            return;

        printGTText(textRenderer, 0, GOLD + "GT-Golden Data:");

        var side = Direction.values()[hit.field_1987];
        printGTText(textRenderer, 1, GREEN + "Looking at side: " + WHITE + side + "(" + hit.field_1987 + ")");
        if (tileEntity instanceof HasPowerStorage powerStorage) {
            printGTText(textRenderer, 1, GREEN + "Power storage found:");
            if (powerStorage.isPowerInput(side)) printGTText(textRenderer, 2, RED + "Is power input");
            if (powerStorage.isPowerOutput(side)) printGTText(textRenderer, 2, RED + "Is power output");

            printGTText(textRenderer, 2, BLUE + "Max power level: " + WHITE + powerStorage.getMaxPower());
            printGTText(textRenderer, 2, BLUE + "Power level: " + WHITE + powerStorage.getPower());
        }
        if (tileEntity instanceof ItemConnection itemConnection) {
            printGTText(textRenderer, 1, GREEN + " Item connections found:");
            if (itemConnection.isItemInput(side)) printGTText(textRenderer, 2, RED + "Is item input");
            if (itemConnection.isItemOutput(side)) printGTText(textRenderer, 2, RED + "Is item output");
        }
        if (tileEntity instanceof ItemIO itemStorage) {
            printGTText(textRenderer, 1, GREEN + " Item storage found:");
            GTSlot[] slots = itemStorage.getSlots();
            for (int k = 0, slotsLength = slots.length; k < slotsLength; k++) {
                GTSlot slot = slots[k];
                var item = slot.getItem();
                if (item == null) continue;
                printGTText(textRenderer, 3, LIGHT_PURPLE + k + ". " + WHITE + item.count + "x" + item.getTranslationKey());
            }
        }
    }

    @Unique
    private void printGTText(TextRenderer textRenderer, int indent, String text) {
        textRenderer.drawTextWithShadow(text, HORIZONTAL_OFFSET + (indent * INDENT_OFFSET), y, 0);
        y += TEXT_SPACING;
    }
}
