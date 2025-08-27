package com.disco190.drugcraft.effects;

import net.minecraft.client.Minecraft;
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
        Level world = entity.level(); // <-- usar getter
        if (!world.isClientSide) return; // efectos visuales solo en cliente

        // efecto cliente: partículas y mareo
        Minecraft mc = Minecraft.getInstance();

        // partículas random tipo portal
        for (int i = 0; i < 5 + amplifier; i++) {
            double offsetX = (random.nextDouble() - 0.5) * 0.5;
            double offsetY = random.nextDouble() * 0.5;
            double offsetZ = (random.nextDouble() - 0.5) * 0.5;
            world.addParticle(
                    ParticleTypes.ENCHANT,
                    entity.getX() + offsetX,
                    entity.getY() + 1 + offsetY,
                    entity.getZ() + offsetZ,
                    0, 0.05, 0
            );
        }

        // movimiento aleatorio para simular mareo
        if (entity instanceof Player player) {
            float wobble = (random.nextFloat() - 0.5f) * 0.1f * (amplifier + 1);
            player.setDeltaMovement(player.getDeltaMovement().add(
                    wobble, 0, wobble
            ));
        }
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return true; // se aplica cada tick
    }
}
