package com.disco190.drugcraft.items;

import net.minecraft.network.chat.Component;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import com.disco190.drugcraft.item.ModItems;

public class SyringeWithMorphineItem extends Item {

    private static final int USE_DURATION = 4;

    public SyringeWithMorphineItem(Properties properties) {
        super(properties.stacksTo(1));
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
            // Aplicar efectos
            player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 20 * 20, 0));
            player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 20 * 40, 0));
            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20 * 20, 2));
            player.addEffect(new MobEffectInstance(MobEffects.HEAL, 5 * 5, 0));
            player.addEffect(new MobEffectInstance(MobEffects.HEALTH_BOOST, 20 * 20, 0));

            // Solo consumir si no estás en creativo
            if (!player.getAbilities().instabuild) {
                stack.shrink(1);
            }

            // Devolver la jeringuilla vacía
            ItemStack emptySyringe = new ItemStack(ModItems.SYRINGE.get());
            if (stack.isEmpty()) {
                return emptySyringe; // si la morfina se consumió, devuelvo el item vacío
            } else {
                player.getInventory().add(emptySyringe); // si queda algo raro, lo agrego al inventario
            }
        }
        return stack;
    }


}
