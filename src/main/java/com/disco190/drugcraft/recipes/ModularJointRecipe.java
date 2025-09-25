package com.disco190.drugcraft.recipes;

import com.disco190.drugcraft.item.ModItems;
import com.disco190.drugcraft.items.ModularBudItem;
import com.disco190.drugcraft.items.ModularJointItem;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;

public class ModularJointRecipe implements CraftingRecipe {

    private final ResourceLocation id;

    public ModularJointRecipe(ResourceLocation id) {
        this.id = id;
    }

    @Override
    public boolean matches(CraftingContainer container, Level world) {
        boolean hasBud = false;
        boolean hasPaper = false;

        for (int i = 0; i < container.getContainerSize(); i++) {
            ItemStack stack = container.getItem(i);
            if (!hasBud && stack.getItem() instanceof ModularBudItem) hasBud = true;
            if (!hasPaper && stack.getItem() == Items.PAPER) hasPaper = true;
        }
        return hasBud && hasPaper;
    }

    @Override
    public ItemStack assemble(CraftingContainer container, RegistryAccess registryAccess) {
        ItemStack bud = ItemStack.EMPTY;

        // Buscar el primer cogollo en la cuadrícula
        for (int i = 0; i < container.getContainerSize(); i++) {
            ItemStack stack = container.getItem(i);
            if (stack.getItem() instanceof ModularBudItem) {
                bud = stack.copy();
                break;
            }
        }

        if (bud.isEmpty()) return ItemStack.EMPTY; // No hay cogollo

        // Determinar tipo de bud
        String budType = ModularBudItem.getBudType(bud);

        // Crear el joint correspondiente
        ItemStack joint;
        switch (budType) {
            case "purple_haze" -> joint = new ItemStack(ModItems.PURPLE_HAZE_JOINT.get());
            case "fujiyama" -> joint = new ItemStack(ModItems.FUJIYAMA_JOINT.get());
            case "blaze_kush" -> joint = new ItemStack(ModItems.BLAZE_KUSH_JOINT.get());
            default            -> joint = new ItemStack(ModItems.CANNABIS_JOINT.get());
        }

        // Copiar modificadores si existen
        if (bud.hasTag() && bud.getTag().contains("Modifier")) {
            ModularJointItem.setModifier(joint,
                    bud.getTag().getString("Modifier"),
                    bud.getTag().getInt("Color")
            );
        }

        return joint;
    }


    @Override
    public boolean canCraftInDimensions(int w, int h) {
        return w * h >= 2;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess registryAccess) {
        return new ItemStack(ModItems.CANNABIS_JOINT.get());
    }

    @Override
    public ResourceLocation getId() {
        return id;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.MODULAR_JOINT_SERIALIZER.get();
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
