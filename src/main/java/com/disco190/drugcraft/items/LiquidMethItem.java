package com.disco190.drugcraft.items;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class LiquidMethItem extends Item {

    public LiquidMethItem(Properties properties) {
        super(properties.stacksTo(16));
    }

    // --- QUALITY SYSTEM ---
    public static void setQuality(ItemStack stack, String quality) {
        stack.getOrCreateTag().putString("Quality", quality);
    }

    public static String getQuality(ItemStack stack) {
        if (stack.hasTag() && stack.getTag().contains("Quality")) {
            return stack.getTag().getString("Quality");
        }
        return "low";
    }

    // --- COOKING SYSTEM (1-10) ---
    public static void setCooked(ItemStack stack, int value) {
        stack.getOrCreateTag().putInt("Cooked", value);
    }

    public static int getCooked(ItemStack stack) {
        return stack.hasTag() && stack.getTag().contains("Cooked") ? stack.getTag().getInt("Cooked") : 0;
    }

    public static void increaseCooked(ItemStack stack) {
        int cooked = getCooked(stack);
        if (cooked < 10) setCooked(stack, cooked + 1);
    }

    private static String getCookedState(ItemStack stack) {
        int cooked = getCooked(stack);
        if (cooked <= 3) return "Undercooked";
        if (cooked <= 7) return "Cooked";
        if (cooked <= 9) return "Well Cooked";
        return "Burnt";
    }

    // --- TOOLTIP ---
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        String quality = getQuality(stack);
        switch (quality) {
            case "high" -> tooltip.add(Component.literal("Calidad: Alta").withStyle(ChatFormatting.GREEN));
            case "medium" -> tooltip.add(Component.literal("Calidad: Media").withStyle(ChatFormatting.YELLOW));
            case "low" -> tooltip.add(Component.literal("Calidad: Baja").withStyle(ChatFormatting.RED));
            case "burnt" -> tooltip.add(Component.literal("Calidad: Quemada").withStyle(ChatFormatting.DARK_RED, ChatFormatting.ITALIC));
            default -> tooltip.add(Component.literal("Calidad desconocida").withStyle(ChatFormatting.GRAY));
        }

        tooltip.add(Component.literal("Estado: " + getCookedState(stack)).withStyle(ChatFormatting.AQUA));
    }

    // --- USE (Click derecho) ---
    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        ItemStack otherHand = player.getItemInHand(hand == InteractionHand.MAIN_HAND ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND);

        // Comprobar mechero en la otra mano
        if (otherHand.getItem() != Items.FLINT_AND_STEEL) {
            if (!world.isClientSide)
                player.displayClientMessage(Component.literal("Necesitas un mechero en la otra mano!"), true);
            return InteractionResultHolder.fail(stack);
        }

        // Aumentar cocinado
        increaseCooked(stack);

        if (!world.isClientSide) {

            // Gastar durabilidad del mechero
            otherHand.hurtAndBreak(1, player, p -> p.broadcastBreakEvent(hand == InteractionHand.MAIN_HAND ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND));
        }

        return InteractionResultHolder.sidedSuccess(stack, world.isClientSide);
    }

    // --- OPTIONAL: Aplicar efectos por calidad/cocinado ---
    public void applyEffects(Player player, ItemStack stack) {
        int cooked = getCooked(stack);
        String quality = getQuality(stack);

        switch (quality) {
            case "high" -> {
                player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 20 * 60, 1));
                player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 20 * 30, 0));
            }
            case "medium" -> {
                player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 20 * 30, 0));
                player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 20 * 5, 0));
            }
            case "low" -> {
                player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 20 * 20, 0));
                player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 20 * 15, 0));
                player.addEffect(new MobEffectInstance(MobEffects.HUNGER, 20 * 20, 0));
            }
            case "burnt" -> {
                player.addEffect(new MobEffectInstance(MobEffects.POISON, 20 * 15, 1));
                player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 20 * 20, 1));
            }
        }

        // Modificación por cocinado
        if (cooked <= 3)
            player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 20 * 20, 0));
        else if (cooked >= 10)
            player.addEffect(new MobEffectInstance(MobEffects.POISON, 20 * 15, 1));
        else if (cooked >= 8)
            player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 20 * 40, 1));
    }
}
