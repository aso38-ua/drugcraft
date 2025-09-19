package com.disco190.drugcraft.client;

import com.disco190.drugcraft.Drugcraft;
import com.disco190.drugcraft.effects.ModEffects;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = Drugcraft.MODID)
public class SmokeOverlay {

    private static final String[] FRAMES = {
            "1","2","3","4","5","6","7","8"
    };

    private static int currentFrame = 0;
    private static long lastTick = -1;
    private static final int FRAME_CHANGE_TICKS = 2; // cambia cada 10 ticks (~0.5 seg si 20 TPS)

    @SubscribeEvent
    public static void onRenderOverlay(RenderGuiOverlayEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.level == null) return;

        if (mc.player.hasEffect(ModEffects.SMOKED.get())) { // supongamos que tu efecto se llama SMOKED
            long tick = mc.level.getGameTime();

            // Cambiar frame cada FRAME_CHANGE_TICKS
            if (lastTick < 0 || tick - lastTick >= FRAME_CHANGE_TICKS) {
                lastTick = tick;
                currentFrame = (currentFrame + 1) % FRAMES.length;
            }

            String frame = FRAMES[currentFrame];
            ResourceLocation texture = new ResourceLocation(Drugcraft.MODID, "textures/gui/smoke/" + frame + ".png");

            GuiGraphics gui = event.getGuiGraphics();

            RenderSystem.disableDepthTest();
            RenderSystem.enableBlend();

            float alpha = 0.03f; // casi transparente
            RenderSystem.setShaderColor(1f, 1f, 1f, alpha);

            gui.blit(texture, 0, 0,
                    0, 0,
                    mc.getWindow().getGuiScaledWidth(),
                    mc.getWindow().getGuiScaledHeight(),
                    mc.getWindow().getGuiScaledWidth(),
                    mc.getWindow().getGuiScaledHeight());

            RenderSystem.setShaderColor(1f,1f,1f,1f);
            RenderSystem.disableBlend();
            RenderSystem.enableDepthTest();
        } else {
            // Reset cuando se quita el efecto
            currentFrame = 0;
            lastTick = -1;
        }
    }
}
