package com.disco190.drugcraft.items;

import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;

public class RefreshmentItem extends Item {

    public RefreshmentItem(Properties properties) {
        super(properties.stacksTo(16)); // máximo 16 por stack, cambia si quieres
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.DRINK; // animación de beber
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 32; // tiempo de bebida (igual que poción)
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        return ItemUtils.startUsingInstantly(world, player, hand);
        // esto abre la animación de beber automáticamente
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity entity) {
        if (!world.isClientSide && entity instanceof Player player) {
            // efectos al beber
            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 200, 0)); // 10s de Speed I
            player.addEffect(new MobEffectInstance(MobEffects.SATURATION, 20, 0));      // 1s de saturación leve
        }

        // devuelve el stack vacío si no está en creativo
        if (entity instanceof Player player && !player.isCreative()) {
            stack.shrink(1); // consume 1 ítem
        }
        return stack;
    }
}
