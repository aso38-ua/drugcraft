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


public class MethItem extends Item {

    public MethItem(Properties properties) {
        super(properties.stacksTo(64));
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

    private static String getCookedState(ItemStack stack) {
        int cooked = getCooked(stack);
        if (cooked <= 3) return "Undercooked";
        if (cooked <= 7) return "Cooked";
        if (cooked <= 9) return "Well Cooked";
        return "Burnt";
    }

    public static void setPurity(ItemStack stack, int purity) {
        stack.getOrCreateTag().putInt("Purity", purity);
    }

    public static int getPurity(ItemStack stack) {
        return stack.hasTag() && stack.getTag().contains("Purity")
                ? stack.getTag().getInt("Purity")
                : 0;
    }


    // --- TOOLTIP ---
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        String quality = getQuality(stack);
        switch (quality) {
            case "very_high" -> tooltip.add(Component.literal("Calidad: Muy Alta").withStyle(ChatFormatting.DARK_AQUA));
            case "high" -> tooltip.add(Component.literal("Calidad: Alta").withStyle(ChatFormatting.GREEN));
            case "medium" -> tooltip.add(Component.literal("Calidad: Media").withStyle(ChatFormatting.YELLOW));
            case "low" -> tooltip.add(Component.literal("Calidad: Baja").withStyle(ChatFormatting.RED));
            case "burnt" -> tooltip.add(Component.literal("Calidad: Quemada").withStyle(ChatFormatting.DARK_RED, ChatFormatting.ITALIC));
            default -> tooltip.add(Component.literal("Calidad desconocida").withStyle(ChatFormatting.GRAY));
        }

        tooltip.add(Component.literal("Estado: " + getCookedState(stack)).withStyle(ChatFormatting.AQUA));
        tooltip.add(Component.literal("Pureza: " + getPurity(stack) + "%").withStyle(ChatFormatting.BLUE));

    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        if (!world.isClientSide) {
            player.displayClientMessage(Component.literal("Necesitas una pipa para consumir esto."), true);
        }
        return InteractionResultHolder.fail(player.getItemInHand(hand));
    }


    // --- EFECTOS ---
    public void applyEffects(Player player, ItemStack stack) {
        int cooked = getCooked(stack);
        String quality = getQuality(stack);

        // Más fuertes que la versión líquida
        switch (quality) {

            case "very_high" -> {
                player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 30 * 90, 2));
                player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 20 * 60, 2));
                player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 20 * 10, 0));
                player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 20 * 30, 1));
                player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 20 * 30, 1));
                player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 80 * 30, 1));
            }

            case "high" -> {
                player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 20 * 90, 2));
                player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 20 * 60, 1));
                player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 20 * 10, 0));
                player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 20 * 30, 1));
            }
            case "medium" -> {
                player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 20 * 60, 1));
                player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 20 * 10, 0));
                player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 20 * 60, 0));
            }
            case "low" -> {
                player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 20 * 40, 0));
                player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 20 * 20, 0));
                player.addEffect(new MobEffectInstance(MobEffects.HUNGER, 20 * 30, 0));
            }
            case "burnt" -> {
                player.addEffect(new MobEffectInstance(MobEffects.POISON, 20 * 20, 1));
                player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 20 * 30, 1));
            }
        }

        int purity = getPurity(stack);

        if (quality.equals("very_high")) {
            int amp = purity >= 95 ? 2 : 1; // más potencia si purity es >95
            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 20 * (purity/2), amp));
            player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 20 * (purity/3), amp));
        }


        // Ajuste por cocinado
        if (cooked <= 3)
            player.addEffect(new MobEffectInstance(MobEffects.HUNGER, 20 * 30, 0));
        else if (cooked >= 10)
            player.addEffect(new MobEffectInstance(MobEffects.POISON, 20 * 20, 1));
        else if (cooked >= 8)
            player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 20 * 60, 1));
    }
}
