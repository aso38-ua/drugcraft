package com.disco190.drugcraft.items;

import com.disco190.drugcraft.Drugcraft;
import com.disco190.drugcraft.item.ModItems;
import com.disco190.drugcraft.sound.ModSounds;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

public class SmokingItem extends Item {
    public SmokingItem(Item.Properties properties) {
        super(properties);
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
        //if (stack.getDamageValue() == 0) {              //Solo necesitas mechero la primera calada
            ItemStack otherHand = player.getItemInHand(hand == InteractionHand.MAIN_HAND ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND);

            if (otherHand.getItem() != Items.FLINT_AND_STEEL && otherHand.getItem() != ModItems.IRON_LIGHTER.get() && otherHand.getItem() != ModItems.GOLD_LIGHTER.get() && otherHand.getItem() != ModItems.DIAMOND_LIGHTER.get()) {
                if (!world.isClientSide) {
                    player.displayClientMessage(Component.literal("Necesitas un mechero en la otra mano!"), true);
                }
                return InteractionResultHolder.fail(stack);
            }
        //}

        if (!world.isClientSide) {
            SoundEvent sound = ModSounds.JOINT_SMOKE.get();
            world.playSound(null, player.getX(), player.getY(), player.getZ(),
                    sound, player.getSoundSource(), 1.0F, 1.0F);
        }

        player.startUsingItem(hand);
        return InteractionResultHolder.sidedSuccess(stack, world.isClientSide());
    }
}
