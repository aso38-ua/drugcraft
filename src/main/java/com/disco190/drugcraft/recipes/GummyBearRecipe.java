package com.disco190.drugcraft.recipes;

import com.disco190.drugcraft.item.ModItems;
import com.google.gson.JsonObject;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class GummyBearRecipe extends CustomRecipe {
    public GummyBearRecipe(ResourceLocation id, CraftingBookCategory category) {
        super(id, category);
    }

    @Override
    public boolean matches(CraftingContainer container, Level level) {
        boolean hasBud = false;
        boolean hasGelatin = false;

        for (int i = 0; i < container.getContainerSize(); i++) {
            ItemStack stack = container.getItem(i);
            if (stack.isEmpty())
                continue;

            if (stack.is(ModItems.PURPLE_HAZE.get()) ||
                    stack.is(ModItems.FUJIYAMA.get()) ||
                    stack.is(ModItems.MARIJUANA.get()) ||
                    stack.is(ModItems.BLAZE_KUSH.get())) {
                if (hasBud)
                    return false; // Solo 1 bud
                hasBud = true;
            } else if (stack.is(ModItems.GELATIN.get())) {
                if (hasGelatin)
                    return false; // Solo 1 gelatin
                hasGelatin = true;
            } else {
                return false; // ingrediente extraño
            }
        }

        return hasBud && hasGelatin;
    }

    @Override
    public @NotNull ItemStack assemble(CraftingContainer container, RegistryAccess registryAccess) {
        ItemStack result = new ItemStack(ModItems.GUMMY_BEAR.get());
        String budType = "unknown";

        for (int i = 0; i < container.getContainerSize(); i++) {
            ItemStack stack = container.getItem(i);
            if (stack.is(ModItems.PURPLE_HAZE.get()))
                budType = "purple_haze";
            else if (stack.is(ModItems.FUJIYAMA.get()))
                budType = "fujiyama";
            else if (stack.is(ModItems.MARIJUANA.get()))
                budType = "marijuana";
            else if (stack.is(ModItems.BLAZE_KUSH.get()))
                budType = "blaze_kush";
        }

        CompoundTag tag = result.getOrCreateTag();
        tag.putString("BudType", budType);
        return result;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= 2;
    }

    @Override
    public @NotNull ItemStack getResultItem(RegistryAccess registryAccess) {
        return new ItemStack(ModItems.GUMMY_BEAR.get());
    }

    @Override
    public @NotNull RecipeSerializer<?> getSerializer() {
        return ModRecipes.GUMMY_BEAR_SERIALIZER.get();
    }

    public static class Serializer implements RecipeSerializer<GummyBearRecipe> {
        public static final Serializer INSTANCE = new Serializer();

        @Override
        public @NotNull GummyBearRecipe fromJson(ResourceLocation id, JsonObject json) {
            return new GummyBearRecipe(id, CraftingBookCategory.MISC);
        }

        @Override
        public GummyBearRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buf) {
            return new GummyBearRecipe(id, CraftingBookCategory.MISC);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, GummyBearRecipe recipe) {
        }
    }
}
