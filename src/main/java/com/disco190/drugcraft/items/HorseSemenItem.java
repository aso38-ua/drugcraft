package com.disco190.drugcraft.items;

import com.disco190.drugcraft.ModDamageSources;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.item.ItemUtils;

public class HorseSemenItem extends Item {
    public HorseSemenItem(Properties properties) {
        super(properties.stacksTo(1));
    }

    // Animación de beber
    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.DRINK;
    }

    // Tiempo para beber
    @Override
    public int getUseDuration(ItemStack stack) {
        return 32;
    }

    // Inicia el uso del item (abre animación)
    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        return ItemUtils.startUsingInstantly(world, player, hand);
    }

    // Lógica al terminar de beber
    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity entityLiving) {
        ItemStack result = super.finishUsingItem(stack, world, entityLiving);

        if (!world.isClientSide && entityLiving instanceof Player player) {

            // Aplica efectos
            player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, Integer.MAX_VALUE, 3));
            player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 900, 3));
            player.addEffect(new MobEffectInstance(MobEffects.JUMP, 900, 3));
            player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 900, 3));
            player.hurt(ModDamageSources.cigarette(world), 2.0F);

            // Si está en survival, deja bucket
            if (!player.isCreative()) {
                player.setItemInHand(player.getUsedItemHand(), new ItemStack(Items.BUCKET));
            }
        }

        // En creativo devuelve el mismo item, en survival el result normal
        if (entityLiving instanceof Player p && p.isCreative()) {
            return stack;
        }
        return result;
    }
}
