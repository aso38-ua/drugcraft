package com.disco190.drugcraft.items;

import com.disco190.drugcraft.ModDamageSources;
import com.disco190.drugcraft.sound.ModSounds;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

public class CigaretteItem extends Item{
    public CigaretteItem(Item.Properties properties) {
        super(properties.stacksTo(64)); // hasta 64 cigarros por stack
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW; // animación al fumar
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 32; // duración de cada calada
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        ItemStack otherHand = player.getItemInHand(hand == InteractionHand.MAIN_HAND ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND);

        if (otherHand.getItem() != Items.FLINT_AND_STEEL) {
            if (!world.isClientSide) {
                player.displayClientMessage(Component.literal("Necesitas un mechero en la otra mano!"), true);
            }
            return InteractionResultHolder.fail(stack);
        }

        if (!world.isClientSide) {
            SoundEvent sound = ModSounds.JOINT_SMOKE.get();
            world.playSound(null, player.getX(), player.getY(), player.getZ(),
                    sound, player.getSoundSource(), 1.0F, 1.0F);
        }

        // Aseguramos que el primer cigarro tenga su propio NBT
        if (stack.getCount() > 1 && !stack.getOrCreateTag().contains("Caladas")) {
            stack = stack.split(1); // separa 1 cigarro del stack
            player.setItemInHand(hand, stack); // sustituye el stack original por este cigarro independiente
        }

        player.startUsingItem(hand);
        return InteractionResultHolder.sidedSuccess(stack, world.isClientSide());
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity entityLiving) {
        if (!world.isClientSide && entityLiving instanceof Player player) {
            player.addEffect(new MobEffectInstance(MobEffects.LUCK, 600, 0));
            player.hurt(ModDamageSources.cigarette(world), 1.0F);
        }

        int caladas = stack.getOrCreateTag().getInt("Caladas");
        caladas++;
        stack.getOrCreateTag().putInt("Caladas", caladas);

        if (caladas >= 1) {
            stack.shrink(1); // consume este cigarro independiente
        }

        return stack;
    }
}
