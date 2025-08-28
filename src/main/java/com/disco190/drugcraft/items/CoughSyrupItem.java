package com.disco190.drugcraft.items;

import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;

public class CoughSyrupItem extends Item {
    public CoughSyrupItem(Properties properties) {
        super(properties);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.DRINK; // animación de beber
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 32; // duración estándar de beber
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        return ItemUtils.startUsingInstantly(world, player, hand);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity entity) {
        ItemStack result = super.finishUsingItem(stack, world, entity);

        if (entity instanceof Player player && !player.getAbilities().instabuild) {
            // al acabar, devolver botella de cristal
            return new ItemStack(Items.GLASS_BOTTLE);
        }

        return result;
    }
}
