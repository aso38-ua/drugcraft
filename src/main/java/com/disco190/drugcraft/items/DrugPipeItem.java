package com.disco190.drugcraft.items;

import com.disco190.drugcraft.effects.ModEffects;
import com.disco190.drugcraft.item.ModItems;
import com.disco190.drugcraft.sound.ModSounds;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.sounds.SoundEvent; // Asegúrate de tener esta importación

public class DrugPipeItem extends Item {

    private static final int USE_DURATION = 60; // 3 segundos

    public DrugPipeItem(Properties properties) {
        super(properties.durability(64)); // pipa con 64 usos
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack pipe = player.getItemInHand(hand);
        ItemStack otherHand = player.getItemInHand(hand == InteractionHand.MAIN_HAND ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND);

        if (!(otherHand.getItem() instanceof MethItem || otherHand.getItem() == ModItems.DMT.get())) {
            if (!world.isClientSide) {
                player.displayClientMessage(Component.literal("¡Necesitas Meth o DMT en la otra mano!"), true);
            }
            return InteractionResultHolder.fail(pipe);
        }

        player.startUsingItem(hand);
        return InteractionResultHolder.consume(pipe);
    }


    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity livingEntity) {
        if (livingEntity instanceof Player player && !level.isClientSide) {
            ItemStack otherHand = player.getItemInHand(
                    player.getUsedItemHand() == InteractionHand.MAIN_HAND ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND
            );

            if (otherHand.getItem() instanceof MethItem meth) {
                meth.applyEffects(player, otherHand); // aplicar efectos de la meth
                otherHand.shrink(1);
                stack.hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(player.getUsedItemHand()));
            } else if (otherHand.getItem() == ModItems.DMT.get()) {
                player.addEffect(new MobEffectInstance(ModEffects.DMT_EFFECT.get(), 1800, 0));
                otherHand.shrink(1);
                stack.hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(player.getUsedItemHand()));
            } else {
                // Si no hay droga en la otra mano, no pasa nada
                player.displayClientMessage(Component.literal("Necesitas Meth o DMT en la otra mano"), true);
            }
        }
        return stack;
    }




    @Override
    public int getUseDuration(ItemStack stack) {
        return USE_DURATION;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW;
    }

    @Override
    public void onUseTick(Level level, LivingEntity livingEntity, ItemStack stack, int remainingTicks) {
        // En un mundo del lado del cliente, crea partículas de humo
        if (level.isClientSide) {
            if (remainingTicks % 5 == 0) {
                double d0 = livingEntity.getX();
                double d1 = livingEntity.getY() + 1.6;
                double d2 = livingEntity.getZ();

                level.addParticle(ParticleTypes.SMOKE, d0, d1, d2, 0.0, 0.1, 0.0);
            }
        }

        // En un mundo del lado del servidor, reproduce el sonido periódicamente
        if (!level.isClientSide) {
            if (remainingTicks % 20 == 0) { // Reproduce el sonido cada segundo
                SoundEvent sound = ModSounds.JOINT_SMOKE.get();
                level.playSound(
                        null,
                        livingEntity.getX(),
                        livingEntity.getY(),
                        livingEntity.getZ(),
                        sound,
                        SoundSource.PLAYERS,
                        1.0F,
                        1.0F
                );
            }
        }
    }

    @Override
    public void releaseUsing(ItemStack stack, Level level, LivingEntity livingEntity, int remainingTicks) {
        super.releaseUsing(stack, level, livingEntity, remainingTicks);
        if (livingEntity instanceof Player player) {
            if (!level.isClientSide) {
                // player.displayClientMessage(Component.literal("Fumada cancelada!"), true);
            }
        }
    }
}