package com.disco190.drugcraft.items;

import com.disco190.drugcraft.item.ModItems;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class PackOfCigaretteItem extends Item {
    public PackOfCigaretteItem(Item.Properties properties) {
        super(properties.durability(20)); //  se usa 20 veces
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (!world.isClientSide) {
            // da un cigarro al jugador
            ItemStack cigarette = new ItemStack(ModItems.CIGARETTE.get());
            if (!player.addItem(cigarette)) {
                // si no hay espacio, lo tira al suelo
                player.drop(cigarette, false);
            }

            // gasta un "uso" de la caja
            stack.hurtAndBreak(1, player, (p) -> p.broadcastBreakEvent(hand));
        }

        return InteractionResultHolder.sidedSuccess(stack, world.isClientSide());
    }
}
