package com.disco190.drugcraft.effects;

import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

import java.util.Random;

public class HallucinationEffect extends MobEffect {

    private static final Random random = new Random();

    public HallucinationEffect() {
        super(MobEffectCategory.HARMFUL, 0xAA00FF);
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        Level world = entity.level();

        if (world.isClientSide) {
            spawnHallucinationParticles(entity, amplifier);
            applyWobble(entity, amplifier);
        }

        if (entity instanceof Player player) {
            // Bloqueo de movimiento
            player.setDeltaMovement(0, player.getDeltaMovement().y, 0);
            // Invulnerabilidad
            entity.addEffect(new MobEffectInstance(
                    net.minecraft.world.effect.MobEffects.DAMAGE_RESISTANCE,
                    40,
                    255,
                    false,
                    false
            ));
        }

        // Brillo
        int radius = 16;
        world.getEntitiesOfClass(LivingEntity.class,
                entity.getBoundingBox().inflate(radius),
                e -> e != entity
        ).forEach(target -> target.addEffect(new MobEffectInstance(
                net.minecraft.world.effect.MobEffects.GLOWING,
                40, 0, false, false
        )));
        entity.addEffect(new MobEffectInstance(net.minecraft.world.effect.MobEffects.GLOWING, 40, 0, false, false));
    }

    private void spawnHallucinationParticles(LivingEntity entity, int amplifier) {
        Level world = entity.level();
        for (int i = 0; i < 10 + amplifier*2; i++) { // más partículas
            double offsetX = (random.nextDouble() - 0.5) * 0.5;
            double offsetY = random.nextDouble() * 0.5;
            double offsetZ = (random.nextDouble() - 0.5) * 0.5;
            world.addParticle(
                    ParticleTypes.ENTITY_EFFECT,
                    entity.getX() + offsetX,
                    entity.getY() + 1 + offsetY,
                    entity.getZ() + offsetZ,
                    random.nextFloat(),
                    random.nextFloat(),
                    random.nextFloat()
            );
        }
    }

    private void applyWobble(LivingEntity entity, int amplifier) {
        if (entity instanceof Player player) {
            float wobble = (random.nextFloat() - 0.5f) * 0.25f * (amplifier + 1);
            double floatY = Math.sin(entity.level().getGameTime() * 0.5) * 0.03 * (amplifier + 1);
            player.setDeltaMovement(player.getDeltaMovement().add(wobble, floatY, wobble));
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true;
    }
}
