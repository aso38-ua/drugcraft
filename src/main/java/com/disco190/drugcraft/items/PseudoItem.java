package com.disco190.drugcraft.items;

import com.disco190.drugcraft.effects.ModEffects;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

public class PseudoItem extends Item {

    public PseudoItem(Properties properties) {
        super(properties);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.EAT;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 32;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        player.startUsingItem(hand);
        return InteractionResultHolder.sidedSuccess(player.getItemInHand(hand), world.isClientSide);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity entity) {
        if (!world.isClientSide && entity instanceof Player player) {
            // efectos de pseudoephedrine
            // velocidad y salto aumentados
            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 600, 1)); // 30 segundos, nivel 2
            player.addEffect(new MobEffectInstance(MobEffects.JUMP, 600, 0)); // 30 segundos, nivel 1

            // efectos negativos
            player.addEffect(new MobEffectInstance(MobEffects.HUNGER, 400, 0)); // 20 segundos
            player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 300, 0)); // 15 segundos, leve
            player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 200, 0)); // 10 segundos, leve
        }

        if (entity instanceof Player player && !player.getAbilities().instabuild) {
            stack.shrink(1);
        }
        return stack;
    }
}
