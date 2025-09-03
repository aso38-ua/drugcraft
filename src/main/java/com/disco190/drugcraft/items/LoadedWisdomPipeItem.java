package com.disco190.drugcraft.items;

import com.disco190.drugcraft.ModDamageSources;
import com.disco190.drugcraft.item.ModItems;
import com.disco190.drugcraft.sound.ModSounds;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;

public class LoadedWisdomPipeItem extends Pipe{

    public LoadedWisdomPipeItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity entityLiving) {

        // aplica primero la lógica de comida (cura hambre, consume ítem, etc.)
        ItemStack result = super.finishUsingItem(stack, world, entityLiving);

        if (!world.isClientSide && entityLiving instanceof Player player) {
            player.hurt(ModDamageSources.cigarette(world), 1.0F);
            player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 900, 0));
            player.addEffect(new MobEffectInstance(MobEffects.GLOWING, 900, 0));
            player.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 900, 0));
        }

        // gastar durabilidad
        stack.hurtAndBreak(1, entityLiving, e -> {
            // cuando se rompe, reemplazar por la pipa vacía
            e.setItemInHand(e.getUsedItemHand(), new ItemStack(ModItems.WISDOM_PIPE.get()));
        });

        return result;
    }

}
