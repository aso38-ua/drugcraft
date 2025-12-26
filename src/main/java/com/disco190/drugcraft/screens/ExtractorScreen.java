package com.disco190.drugcraft.screens;

import com.disco190.drugcraft.menu.ExtractorMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class ExtractorScreen extends AbstractContainerScreen<ExtractorMenu> {

    private static final ResourceLocation TEXTURE =
            new ResourceLocation("drugcraft", "textures/gui/extractor.png");

    private static final ResourceLocation ARROW =
            new ResourceLocation("drugcraft", "textures/gui/burn_progress.png");

    public ExtractorScreen(ExtractorMenu menu, Inventory inv, Component title) {
        super(menu, inv, title);
        this.imageWidth = 176;
        this.imageHeight = 166;
    }

    @Override
    protected void renderBg(GuiGraphics gui, float partialTick, int mouseX, int mouseY) {
        // Fondo
        gui.blit(TEXTURE, leftPos, topPos, 0, 0, imageWidth, imageHeight);

        // Flecha de progreso
        int arrowX = leftPos + 82;
        int arrowY = topPos + 40;
        int arrowWidth = 24;
        int arrowHeight = 17;

        float progress = (float) menu.getProgress() / menu.getMaxProgress();
        int filled = (int) (arrowWidth * progress);

        if (filled > 0) {
            gui.blit(
                    ARROW,
                    arrowX,
                    arrowY,
                    0,
                    0,
                    filled,
                    arrowHeight,
                    arrowWidth,
                    arrowHeight
            );
        }
    }

    @Override
    public void render(GuiGraphics gui, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(gui);
        super.render(gui, mouseX, mouseY, partialTick);
        this.renderTooltip(gui, mouseX, mouseY);
    }
}
