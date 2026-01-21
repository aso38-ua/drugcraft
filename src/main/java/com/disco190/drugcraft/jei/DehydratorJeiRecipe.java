package com.disco190.drugcraft.jei;

import com.disco190.drugcraft.recipes.ExtractorRecipes;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class DehydratorJeiRecipe {

    private final ItemStack input;
    private final ItemStack output;

    public DehydratorJeiRecipe(ItemStack input, ItemStack output) {
        this.input = input;
        this.output = output;
    }

    public ItemStack getInput() {
        return input;
    }

    public ItemStack getOutput() {
        return output;
    }

    public static List<DehydratorJeiRecipe> getRecipesForJEI() {
        List<DehydratorJeiRecipe> recipes = new ArrayList<>();

        for (var entry : ExtractorRecipes.getAllRecipes().entrySet()) {
            recipes.add(new DehydratorJeiRecipe(
                    new ItemStack(entry.getKey()),
                    entry.getValue()
            ));
        }

        return recipes;
    }
}

