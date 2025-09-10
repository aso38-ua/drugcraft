package com.disco190.drugcraft.events;

import com.disco190.drugcraft.items.ModularBudItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = "drugcraft")
public class CraftingEvents {/*

    @SubscribeEvent
    public static void onCraft(PlayerEvent.ItemCraftedEvent event) {
        ItemStack result = event.getCrafting();

        // Verificar que el resultado es un ModularBudItem
        if (result.getItem() instanceof ModularBudItem) {
            boolean hasGlowstone = false;

            // Recorremos el inventario de la mesa de crafteo
            for (int i = 0; i < event.getInventory().getContainerSize(); i++) {
                ItemStack ingredient = event.getInventory().getItem(i);
                if (!ingredient.isEmpty() && ingredient.getItem() == Items.GLOWSTONE_DUST) {
                    hasGlowstone = true;
                    break;
                }
            }

            if (hasGlowstone) {
                ModularBudItem.setModifier(result, "glowing", 0xFFD700);
            }
        }
    }*/
}
