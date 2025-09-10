package com.disco190.drugcraft.items;

import com.disco190.drugcraft.ModDamageSources;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class EsplendidoCigarItem extends Cigar{


    public EsplendidoCigarItem(Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity entityLiving) {

        ItemStack result = super.finishUsingItem(stack, world, entityLiving);

        if (!world.isClientSide && entityLiving instanceof Player player) {
            player.hurt(ModDamageSources.cigarette(world), 1.0F);
            player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 900, 1));
        }

        System.out.println(stack.getMaxDamage());
        System.out.println(stack.getMaxDamage() - stack.getDamageValue());

        // gasta un "uso"
        stack.hurtAndBreak(1, entityLiving, e -> { e.broadcastBreakEvent(e.getUsedItemHand()); });

        return result;
    }
}
