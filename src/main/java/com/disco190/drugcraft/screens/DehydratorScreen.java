package com.disco190.drugcraft.screens;

import com.disco190.drugcraft.menu.DehydratorMenu;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;

public class DehydratorScreen extends AbstractContainerScreen<DehydratorMenu> {

    private static final ResourceLocation TEXTURE = new ResourceLocation("drugcraft", "textures/gui/dehydrator.png");
    private static final ResourceLocation ARROW = new ResourceLocation("drugcraft", "textures/gui/burn_progress.png");
    private static final ResourceLocation LIT = new ResourceLocation("drugcraft", "textures/gui/lit_progress.png");

    public DehydratorScreen(DehydratorMenu menu, Inventory inv, Component title) {
        super(menu, inv, title);
        this.imageWidth = 176;
        this.imageHeight = 166;
    }

    @Override
    protected void renderBg(GuiGraphics gui, float partialTick, int mouseX, int mouseY) {
        // 1. Dibujar el fondo (el cuadrado gris de la GUI)
        gui.blit(TEXTURE, leftPos, topPos, 0, 0, imageWidth, imageHeight);

        // 2. Dibujar el FUEGO (usando lit_progress.png)
        if (menu.isLit()) {
            int fuelHeight = (int) (14.0F * menu.getLitTime() / menu.getMaxLitTime());
            if (fuelHeight > 0) {
                // Cambiamos a la textura del fuego
                // Posición: x=56, y=36 (donde va el fuego en el horno)
                // El '14, 14' final es el tamaño total del archivo lit_progress.png
                gui.blit(LIT, leftPos + 56, topPos + 36 + 14 - fuelHeight,
                        0, 14 - fuelHeight, 14, fuelHeight, 14, 14);
            }
        }

        // 3. Dibujar la FLECHA (usando burn_progress.png)
        if (menu.getMaxProgress() > 0) {
            int progressWidth = (int) (24.0F * menu.getProgress() / menu.getMaxProgress());
            if (progressWidth > 0) {
                // Cambiamos a la textura de la flecha
                // Posición: x=79, y=34 (donde va la flecha)
                // El '24, 17' final es el tamaño total del archivo burn_progress.png
                gui.blit(ARROW, leftPos + 79, topPos + 34,
                        0, 0, progressWidth, 17, 24, 17);
            }
        }
    }

    @Override
    public void render(GuiGraphics gui, int mouseX, int mouseY, float partialTick) {
        this.renderBackground(gui);
        super.render(gui, mouseX, mouseY, partialTick);
        this.renderTooltip(gui, mouseX, mouseY);
    }
}
