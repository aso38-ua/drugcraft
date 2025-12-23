package com.disco190.drugcraft.events;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class PlayerDrugEffects {

    private static final String MORPHINE_BRAIN_DAMAGE = "MorphineBrainDamage";
    private static final String MORPHINE_USES = "MorphineUses";
    private static final String MORPHINE_TOLERANCE = "MorphineTolerance";
    private static final String MORPHINE_ADDICTED = "MorphineAddicted";
    private static final String LAST_MORPHINE_TICK = "LastMorphineTick";

    @SubscribeEvent
    public static void onPlayerTick(TickEvent.PlayerTickEvent event) {
        if (event.phase != TickEvent.Phase.END) return;

        Player player = event.player;
        CompoundTag data = player.getPersistentData();

        if (data.getBoolean(MORPHINE_BRAIN_DAMAGE)) {

            player.addEffect(new MobEffectInstance(
                    MobEffects.MOVEMENT_SLOWDOWN,
                    40,
                    4,
                    false,
                    false,
                    true
            ));

            player.addEffect(new MobEffectInstance(
                    MobEffects.DIG_SLOWDOWN,
                    40,
                    2,
                    false,
                    false,
                    true
            ));

            player.addEffect(new MobEffectInstance(
                    MobEffects.WEAKNESS,
                    40,
                    1,
                    false,
                    false,
                    true
            ));
        }

        long time = player.level().getGameTime();

        long lastUse = data.getLong(LAST_MORPHINE_TICK);

        // Cada 10 minutos sin consumir (6000 ticks)
        if (time - lastUse > 12000) {

            int tolerance = data.getInt(MORPHINE_TOLERANCE);
            int uses = data.getInt(MORPHINE_USES);

            if (tolerance > 0) {
                data.putInt(MORPHINE_TOLERANCE, tolerance - 1);
            }

            if (uses > 0 && time % 12000 == 0) { // más lento
                data.putInt(MORPHINE_USES, uses - 1);
            }

            // Si baja lo suficiente → deja de estar enganchado
            if (data.getInt(MORPHINE_USES) < 3) {
                data.putBoolean(MORPHINE_ADDICTED, false);
            }
        }

        // MONO / abstinencia
        if (data.getBoolean(MORPHINE_ADDICTED)
                && time - lastUse > 4000) {

            player.addEffect(new MobEffectInstance(
                    MobEffects.WEAKNESS, 200, 0, false, false));

            player.addEffect(new MobEffectInstance(
                    MobEffects.MOVEMENT_SLOWDOWN, 200, 0, false, false));
        }

    }
}
