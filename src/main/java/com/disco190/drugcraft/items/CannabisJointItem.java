package com.disco190.drugcraft.items;

import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

public class CannabisJointItem extends Item {

    public CannabisJointItem(Properties properties) {
        super(properties);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.DRINK; // animación de beber
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 32; // duración de cada calada
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        ItemStack otherHand = player.getItemInHand(hand == InteractionHand.MAIN_HAND ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND);

        // Comprueba que la otra mano tenga el mechero
        if (otherHand.getItem() != Items.FLINT_AND_STEEL) {
            if (!world.isClientSide) {
                player.displayClientMessage(Component.literal("Necesitas un mechero en la otra mano!"), true);
            }
            return InteractionResultHolder.fail(stack);
        }

        // Inicia animación y uso
        player.startUsingItem(hand);
        return InteractionResultHolder.sidedSuccess(stack, world.isClientSide());
    }


    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity entityLiving) {
        if (!world.isClientSide && entityLiving instanceof Player player) {
            // Efectos al fumar
            player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 600, 0)); // nausea 1, 10s
            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 400, 0)); // lentitud 5s
            // Puedes añadir más efectos aquí
        }

        // Guardar cuántas caladas se han dado
        int caladas = stack.getOrCreateTag().getInt("Caladas");
        caladas++;
        stack.getOrCreateTag().putInt("Caladas", caladas);

        // Si llega a 3 caladas, se consume el porro
        if (caladas >= 3) {
            stack.shrink(1); // quita un ítem del stack
        }

        // Sonido de “fumar” (de momento bebida)
        world.playSound(null, entityLiving.getX(), entityLiving.getY(), entityLiving.getZ(),
                SoundEvents.HONEY_DRINK, entityLiving.getSoundSource(), 1.0F, 1.0F);

        return stack;
    }
}
