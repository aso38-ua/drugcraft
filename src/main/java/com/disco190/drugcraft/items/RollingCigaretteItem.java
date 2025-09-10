package com.disco190.drugcraft.items;

import com.disco190.drugcraft.ModDamageSources;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class RollingCigaretteItem extends CigaretteItem{


    public RollingCigaretteItem(Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity entityLiving) {

        // aplica primero la lógica de comida (cura hambre, consume ítem, etc.)
        ItemStack result = super.finishUsingItem(stack, world, entityLiving);

        if (!world.isClientSide && entityLiving instanceof Player player) {
            player.addEffect(new MobEffectInstance(MobEffects.LEVITATION, 40, 0));
            player.hurt(ModDamageSources.cigarette(world), 1.0F);
        }

        return result;
    }
}
