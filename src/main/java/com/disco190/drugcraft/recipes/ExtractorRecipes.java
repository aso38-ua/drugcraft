package com.disco190.drugcraft.recipes;

import com.disco190.drugcraft.item.ModItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class ExtractorRecipes {

    private static final Map<Item, ItemStack> recipes = new HashMap<>();

    static {
        // Solo latex → opio
        recipes.put(ModItems.OPIUM_LATEX.get(), new ItemStack(ModItems.OPIUM.get()));
    }

    public static ItemStack getResult(ItemStack input) {
        if (input.isEmpty()) return ItemStack.EMPTY;

        ItemStack result = recipes.get(input.getItem());
        return result != null ? result.copy() : ItemStack.EMPTY;
    }
}
