package com.disco190.drugcraft.items;

import com.disco190.drugcraft.ModDamageSources;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class CigaretteItem extends SmokingItem{
    public CigaretteItem(Item.Properties properties) {
        super(properties.stacksTo(64)); // hasta 64 cigarros por stack
    }


    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity entityLiving) {
        // aplica primero la lógica de comida (cura hambre, consume ítem, etc.)
        ItemStack result = super.finishUsingItem(stack, world, entityLiving);

        if (!world.isClientSide && entityLiving instanceof Player player) {
            player.hurt(ModDamageSources.cigarette(world), 1.0F);
        }

        return result;
    }
}
