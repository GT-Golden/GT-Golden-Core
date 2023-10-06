package com.github.gtgolden.gttest.gui;

import com.github.gtgolden.gtgoldencore.machines.api.item.ItemInventoryBase;
import com.github.gtgolden.gttest.container.ContainerPowerTool;
import com.github.gtgolden.gttest.item.TestPowerTool;
import net.minecraft.client.gui.screen.container.ContainerBase;
import net.minecraft.client.gui.widgets.Button;
import net.minecraft.entity.player.PlayerInventory;
import org.lwjgl.opengl.GL11;

public class GuiPowerTool extends ContainerBase {
    private final ItemInventoryBase powerToolInventory;
    private final PlayerInventory playerInventory;

    private Button swapToolButton;

    public GuiPowerTool(PlayerInventory playerInventory, ItemInventoryBase powerToolInventory) {
        super(new ContainerPowerTool(playerInventory, powerToolInventory));
        this.playerInventory = playerInventory;
        this.powerToolInventory = powerToolInventory;
    }

    @Override
    public void init() {
        buttons.add(swapToolButton = new SwapToolButton(0, ((this.width - this.containerWidth) / 2) + 60, ((this.height - this.containerHeight) / 2) + 68, "Swap Tool"));
        super.init();
    }

    @Override
    protected void renderForeground() {
        this.textManager.drawText(powerToolInventory.getContainerName(), 8, 6, 0x404040);
        this.textManager.drawText(playerInventory.getContainerName(), 8, containerHeight - 96 + 2, 0x404040);
    }

    @Override
    public void renderContainerBackground(float f) {
        int n = minecraft.textureManager.getTextureId("/assets/gt-test/stationapi/gui/power_tool.png");
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        this.minecraft.textureManager.bindTexture(n);
        int n3 = (width - containerWidth) / 2;
        int n4 = (height - containerHeight) / 2;
        this.blit(n3, n4, 0, 0, containerWidth, containerHeight);

        if (TestPowerTool.getSelectedTool(powerToolInventory.itemInstance).equals("drill")) {
            blit(n3 + 166, n4 + 67, 176, 0, 10, 10);
        } else {
            blit(n3 + 166, n4 + 67, 176, 10, 10, 10);
        }
    }

    @Override
    public void onClose() {
        powerToolInventory.writeData();
        super.onClose();
    }

    @Override
    protected void buttonClicked(Button button) {
        if (button == swapToolButton) {
            var nbt = powerToolInventory.itemInstance.getStationNBT();
            if (TestPowerTool.getSelectedTool(powerToolInventory.itemInstance).equals("chainsaw")) {
                nbt.put("power_tool", "drill");
            } else {
                nbt.put("power_tool", "chainsaw");
            }
        }
        super.buttonClicked(button);
    }
}
