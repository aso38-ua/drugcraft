package com.disco190.drugcraft.items;

import com.disco190.drugcraft.ModDamageSources;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class DonJavierCigarItem extends Cigar{
    public DonJavierCigarItem(Item.Properties properties) {
        super(properties); // 5 caladas
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity entityLiving) {
        // aplica primero la lógica de comida (cura hambre, consume ítem, etc.)
        ItemStack result = super.finishUsingItem(stack, world, entityLiving);

        if (!world.isClientSide && entityLiving instanceof Player player) {
            player.hurt(ModDamageSources.cigarette(world), 1.0F);
            player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 900, 0));
        }

        // gasta un "uso"
        stack.hurtAndBreak(1, entityLiving, e -> { e.broadcastBreakEvent(e.getUsedItemHand()); });

        return result;
    }
}
