package com.disco190.drugcraft.items;

import com.disco190.drugcraft.ModDamageSources;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class HorseSemenItem extends Item {
    public HorseSemenItem(Properties properties) {
        super(properties.stacksTo(1));
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity entityLiving) {

        // aplica primero la lógica de comida (cura hambre, consume ítem, etc.)
        ItemStack result = super.finishUsingItem(stack, world, entityLiving);

        if (!world.isClientSide && entityLiving instanceof Player player) {
            player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, Integer.MAX_VALUE, 3));
            player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 900, 3));
            player.addEffect(new MobEffectInstance(MobEffects.JUMP, 900, 3));
            player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 900, 3));
            player.hurt(ModDamageSources.cigarette(world), 2.0F);
        }

        return result;
    }

}
