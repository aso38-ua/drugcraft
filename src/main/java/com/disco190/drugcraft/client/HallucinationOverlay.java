package com.disco190.drugcraft.client;

import com.disco190.drugcraft.effects.ModEffects;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.awt.Color;

@Mod.EventBusSubscriber(value = Dist.CLIENT)
public class HallucinationOverlay {

    @SubscribeEvent
    public static void onRenderOverlay(RenderGuiOverlayEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();

        if (mc.player != null && mc.player.hasEffect(ModEffects.HALLUCINATION.get())) {
            int amplifier = mc.player.getEffect(ModEffects.HALLUCINATION.get()).getAmplifier();

            // Tiempo del juego para animar
            float time = (mc.level.getGameTime() % 200) / 200.0f;

            // Colores dinámicos (cambia con el tiempo)
            float hue = (time + amplifier * 0.2f) % 1.0f; // rueda de color
            Color rgb = Color.getHSBColor(hue, 0.6f, 0.8f);

            // Transparencia según nivel
            int alpha = (20 + amplifier * 25) << 24; // 20-70 de alpha
            float pulse = (float)(0.5 + 0.5 * Math.sin(mc.level.getGameTime() * 0.05));
            int dynamicAlpha = (int)(alpha * pulse);
            int color = (rgb.getRed() << 16) | (rgb.getGreen() << 8) | rgb.getBlue() | dynamicAlpha;

            GuiGraphics gui = event.getGuiGraphics();

            // Dibujar un rectángulo por encima de toda la pantalla
            RenderSystem.disableDepthTest();
            RenderSystem.enableBlend();
            gui.fillGradient(0, 0, mc.getWindow().getGuiScaledWidth(), mc.getWindow().getGuiScaledHeight(),
                    color, (color & 0x00FFFFFF) | ((alpha / 2) << 24));

            RenderSystem.disableBlend();
            RenderSystem.enableDepthTest();
        }
    }
}
