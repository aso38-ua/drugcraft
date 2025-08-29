package com.disco190.drugcraft.recipes;

import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.crafting.RecipeSerializer;

public class ModularJointSerializer implements RecipeSerializer<ModularJointRecipe> {

    @Override
    public ModularJointRecipe fromJson(ResourceLocation id, JsonObject json) {
        return new ModularJointRecipe(id);
    }

    @Override
    public ModularJointRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
        return new ModularJointRecipe(id);
    }

    @Override
    public void toNetwork(FriendlyByteBuf buf, ModularJointRecipe recipe) {}
}
