package com.disco190.drugcraft.screens;

import com.disco190.drugcraft.menu.ChemistryStationMenu;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Inventory;


public class ChemistryStationScreen extends AbstractContainerScreen<ChemistryStationMenu> {

    private static final ResourceLocation BURN_PROGRESS = new ResourceLocation("drugcraft", "textures/gui/burn_progress.png");

    private static final ResourceLocation TEXTURE =
            new ResourceLocation("drugcraft", "textures/gui/chemistry_station.png");

    public ChemistryStationScreen(ChemistryStationMenu menu, Inventory inv, Component title) {
        super(menu, inv, title);
        this.imageWidth = 176;  // ancho de la GUI
        this.imageHeight = 166; // alto de la GUI
    }

    @Override
    protected void renderBg(GuiGraphics guiGraphics, float partialTicks, int mouseX, int mouseY) {
        // 1️⃣ Dibuja el fondo de la GUI
        guiGraphics.blit(TEXTURE, leftPos, topPos, 0, 0, imageWidth, imageHeight);

        // 2️⃣ Flecha de progreso
        int arrowX = leftPos + 62;
        int arrowY = topPos + 34;
        int arrowWidth = 24;  // ancho total de la flecha
        int arrowHeight = 17; // alto

        float progress = (float) menu.getCookTime() / menu.getCookTimeTotal();
        int filledWidth = (int) (arrowWidth * progress);

        if (filledWidth > 0) {
            // Dibuja solo la parte llenada de la flecha
            guiGraphics.blit(BURN_PROGRESS, arrowX, arrowY, 0, 0, filledWidth, arrowHeight);

        }
    }



    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(guiGraphics);
        super.render(guiGraphics, mouseX, mouseY, partialTicks);
        this.renderTooltip(guiGraphics, mouseX, mouseY);
    }
}
