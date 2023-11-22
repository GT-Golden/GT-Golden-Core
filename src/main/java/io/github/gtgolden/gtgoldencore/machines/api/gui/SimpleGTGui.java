package io.github.gtgolden.gtgoldencore.machines.api.gui;

import io.github.gtgolden.gtgoldencore.GTGoldenCore;
import io.github.gtgolden.gtgoldencore.machines.api.block.items.HasItemStorage;
import io.github.gtgolden.gtgoldencore.machines.api.block.items.ItemStorage;
import io.github.gtgolden.gtgoldencore.machines.api.container.SimpleGTContainer;
import io.github.gtgolden.gtgoldencore.machines.api.slot.GTSlot;
import net.minecraft.client.gui.screen.container.ContainerBase;
import net.minecraft.entity.player.PlayerBase;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.level.Level;
import net.minecraft.tileentity.TileEntityBase;
import net.modificationstation.stationapi.api.gui.screen.container.GuiHelper;
import net.modificationstation.stationapi.api.util.Identifier;
import org.lwjgl.opengl.GL11;

public class SimpleGTGui extends ContainerBase {
    public static Identifier identifier = GTGoldenCore.NAMESPACE.id("simple_gui");

    public static void openGui(Level level, int x, int y, int z, PlayerBase player) {
        TileEntityBase tileEntity = level.getTileEntity(x, y, z);
        if (tileEntity instanceof HasItemStorage hasItemStorage) {
            openGui(player, hasItemStorage.getItemStorage());
        } else {
            GTGoldenCore.LOGGER.error("Called openGui() when there wasn't an item storage there");
        }
    }

    public static void openGui(PlayerBase player, ItemStorage itemStorage) {
        GuiHelper.openGUI(player, identifier, itemStorage, new SimpleGTContainer(player.inventory, itemStorage));
    }

    private final PlayerInventory playerInventory;
    private final ItemStorage itemStorage;

    public SimpleGTGui(PlayerInventory playerInventory, ItemStorage itemStorage) {
        super(new SimpleGTContainer(playerInventory, itemStorage));
        this.playerInventory = playerInventory;
        this.itemStorage = itemStorage;
    }

    @Override
    protected void renderContainerBackground(float f) {
        int n = minecraft.textureManager.getTextureId("/assets/gt-golden-core/stationapi/gui/base_gui.png");
        GL11.glColor4f(1.0f, 1.0f, 1.0f, 1.0f);
        minecraft.textureManager.bindTexture(n);
        int n3 = (width - containerWidth) / 2;
        int n4 = (height - containerHeight) / 2;
        blit(n3, n4, 0, 0, containerWidth, containerHeight);

        for (GTSlot slot : itemStorage.getSlots()) {
            blit(n3 + slot.x - 1, n4 + slot.y - 1, 176, 0, 18, 18);
        }
    }
}
