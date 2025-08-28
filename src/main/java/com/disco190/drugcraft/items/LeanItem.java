package com.disco190.drugcraft.items;

import com.disco190.drugcraft.item.DrinkableItem;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemUtils;
import net.minecraft.world.level.Level;

public class LeanItem extends DrinkableItem {

    public LeanItem(Properties properties) {
        super(properties);
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 32; // duración estándar de beber
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        // inicia la animación de beber instantáneamente
        return ItemUtils.startUsingInstantly(world, player, hand);
    }

    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity entity) {
        ItemStack result = super.finishUsingItem(stack, world, entity);

        if (entity instanceof Player player) {
            // efectos del Lean (puedes ajustarlos)
            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 20 * 15, 1)); // lentitud leve
            player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 20 * 30, 0)); // resistencia
            player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 20 * 20, 0)); // confusión leve
            player.addEffect(new MobEffectInstance(MobEffects.DIG_SLOWDOWN, 20 * 20, 1)); // debuff adicional
            player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 20 * 2, 0)); // regen I por 5s
            player.addEffect(new MobEffectInstance(MobEffects.SATURATION, 20 * 2, 0)); // saciedad instantánea leve

            // si no tiene instabuild, devolver botella
            if (!player.getAbilities().instabuild) {
                if (stack.isEmpty()) {
                    return new ItemStack(Items.GLASS_BOTTLE);
                } else if (!player.getInventory().add(new ItemStack(Items.GLASS_BOTTLE))) {
                    player.drop(new ItemStack(Items.GLASS_BOTTLE), false);
                }
            }
        }

        return result;
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.DRINK; // animación de beber
    }
}
