package com.disco190.drugcraft.items;

import com.disco190.drugcraft.effects.ModEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;

public class AyahuascaItem extends Item {

    public AyahuascaItem(Properties properties) {
        super(properties);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.DRINK; // que se vea que lo bebes
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 48; // más largo, como tomar una poción ritual
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        player.startUsingItem(hand);
        return InteractionResultHolder.sidedSuccess(player.getItemInHand(hand), world.isClientSide);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity entity) {
        if (!world.isClientSide && entity instanceof Player player) {
            // efectos más largos e intensos que los hongos
            player.addEffect(new MobEffectInstance(ModEffects.HALLUCINATION.get(), 1200, 1)); // 1 min más fuerte
            player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 1000, 1));
            player.addEffect(new MobEffectInstance(MobEffects.GLOWING, 1400, 1));
            player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 1600, 0));
            player.addEffect(new MobEffectInstance(MobEffects.HEALTH_BOOST, 800, 1)); // efecto positivo extra
            player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 200, 1)); // un poco de curación inicial
        }

        stack.shrink(1); // consume la bebida
        return stack;
    }
}
