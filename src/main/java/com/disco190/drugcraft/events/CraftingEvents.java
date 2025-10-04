package com.disco190.drugcraft.events;

import com.disco190.drugcraft.item.ModItems;
import com.disco190.drugcraft.items.MorphineItem;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "drugcraft")
public class CraftingEvents {

    @SubscribeEvent
    public static void onItemCrafted(PlayerEvent.ItemCraftedEvent event) {
        ItemStack result = event.getCrafting();
        if (result.isEmpty()) return;

        if (result.getItem() == ModItems.SYRINGE_WITH_MORPHINE.get()) {

            // Solo en servidor
            if (event.getEntity().level().isClientSide()) return;

            Container matrix = event.getInventory();
            for (int i = 0; i < matrix.getContainerSize(); i++) {
                ItemStack ingredient = matrix.getItem(i);
                if (!ingredient.isEmpty() && ingredient.getItem() == ModItems.MORPHINE.get()) {
                    String quality = MorphineItem.getQuality(ingredient);
                    if (quality != null && !quality.equals("unknown")) {
                        MorphineItem.setQuality(result, quality);
                    }
                    break;
                }
            }
        }
    }
}
