package com.disco190.drugcraft.events;

import com.disco190.drugcraft.Drugcraft;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Drugcraft.MODID)
public class AddictionEvents {

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase == TickEvent.Phase.END && !event.player.level().isClientSide) {
            Player player = event.player;
            if (player.getPersistentData().getBoolean("HeroinAddicted")) {
                long lastUse = player.getPersistentData().getLong("HeroinLastUseTime");
                long currentTime = player.level().getGameTime();
                long timeSinceLastUse = currentTime - lastUse;

                // 1 Minecraft Day = 24000 ticks
                if (timeSinceLastUse > 24000) {
                    // Withdrawal symptoms (Mono)
                    // Apply effects every 10 seconds to ensure they stay active but don't spam
                    if (player.tickCount % 200 == 0) {
                        applyWithdrawalEffects(player, timeSinceLastUse);
                    }
                }
            }
        }
    }

    private static void applyWithdrawalEffects(Player player, long timeSince) {
        int amplifier = 0;
        // Worsen over time
        if (timeSince > 48000)
            amplifier = 1; // 2 days
        if (timeSince > 72000)
            amplifier = 2; // 3 days

        player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 300, amplifier, false, false, true));
        player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 300, amplifier, false, false, true));
        player.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 300, amplifier, false, false, true));

        if (timeSince > 48000 && player.getRandom().nextFloat() < 0.05f) {
            player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 200, 0, false, false, true));
            player.addEffect(new MobEffectInstance(MobEffects.HUNGER, 200, 1, false, false, true));
        }

        if (timeSince > 72000 && player.getRandom().nextFloat() < 0.01f) {
            // Rare hallucination message or damage
            player.hurt(player.damageSources().magic(), 1.0f);
            player.sendSystemMessage(Component.literal("§cYour body craves heroin..."));
        }
    }
}
