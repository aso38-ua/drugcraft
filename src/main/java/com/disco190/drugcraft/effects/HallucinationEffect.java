package com.disco190.drugcraft.effects;

import net.minecraft.client.Minecraft;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.core.particles.ParticleTypes;

import java.util.Random;

public class HallucinationEffect extends MobEffect {

    private static final Random random = new Random();

    public HallucinationEffect() {
        super(MobEffectCategory.HARMFUL, 0xAA00FF); // color púrpura
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int amplifier) {
        Level world = entity.level();
        if (world.isClientSide) {
            // --- Cliente: partículas y mareo ---
            spawnHallucinationParticles(entity, amplifier);
            applyWobble(entity, amplifier);
        } else {
            // --- Servidor: dar GLOWING al jugador y a los mobs cercanos ---
            int radius = 16; // alcance de la "alucinación"
            world.getEntitiesOfClass(LivingEntity.class,
                    entity.getBoundingBox().inflate(radius),
                    e -> e != entity // excluir al jugador mismo (si no quieres, bórralo)
            ).forEach(target -> {
                target.addEffect(new MobEffectInstance(
                        net.minecraft.world.effect.MobEffects.GLOWING,
                        40, // 2 segundos, se refresca cada tick
                        0,
                        false,
                        false
                ));
            });

            // también el propio jugador puede brillar si quieres
            entity.addEffect(new MobEffectInstance(
                    net.minecraft.world.effect.MobEffects.GLOWING,
                    40,
                    0,
                    false,
                    false
            ));
        }
    }

    private void spawnHallucinationParticles(LivingEntity entity, int amplifier) {
        Random random = HallucinationEffect.random;
        Level world = entity.level();
        for (int i = 0; i < 5 + amplifier; i++) {
            double offsetX = (random.nextDouble() - 0.5) * 0.5;
            double offsetY = random.nextDouble() * 0.5;
            double offsetZ = (random.nextDouble() - 0.5) * 0.5;

            SimpleParticleType[] particles = {
                    ParticleTypes.ENCHANT, ParticleTypes.END_ROD,
                    ParticleTypes.HAPPY_VILLAGER, ParticleTypes.COMPOSTER
            };
            SimpleParticleType particle = particles[random.nextInt(particles.length)];

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
            float wobble = (random.nextFloat() - 0.5f) * 0.15f * (amplifier + 1);
            double floatY = Math.sin(entity.level().getGameTime() * 0.3) * 0.02 * (amplifier + 1);

            player.setDeltaMovement(player.getDeltaMovement().add(wobble, floatY, wobble));
        }
    }


    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true; // se aplica cada tick
    }
}
