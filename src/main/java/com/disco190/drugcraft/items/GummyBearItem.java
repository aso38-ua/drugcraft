package com.disco190.drugcraft.items;

import com.disco190.drugcraft.effects.ModEffects;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class GummyBearItem extends Item {

    public GummyBearItem(Properties properties) {
        super(properties.food(new FoodProperties.Builder()
                .nutrition(2)
                .saturationMod(0.2f)
                .alwaysEat()
                .effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 200, 0), 0.5F)
                .effect(() -> new MobEffectInstance(MobEffects.JUMP, 200, 0), 0.5F)
                .build()));
    }

    public static String getBudType(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        if (tag != null && tag.contains("BudType")) {
            return tag.getString("BudType");
        }
        return "unknown";
    }

    public static void setBudType(ItemStack stack, String type) {
        stack.getOrCreateTag().putString("BudType", type);
    }

    // --- Al consumir ---
    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        ItemStack result = super.finishUsingItem(stack, level, entity);

        if (!level.isClientSide && entity instanceof Player player) {
            // Efecto retardado corto (ej. 5 segundos)
            player.getPersistentData().putInt("gummy_bear_delay", 100);
            player.getPersistentData().putString("gummy_bear_type", getBudType(stack));
        }

        return result;
    }

    // --- Tickeo para aplicar efectos retardados ---
    @Override
    public void inventoryTick(ItemStack stack, Level level, net.minecraft.world.entity.Entity entity, int slot,
            boolean selected) {
        if (level.isClientSide || !(entity instanceof Player player))
            return;

        if (player.getPersistentData().contains("gummy_bear_delay")) {
            int ticks = player.getPersistentData().getInt("gummy_bear_delay");

            if (ticks <= 0) {
                String type = player.getPersistentData().getString("gummy_bear_type");
                player.getPersistentData().remove("gummy_bear_delay");
                player.getPersistentData().remove("gummy_bear_type");

                applyGummyEffects(player, type);
            } else {
                player.getPersistentData().putInt("gummy_bear_delay", ticks - 1);
            }
        }
    }

    private void applyGummyEffects(Player player, String type) {
        // Duraciones reducidas a la mitad respecto al brownie
        switch (type) {
            case "purple_haze" -> {
                player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 300, 0));
                player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 100, 0));
                player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 200, 0));
                player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 300, 0));
                player.addEffect(new MobEffectInstance(ModEffects.SMOKED.get(), 800, 0));
            }
            case "marijuana" -> {
                player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 300, 1));
                player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 200, 0));
                player.addEffect(new MobEffectInstance(MobEffects.HUNGER, 300, 0));
                player.addEffect(new MobEffectInstance(ModEffects.SMOKED.get(), 800, 0));
            }
            case "fujiyama" -> {
                player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 800, 1));
                player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 600, 0));
                player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 400, 0));
                player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 600, 0));
                player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 200, 0));
                player.addEffect(new MobEffectInstance(ModEffects.SMOKED.get(), 800, 0));
            }
            case "blaze_kush" -> {
                player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 800, 0));
                player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 100, 0));
                player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 400, 0));
                player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 200, 0));
                player.addEffect(new MobEffectInstance(ModEffects.SMOKED.get(), 800, 0));
            }
            default -> player.addEffect(new MobEffectInstance(ModEffects.SMOKED.get(), 300, 0));
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        String budType = getBudType(stack);
        tooltip.add(Component.literal("Type: " + budType.replace("_", " "))
                .withStyle(net.minecraft.ChatFormatting.DARK_GREEN));
    }
}
