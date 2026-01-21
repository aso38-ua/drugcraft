package com.disco190.drugcraft.recipes;

import com.disco190.drugcraft.item.ModItems;
import net.minecraft.world.item.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class DehydratorRecipes {

    private static final Map<ItemStack, ItemStack> recipes = new HashMap<>();

    static {
        // Liquid Heroin -> Heroin
        recipes.put(new ItemStack(ModItems.LIQUID_HEROIN.get()), new ItemStack(ModItems.HEROIN.get()));

        // Tobacco Curing
        recipes.put(new ItemStack(ModItems.CAPA.get()), new ItemStack(ModItems.CURED_CAPA.get()));
        recipes.put(new ItemStack(ModItems.CAPOTE.get()), new ItemStack(ModItems.CURED_CAPOTE.get()));
        recipes.put(new ItemStack(ModItems.TRIPA.get()), new ItemStack(ModItems.CURED_TRIPA.get()));
    }

    public static ItemStack getResult(ItemStack input) {
        for (Map.Entry<ItemStack, ItemStack> entry : recipes.entrySet()) {
            if (entry.getKey().getItem() == input.getItem()) {
                // Return copy of result
                return entry.getValue().copy();
            }
        }
        return ItemStack.EMPTY;
    }

    public static Map<ItemStack, ItemStack> getRecipes() {
        return recipes;
    }
}
