package com.disco190.drugcraft.recipes;

import com.disco190.drugcraft.items.ModularBudItem;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;

public class BudInfusionRecipe implements CraftingRecipe {

    private final ResourceLocation id;

    public BudInfusionRecipe(ResourceLocation id) {
        this.id = id;
    }

    @Override
    public boolean matches(CraftingContainer container, Level world) {
        boolean hasBud = false;
        boolean hasModifier = false;

        for (int i = 0; i < container.getContainerSize(); i++) {
            ItemStack stack = container.getItem(i);
            if (stack.getItem() instanceof ModularBudItem) {
                hasBud = true;
            }
            if (stack.getItem() == Items.GLOWSTONE_DUST
                    || stack.getItem() == Items.REDSTONE
                    || stack.getItem() == Items.SUGAR) {
                hasModifier = true;
            }
        }
        return hasBud && hasModifier;
    }


    @Override
    public ItemStack assemble(CraftingContainer container, RegistryAccess registryAccess) {
        ItemStack bud = ItemStack.EMPTY;
        ItemStack modifierItem = ItemStack.EMPTY;

        // Buscar cogollo y item modificador
        for (int i = 0; i < container.getContainerSize(); i++) {
            ItemStack stack = container.getItem(i);
            if (stack.getItem() instanceof ModularBudItem) bud = stack.copy();

            if (stack.getItem() == Items.GLOWSTONE_DUST
                    || stack.getItem() == Items.REDSTONE
                    || stack.getItem() == Items.SUGAR) {
                modifierItem = stack;
            }

        }

        // Aplicar modificador según el item usado
        ResourceLocation id = ForgeRegistries.ITEMS.getKey(modifierItem.getItem());
        if (id != null) {
            switch (id.getPath()) {
                case "glowstone_dust" -> ModularBudItem.setModifier(bud, "glowing", 0xFFD700);
                case "redstone"       -> ModularBudItem.setModifier(bud, "speedy", 0xFF0000);
                case "sugar"          -> ModularBudItem.setModifier(bud, "strong", 0x00FF00);
                // aquí puedes añadir más materiales y efectos
            }
        }

        return bud;
    }

    @Override
    public boolean canCraftInDimensions(int w, int h) {
        return w * h >= 2;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess registryAccess) {
        return ItemStack.EMPTY; // se genera dinámicamente
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.BUD_INFUSION_SERIALIZER.get();
    }

    @Override
    public RecipeType<?> getType() {
        return RecipeType.CRAFTING;
    }

    @Override
    public CraftingBookCategory category() {
        return CraftingBookCategory.MISC;
    }
}
