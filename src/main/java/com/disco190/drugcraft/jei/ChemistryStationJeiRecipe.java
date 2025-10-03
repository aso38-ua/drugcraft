package com.disco190.drugcraft.jei;

import com.disco190.drugcraft.recipes.ChemistryStationRecipes;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ChemistryStationJeiRecipe {

    private final List<ItemStack> ingredients;
    private final ItemStack result;

    public ChemistryStationJeiRecipe(List<ItemStack> ingredients, ItemStack result) {
        this.ingredients = ingredients;
        this.result = result;
    }

    public List<ItemStack> getIngredients() {
        return ingredients;
    }

    public ItemStack getResult() {
        return result;
    }

    public static List<ChemistryStationJeiRecipe> getRecipesForJEI() {
        List<ChemistryStationJeiRecipe> recipes = new ArrayList<>();

        // Adaptar según tu propia estructura de recetas
        for (Map.Entry<ItemStack, List<ItemStack>> entry : ChemistryStationRecipes.getAllRecipes().entrySet()) {
            recipes.add(new ChemistryStationJeiRecipe(entry.getValue(), entry.getKey()));
        }

        return recipes;
    }
}
