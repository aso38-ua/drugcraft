package com.disco190.drugcraft.recipes;

import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class BudInfusionSerializer implements RecipeSerializer<BudInfusionRecipe> {

    @Override
    public BudInfusionRecipe fromJson(ResourceLocation id, JsonObject json) {
        return new BudInfusionRecipe(id);
    }

    @Override
    public BudInfusionRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
        return new BudInfusionRecipe(id);
    }

    @Override
    public void toNetwork(FriendlyByteBuf buf, BudInfusionRecipe recipe) {
        // no guardamos nada especial, solo el id
    }
}
