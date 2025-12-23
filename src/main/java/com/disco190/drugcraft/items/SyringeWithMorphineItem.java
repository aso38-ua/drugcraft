package com.disco190.drugcraft.items;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
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

    private static final String MORPHINE_USES = "MorphineUses";
    private static final String MORPHINE_TOLERANCE = "MorphineTolerance";
    private static final String MORPHINE_ADDICTED = "MorphineAddicted";
    private static final String MORPHINE_BRAIN_DAMAGE = "MorphineBrainDamage";
    private static final String LAST_MORPHINE_TICK = "LastMorphineTick";


    private static final int USE_DURATION = 32;

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

            CompoundTag data = player.getPersistentData();

            int uses = data.getInt(MORPHINE_USES);
            int tolerance = data.getInt(MORPHINE_TOLERANCE);

            // Incrementos
            uses++;
            tolerance = Math.min(tolerance + 1, 10);

            data.putInt(MORPHINE_USES, uses);
            data.putInt(MORPHINE_TOLERANCE, tolerance);

            // Adicción
            if (uses >= 3) {
                data.putBoolean(MORPHINE_ADDICTED, true);
            }


            int tolerancePenalty = tolerance * 5; // ticks

            switch (quality) {
                case "high" -> {
                    player.addEffect(new MobEffectInstance(
                            MobEffects.REGENERATION,
                            Math.max(20 * 30 - tolerancePenalty, 20 * 10),
                            1
                    ));
                    player.addEffect(new MobEffectInstance(
                            MobEffects.DAMAGE_RESISTANCE,
                            20 * 60,
                            1
                    ));
                    player.addEffect(new MobEffectInstance(
                            MobEffects.ABSORPTION,
                            20 * 40,
                            2
                    ));

                    // Costes
                    player.addEffect(new MobEffectInstance(
                            MobEffects.MOVEMENT_SLOWDOWN,
                            20 * 60,
                            1
                    ));
                    player.addEffect(new MobEffectInstance(
                            MobEffects.DIG_SLOWDOWN,
                            20 * 60,
                            1
                    ));
                }
                case "medium" -> {
                    player.addEffect(new MobEffectInstance(
                            MobEffects.REGENERATION,
                            Math.max(20 * 20 - tolerancePenalty, 20 * 5),
                            0
                    ));
                    player.addEffect(new MobEffectInstance(
                            MobEffects.DAMAGE_RESISTANCE,
                            20 * 40,
                            0
                    ));
                    player.addEffect(new MobEffectInstance(
                            MobEffects.ABSORPTION,
                            20 * 30,
                            1
                    ));

                    player.addEffect(new MobEffectInstance(
                            MobEffects.MOVEMENT_SLOWDOWN,
                            20 * 60,
                            2
                    ));
                    player.addEffect(new MobEffectInstance(
                            MobEffects.DIG_SLOWDOWN,
                            20 * 60,
                            1
                    ));
                }
                default -> {
                    player.addEffect(new MobEffectInstance(
                            MobEffects.CONFUSION,
                            20 * 30,
                            1
                    ));
                    player.addEffect(new MobEffectInstance(
                            MobEffects.MOVEMENT_SLOWDOWN,
                            20 * 60,
                            2
                    ));
                }
            }

            float lowOD;
            float highOD;

            if ("high".equals(quality)) {
                lowOD = 0.04f + (tolerance * 0.01f);
                highOD = 0.005f + (tolerance * 0.002f);
            } else {
                lowOD = 0.02f + (tolerance * 0.003f);
                highOD = 0.003f + (tolerance * 0.001f);
            }

            float roll = player.getRandom().nextFloat();

            // 🔴 Sobredosis alta – MUERTE
            if (roll < highOD) {
                player.kill();
                return stack;
            }

            // 🟡 Sobredosis baja – daño cerebral
            if (roll < lowOD) {
                CompoundTag pdata = player.getPersistentData();
                pdata.putBoolean(MORPHINE_BRAIN_DAMAGE, true);

                player.removeAllEffects();

                player.addEffect(new MobEffectInstance(
                        MobEffects.CONFUSION,
                        20 * 30,
                        1
                ));
                player.addEffect(new MobEffectInstance(
                        MobEffects.POISON,
                        20 * 40,
                        0
                ));
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

            data.putLong(LAST_MORPHINE_TICK, level.getGameTime());
        }
        return stack;
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);

        String quality = MorphineItem.getQuality(stack);

        tooltip.add(Component.literal("Injectable analgesic").withStyle(ChatFormatting.GRAY));
        tooltip.add(Component.literal("Quality: " + quality).withStyle(ChatFormatting.AQUA));
        tooltip.add(Component.literal("Repeated use has consequences").withStyle(ChatFormatting.DARK_RED));
    }


}
