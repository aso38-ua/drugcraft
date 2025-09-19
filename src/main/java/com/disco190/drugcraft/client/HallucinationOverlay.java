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

import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber(value = Dist.CLIENT, modid = Drugcraft.MODID)
public class HallucinationOverlay {

    private static final Map<Integer, String[][]> FRAME_SETS = new HashMap<>();

    // Variables de control del bucle
    private static int currentSubSet = 0;
    private static int currentFrame = 0;
    private static int currentRepetition = 0;
    private static long lastTick = -1;

    private static final int FRAMES_PER_TICK = 1; // velocidad de cambio de frame
    private static final int REPETITIONS_PER_SET = 14; // cuántas veces repetir un set completo

    static {
        // Nivel 1
        FRAME_SETS.put(0, new String[][]{
                {"mushroom_1","mushroom_2","mushroom_3","mushroom_4","mushroom_5"}
        });

        // Nivel 2 → varios sub-sets
        FRAME_SETS.put(1, new String[][]{
                {"lsd1_1","lsd1_2","lsd1_3","lsd1_4","lsd1_5",
                        "lsd1_6","lsd1_7","lsd1_8","lsd1_9","lsd1_10"},
                {"lsd3_1","lsd3_2","lsd3_3","lsd3_4","lsd3_5","lsd3_6","lsd3_7","lsd3_8","lsd3_9"},
                {"lsd4_1","lsd4_2","lsd4_3","lsd4_4","lsd4_5","lsd4_6","lsd4_7","lsd4_8","lsd4_9","lsd4_10",
                        "lsd4_11","lsd4_12","lsd4_13","lsd4_14","lsd4_15","lsd4_16","lsd4_17","lsd4_18","lsd4_19","lsd4_20",
                        "lsd4_21","lsd4_22"}
        });

        // Nivel 3
        FRAME_SETS.put(2, new String[][]{
                {"lsd5_1","lsd5_2","lsd5_3","lsd5_4","lsd5_5","lsd5_6","lsd5_7","lsd5_8","lsd5_9","lsd5_10",
                        "lsd5_11","lsd5_12","lsd5_13","lsd5_14","lsd5_15","lsd5_16","lsd5_17","lsd5_18","lsd5_19","lsd5_20","lsd5_21"}
        });
    }

    @SubscribeEvent
    public static void onRenderOverlay(RenderGuiOverlayEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.level == null) return;
        if (!mc.player.hasEffect(ModEffects.HALLUCINATION.get())) {
            // Reset
            currentSubSet = 0;
            currentFrame = 0;
            currentRepetition = 0;
            lastTick = -1;
            return;
        }

        int amplifier = mc.player.getEffect(ModEffects.HALLUCINATION.get()).getAmplifier();
        String[][] subSets = FRAME_SETS.getOrDefault(amplifier, FRAME_SETS.get(0));
        if (subSets.length == 0) return;

        long tick = mc.level.getGameTime();

        // Actualizar frame cada tick
        if (lastTick < 0 || tick > lastTick) {
            lastTick = tick;
            currentFrame += FRAMES_PER_TICK;

            String[] currentSetFrames = subSets[currentSubSet];
            if (currentFrame >= currentSetFrames.length) {
                currentFrame = 0;
                currentRepetition++;

                if (currentRepetition >= REPETITIONS_PER_SET) {
                    currentRepetition = 0;
                    currentSubSet = (currentSubSet + 1) % subSets.length;
                }
            }
        }

        String frame = subSets[currentSubSet][currentFrame];
        ResourceLocation texture = new ResourceLocation(Drugcraft.MODID, "textures/gui/hallucination/" + frame + ".png");

        GuiGraphics gui = event.getGuiGraphics();

        RenderSystem.disableDepthTest();
        RenderSystem.enableBlend();
        float alpha = 0.5f + 0.25f * amplifier;
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
    }
}
