package com.disco190.drugcraft.effects;

import com.disco190.drugcraft.Drugcraft;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.Random;

public class DMTEffect extends MobEffect {

    private static final Random RANDOM = new Random();

    public DMTEffect() {
        super(MobEffectCategory.BENEFICIAL, 0xFFFFFF); // color blanco
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        Level world = entity.level();

        if (world.isClientSide) {
            spawnDMTParticles(entity, amplifier);
            applyScreenOverlay(entity, amplifier);
        }

        if (entity instanceof Player player) {
            // Bloqueo de movimiento parcial
            player.setDeltaMovement(0, player.getDeltaMovement().y, 0);

            // Invulnerabilidad
            entity.addEffect(new MobEffectInstance(
                    net.minecraft.world.effect.MobEffects.DAMAGE_RESISTANCE,
                    40,
                    255,
                    false,
                    false
            ));

            // Regeneración máxima
            entity.addEffect(new MobEffectInstance(
                    net.minecraft.world.effect.MobEffects.REGENERATION,
                    40,
                    255,
                    false,
                    false
            ));

            // Absorción máxima
            entity.addEffect(new MobEffectInstance(
                    net.minecraft.world.effect.MobEffects.ABSORPTION,
                    40,
                    255,
                    false,
                    false
            ));

            // Inmunidad a enemigos: invisibilidad para IA
            entity.addEffect(new MobEffectInstance(
                    net.minecraft.world.effect.MobEffects.INVISIBILITY,
                    40,
                    0,
                    false,
                    false
            ));
        }
    }

    private void spawnDMTParticles(LivingEntity entity, int amplifier) {
        Level world = entity.level();
        for (int i = 0; i < 20 + amplifier * 2; i++) {
            double offsetX = (RANDOM.nextDouble() - 0.5) * 0.5;
            double offsetY = 0.1 + RANDOM.nextDouble() * 0.5;
            double offsetZ = (RANDOM.nextDouble() - 0.5) * 0.5;
            world.addParticle(
                    ParticleTypes.ENCHANTED_HIT,
                    entity.getX() + offsetX,
                    entity.getY() + 1.6 + offsetY,
                    entity.getZ() + offsetZ,
                    RANDOM.nextFloat(),
                    RANDOM.nextFloat(),
                    RANDOM.nextFloat()
            );
        }
    }

    private void applyScreenOverlay(LivingEntity entity, int amplifier) {
        // En el cliente, usaríamos tus imágenes dmt1_x → dmt22_x
        if (entity.level().isClientSide && entity instanceof Player player) {
            Minecraft mc = Minecraft.getInstance();
            // Aquí podrías usar mc.gui o shaders para poner la textura según un contador
            // Por ejemplo, un contador que pase cada 8 ticks a la siguiente imagen
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true; // se aplica cada tick
    }
}
