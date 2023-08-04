package com.github.gtgolden.gttest.mixin;

import com.github.gtgolden.gtgoldencore.machines.HasItemIO;
import com.github.gtgolden.gtgoldencore.machines.HasItemStorage;
import com.github.gtgolden.gtgoldencore.machines.HasPowerStorage;
import com.github.gtgolden.gtgoldencore.machines.SlotType;
import com.github.gtgolden.gttest.GtTest;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.InGame;
import net.minecraft.client.render.TextRenderer;
import net.minecraft.client.util.ScreenScaler;
import net.minecraft.util.hit.HitResult;
import net.modificationstation.stationapi.api.util.Colours;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(InGame.class)
public class DebugMonitorOverlayMixin {
    @Unique
    private static final int VERTICAL_OFFSET = 0;
    @Unique
    private static final int DEBUG_VERTICAL_OFFSET = 108;
    @Unique
    private static final int TEXT_SPACING = 12;
    @Shadow private Minecraft minecraft;

    @Inject(
            method="renderHud(FZII)V",
            at=@At("RETURN"),
            locals = LocalCapture.CAPTURE_FAILHARD
    )
    private void renderDebugMonitor(float f, boolean flag, int i, int j, CallbackInfo ci, ScreenScaler var5, int var6, int var7, TextRenderer var8) {
        var heldItem = minecraft.player.getHeldItem();
        if (heldItem == null || heldItem.getType() != GtTest.DEBUG_MONITOR) return;
        int y = this.minecraft.options.debugHud ? DEBUG_VERTICAL_OFFSET : VERTICAL_OFFSET;
        var8.drawTextWithShadow(Colours.GOLD + "Debug Monitor Data:", 0, y, 0);
        y += TEXT_SPACING;
        HitResult hit = minecraft.hitResult;
        if (hit == null) return;
        var tileEntity = minecraft.level.getTileEntity(hit.x, hit.y, hit.z);
        if (tileEntity == null) return;
        if (tileEntity instanceof HasPowerStorage powerStorage) {
            var8.drawTextWithShadow(Colours.GREEN + " Power storage found: ", 0, y, 0);
            y += TEXT_SPACING;
            var8.drawTextWithShadow(Colours.BLUE + "  Max power level: " + Colours.WHITE + powerStorage.getMaxPower(), 0, y, 0);
            y += TEXT_SPACING;
            var8.drawTextWithShadow(Colours.BLUE + "  Power level: " + Colours.WHITE + powerStorage.getPower(), 0, y, 0);
            y += TEXT_SPACING;
        }
        if (tileEntity instanceof HasItemIO itemStorage) {
            var8.drawTextWithShadow(Colours.GREEN + " Item storage found: ", 0, y, 0);
            y += TEXT_SPACING;
            for (SlotType type: SlotType.values()) {
                int size = itemStorage.getInventorySize(type);
                if (size <= 0) continue;
                var8.drawTextWithShadow(Colours.BLUE + "  " + type.name() + Colours.DARK_PURPLE + " (" + size + ")" + ": ", 0, y, 0);
                y += TEXT_SPACING;
                for (int slot = 0; slot < size; slot++) {
                    var item = itemStorage.getInventoryItem(type, slot);
                    if (item != null) {
                        var8.drawTextWithShadow(Colours.LIGHT_PURPLE + "   " + slot + ". " + Colours.WHITE + item.count + "x" + item.getTranslationKey(), 0, y, 0);
                        y += TEXT_SPACING;
                    }
                }
            }
        }
    }
}