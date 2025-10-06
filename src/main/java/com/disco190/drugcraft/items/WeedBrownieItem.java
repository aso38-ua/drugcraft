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

public class WeedBrownieItem extends Item {

    public WeedBrownieItem(Properties properties) {
        super(properties.food(new FoodProperties.Builder()
                .nutrition(6)
                .saturationMod(0.6f)
                .alwaysEat()
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
            player.getPersistentData().putInt("weed_brownie_delay", 200); // 10 segundos
            player.getPersistentData().putString("weed_brownie_type", getBudType(stack));
        }

        return result;
    }

    // --- Tickeo para aplicar efectos retardados ---
    @Override
    public void inventoryTick(ItemStack stack, Level level, net.minecraft.world.entity.Entity entity, int slot, boolean selected) {
        if (level.isClientSide || !(entity instanceof Player player)) return;

        if (player.getPersistentData().contains("weed_brownie_delay")) {
            int ticks = player.getPersistentData().getInt("weed_brownie_delay");

            if (ticks <= 0) {
                String type = player.getPersistentData().getString("weed_brownie_type");
                player.getPersistentData().remove("weed_brownie_delay");
                player.getPersistentData().remove("weed_brownie_type");

                applyBrownieEffects(player, type);
            } else {
                player.getPersistentData().putInt("weed_brownie_delay", ticks - 1);
            }
        }
    }

    // --- Aplicar efectos según el tipo ---
    private void applyBrownieEffects(Player player, String type) {
        switch (type) {
            case "purple_haze" -> {
                player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 600, 0));
                player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 200, 0));
                player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 400, 0));
                player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 600, 0));
                player.addEffect(new MobEffectInstance(ModEffects.SMOKED.get(), 1600, 0));
            }
            case "marijuana" -> {
                player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 600, 1));
                player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 400, 0));
                player.addEffect(new MobEffectInstance(MobEffects.HUNGER, 600, 0));
                player.addEffect(new MobEffectInstance(ModEffects.SMOKED.get(), 1600, 0));
            }
            case "fujiyama" -> {
                player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 1600, 2));
                player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 1200, 1));
                player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 800, 1));
                player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 1200, 1));
                player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 400, 0));
                player.addEffect(new MobEffectInstance(ModEffects.SMOKED.get(), 1600, 0));
            }
            case "blaze_kush" -> {
                player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 1600, 0));
                player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 200, 0));
                player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 800, 1));
                player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 400, 0));
                player.addEffect(new MobEffectInstance(ModEffects.SMOKED.get(), 1600, 0));
            }
            default -> player.addEffect(new MobEffectInstance(ModEffects.SMOKED.get(), 600, 0));
        }
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        String budType = getBudType(stack);
        tooltip.add(Component.literal("Type: " + budType.replace("_", " "))
                .withStyle(net.minecraft.ChatFormatting.DARK_GREEN));
    }

}
