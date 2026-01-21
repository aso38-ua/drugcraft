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

public class SyringeWithHeroinItem extends Item {

    private static final String HEROIN_USES = "HeroinUses";
    private static final String HEROIN_TOLERANCE = "HeroinTolerance";
    private static final String HEROIN_ADDICTED = "HeroinAddicted";
    private static final String HEROIN_BRAIN_DAMAGE = "HeroinBrainDamage";

    private static final int USE_DURATION = 32;

    public SyringeWithHeroinItem(Properties properties) {
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
            CompoundTag data = player.getPersistentData();

            int uses = data.getInt(HEROIN_USES);
            int tolerance = data.getInt(HEROIN_TOLERANCE);

            uses++;
            tolerance = Math.min(tolerance + 1, 10); // Max tolerance 10

            data.putInt(HEROIN_USES, uses);
            data.putInt(HEROIN_TOLERANCE, tolerance);

            if (uses >= 2) { // Addictive fast
                data.putBoolean(HEROIN_ADDICTED, true);
            }

            int tolerancePenalty = tolerance * 5; // Reduces beneficial duration

            // Potent effects
            player.addEffect(
                    new MobEffectInstance(MobEffects.REGENERATION, Math.max(20 * 60 - tolerancePenalty, 20 * 20), 2));
            player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 20 * 120, 2));
            player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 20 * 60, 4)); // 4 hearts
            player.addEffect(new MobEffectInstance(MobEffects.HEALTH_BOOST, 20 * 60, 4)); // Extra max health

            // Side effects
            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20 * 120, 2));
            player.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 20 * 120, 2));
            player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 20 * 10, 0));

            // Overdose Logic - Much more dangerous than Morphine
            float baseOD = 0.05f;
            float toleranceRisk = tolerance * 0.02f;

            float deathChance = baseOD + toleranceRisk; // 5% to 25% chance of death roughly
            float damageChance = deathChance * 2.0f; // Higher chance of brain damage

            float roll = player.getRandom().nextFloat();

            // 🔴 Muerte -> Sobredosis
            if (roll < deathChance) {
                player.kill();
                Component deathMsg = Component.literal(player.getName().getString() + " overdosed on Heroin.");
                level.getServer().getPlayerList().broadcastSystemMessage(deathMsg, false);
                return stack;
            }

            // 🟡 Daño cerebral / Efectos negativos graves
            if (roll < damageChance) {
                data.putBoolean(HEROIN_BRAIN_DAMAGE, true);
                player.removeAllEffects();
                player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 20 * 60, 2));
                player.addEffect(new MobEffectInstance(MobEffects.POISON, 20 * 60, 1));
                player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 20 * 30, 0));
                player.addEffect(new MobEffectInstance(MobEffects.WITHER, 20 * 20, 0));
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
        tooltip.add(Component.literal("Extremely potent opioid").withStyle(ChatFormatting.DARK_PURPLE));
        tooltip.add(Component.literal("High risk of overdose").withStyle(ChatFormatting.DARK_RED));
    }
}
