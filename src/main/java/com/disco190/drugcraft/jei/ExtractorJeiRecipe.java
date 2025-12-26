package com.disco190.drugcraft.jei;

import com.disco190.drugcraft.recipes.ExtractorRecipes;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class ExtractorJeiRecipe {

    private final ItemStack input;
    private final ItemStack output;

    public ExtractorJeiRecipe(ItemStack input, ItemStack output) {
        this.input = input;
        this.output = output;
    }

    public ItemStack getInput() {
        return input;
    }

    public ItemStack getOutput() {
        return output;
    }

    public static List<ExtractorJeiRecipe> getRecipesForJEI() {
        List<ExtractorJeiRecipe> recipes = new ArrayList<>();

        for (var entry : ExtractorRecipes.getAllRecipes().entrySet()) {
            recipes.add(new ExtractorJeiRecipe(
                    new ItemStack(entry.getKey()),
                    entry.getValue()
            ));
        }

        return recipes;
    }
}

