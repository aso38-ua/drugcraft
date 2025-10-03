package com.disco190.drugcraft.items;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class OpiumItem extends Item {

    public OpiumItem(Properties properties) {
        super(properties.stacksTo(64));
    }

    // --- USE (requiere pipa) ---
    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        if (!world.isClientSide) {
            player.displayClientMessage(Component.literal("Necesitas una pipa para fumar el opio."), true);
        }
        return InteractionResultHolder.fail(player.getItemInHand(hand));
    }

    // --- EFECTOS AL CONSUMIR ---
    public void applyEffects(Player player, ItemStack stack) {
        // Efectos principales
        player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 20 * 60, 1)); // 1 min resistencia II
        player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 20 * 30, 0)); // 30s regeneración I
        player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 20 * 60, 1)); // 1 min + corazones extra

        // Efectos negativos (sedación / mareo)
        player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20 * 45, 1)); // 45s lentitud II
        player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 20 * 15, 0)); // 15s nausea
    }
}
