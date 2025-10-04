package com.disco190.drugcraft.items;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import com.disco190.drugcraft.item.ModItems;

import javax.annotation.Nullable;
import java.util.List;

public class SyringeWithMorphineItem extends Item {

    private static final int USE_DURATION = 4;

    public SyringeWithMorphineItem(Properties properties) {
        super(properties.stacksTo(64));
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return USE_DURATION;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        player.startUsingItem(hand);
        return InteractionResultHolder.consume(player.getItemInHand(hand));
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level level, LivingEntity entity) {
        if (!level.isClientSide && entity instanceof Player player) {
            // Leer la calidad
            String quality = MorphineItem.getQuality(stack);

            switch (quality) {
                case "high" -> {
                    player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 20 * 30, 1));
                    player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 20 * 60, 1));
                    player.addEffect(new MobEffectInstance(MobEffects.HEALTH_BOOST, 20 * 20, 0));
                    player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20 * 40, 1));
                    player.addEffect(new MobEffectInstance(MobEffects.HEAL, 20 * 10, 0));
                }
                case "medium" -> {
                    player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 20 * 20, 0));
                    player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 20 * 40, 0));
                    player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20 * 40, 2));
                    player.addEffect(new MobEffectInstance(MobEffects.HEAL, 20 * 10, 0));
                }
                case "low" -> {
                    player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 20 * 10, 0));
                    player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 20 * 15, 0));
                    player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20 * 60, 2));
                }
                default -> {
                    player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 20 * 30, 1));
                    player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20 * 60, 2));
                }
            }

            if (!player.getAbilities().instabuild) {
                stack.shrink(1);
                ItemStack emptySyringe = new ItemStack(ModItems.SYRINGE.get());
                if (stack.isEmpty()) {
                    return emptySyringe;
                } else {
                    player.getInventory().add(emptySyringe);
                }
            }
        }
        return stack;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);

        String quality = MorphineItem.getQuality(stack);

        tooltip.add(Component.literal("Contains Morphine").withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.literal("Quality: " + quality).withStyle(ChatFormatting.AQUA));
    }


}
