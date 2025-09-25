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
public class DMTOverlay {

    private static final Map<Integer, String[][]> FRAME_SETS = new HashMap<>();
    private static final Map<Integer, int[]> REPETITIONS_PER_SUBSET = new HashMap<>();

    private static int currentSubSet = 0;
    private static int currentFrame = 0;
    private static int currentRepetition = 0;
    private static double frameCounter = 0; // contador flotante para suavizar animación

    private static final int FRAMES_PER_TICK = 1;       // velocidad de cambio de frame
    private static final int DEFAULT_REPETITIONS = 10; // valor por defecto si no hay array definido

    static {
        FRAME_SETS.put(0, new String[][]{
                {"dmt1_1","dmt1_2","dmt1_3","dmt1_4","dmt1_5","dmt1_6","dmt1_7","dmt1_8","dmt1_9","dmt1_10"},
                {"dmt2_1","dmt2_2","dmt2_3","dmt2_4","dmt2_5","dmt2_6","dmt2_7","dmt2_8","dmt2_9","dmt2_10"},
                {"dmt3_1","dmt3_2","dmt3_3","dmt3_4","dmt3_5","dmt3_6","dmt3_7","dmt3_8","dmt3_9","dmt3_10"},
                {"dmt4_1","dmt4_2","dmt4_3","dmt4_4","dmt4_5","dmt4_6","dmt4_7","dmt4_8","dmt4_9","dmt4_10",
                        "dmt4_11","dmt4_12","dmt4_13","dmt4_14","dmt4_15","dmt4_16","dmt4_17","dmt4_18","dmt4_19","dmt4_20",
                        "dmt4_21","dmt4_22","dmt4_23","dmt4_24","dmt4_25","dmt4_26","dmt4_27"},
                {"dmt5_1","dmt5_2","dmt5_3","dmt5_4","dmt5_5","dmt5_6","dmt5_7","dmt5_8","dmt5_9"},
                {"dmt6_1","dmt6_2","dmt6_3","dmt6_4","dmt6_5","dmt6_6","dmt6_7","dmt6_8","dmt6_9"},
                {"dmt7_1","dmt7_2","dmt7_3","dmt7_4"},
                {"dmt22_1","dmt22_2","dmt22_3","dmt22_4","dmt22_5","dmt22_6","dmt22_7","dmt22_8","dmt22_9","dmt22_10",
                        "dmt22_11","dmt22_12","dmt22_13","dmt22_14","dmt22_15","dmt22_16","dmt22_17","dmt22_18","dmt22_19","dmt22_20",
                        "dmt22_21","dmt22_22","dmt22_23","dmt22_24","dmt22_25","dmt22_26","dmt22_27","dmt22_28","dmt22_29","dmt22_30",
                        "dmt22_31","dmt22_32","dmt22_33","dmt22_34","dmt22_35","dmt22_36","dmt22_37","dmt22_38","dmt22_39","dmt22_40",
                        "dmt22_41","dmt22_42","dmt22_43","dmt22_44","dmt22_45","dmt22_46","dmt22_48","dmt22_49","dmt22_50"}
        });

        // Definir repeticiones específicas para cada subset
        REPETITIONS_PER_SUBSET.put(0, new int[]{8, 14, 12, 12, 18, 18, 18, 9}); // puedes ajustar como quieras
    }

    @SubscribeEvent
    public static void onRenderOverlay(RenderGuiOverlayEvent.Post event) {
        Minecraft mc = Minecraft.getInstance();
        if (mc.player == null || mc.level == null) return;

        if (!mc.player.hasEffect(ModEffects.DMT_EFFECT.get())) {
            currentSubSet = 0;
            currentFrame = 0;
            currentRepetition = 0;
            frameCounter = 0;
            return;
        }

        int amplifier = mc.player.getEffect(ModEffects.DMT_EFFECT.get()).getAmplifier();
        String[][] subSets = FRAME_SETS.getOrDefault(amplifier, FRAME_SETS.get(0));
        if (subSets.length == 0) return;

        String[] currentSetFrames = subSets[currentSubSet];

        // Número de repeticiones de este subset
        int reps = REPETITIONS_PER_SUBSET.getOrDefault(amplifier, new int[]{DEFAULT_REPETITIONS})[currentSubSet];

        // Avanzar el frame suavemente
        frameCounter += FRAMES_PER_TICK * 0.02;
        int frameIndex = (int) frameCounter;

        if (frameIndex >= currentSetFrames.length) {
            frameCounter = 0;
            frameIndex = 0;
            currentRepetition++;
            if (currentRepetition >= reps) {
                currentRepetition = 0;
                currentSubSet = (currentSubSet + 1) % subSets.length;
            }
        }

        String frame = currentSetFrames[frameIndex];
        ResourceLocation texture = new ResourceLocation(Drugcraft.MODID, "textures/gui/dmt/" + frame + ".png");

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

        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        RenderSystem.disableBlend();
        RenderSystem.enableDepthTest();
    }
}
