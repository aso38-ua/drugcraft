package com.disco190.drugcraft.jei;

import com.disco190.drugcraft.Drugcraft;
import com.disco190.drugcraft.blocks.ModBlocks;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.registration.IRecipeCatalystRegistration;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import mezz.jei.api.helpers.IGuiHelper;
import com.disco190.drugcraft.item.ModItems;
import com.disco190.drugcraft.items.GummyBearItem;
import com.disco190.drugcraft.items.WeedBrownieItem;
import mezz.jei.api.constants.RecipeTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingRecipe;
import java.util.ArrayList;
import java.util.List;

@JeiPlugin
public class DrugCraftJeiPlugin implements IModPlugin {

    @Override
    public ResourceLocation getPluginUid() {
        return new ResourceLocation(Drugcraft.MODID, "jei_plugin");
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
        IGuiHelper guiHelper = registration.getJeiHelpers().getGuiHelper();
        registration.addRecipeCategories(
                new ChemistryStationCategory(guiHelper),
                new ExtractorCategory(guiHelper));
                new DehydratorCategory(guiHelper);
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
        registration.addRecipes(
                ChemistryStationCategory.RECIPE_TYPE,
                ChemistryStationJeiRecipe.getRecipesForJEI());

        registration.addRecipes(
                ExtractorCategory.RECIPE_TYPE,
                ExtractorJeiRecipe.getRecipesForJEI());

        registration.addRecipes(
                DehydratorCategory.RECIPE_TYPE,
                DehydratorJeiRecipe.getRecipesForJEI());

        // --- Modular Recipes (Brownies, Gummy Bears, Joints) ---
        List<CraftingRecipe> syntheticRecipes = new ArrayList<>();

        // List of bud items
        List<Item> buds = List.of(
                ModItems.MARIJUANA.get(),
                ModItems.PURPLE_HAZE.get(),
                ModItems.BLAZE_KUSH.get(),
                ModItems.FUJIYAMA.get());

        // 1. Weed Brownie Recipes
        for (Item bud : buds) {
            ItemStack output = new ItemStack(ModItems.WEED_BROWNIE.get());
            WeedBrownieItem.setBudType(output, getBudName(bud));

            // Ingredients: Bud, Cocoa Beans, Wheat, Egg
            net.minecraft.core.NonNullList<net.minecraft.world.item.crafting.Ingredient> inputs = net.minecraft.core.NonNullList
                    .of(net.minecraft.world.item.crafting.Ingredient.EMPTY,
                            net.minecraft.world.item.crafting.Ingredient.of(bud),
                            net.minecraft.world.item.crafting.Ingredient.of(net.minecraft.world.item.Items.COCOA_BEANS),
                            net.minecraft.world.item.crafting.Ingredient.of(net.minecraft.world.item.Items.WHEAT),
                            net.minecraft.world.item.crafting.Ingredient.of(net.minecraft.world.item.Items.EGG));

            syntheticRecipes.add(new net.minecraft.world.item.crafting.ShapelessRecipe(
                    new ResourceLocation(Drugcraft.MODID, "jei_brownie_" + getBudName(bud)),
                    "drugcraft:brownies",
                    net.minecraft.world.item.crafting.CraftingBookCategory.MISC,
                    output,
                    inputs));
        }

        // 2. Gummy Bear Recipes
        for (Item bud : buds) {
            ItemStack output = new ItemStack(ModItems.GUMMY_BEAR.get());
            GummyBearItem.setBudType(output, getBudName(bud));

            // Ingredients: Bud, Gelatin
            net.minecraft.core.NonNullList<net.minecraft.world.item.crafting.Ingredient> inputs = net.minecraft.core.NonNullList
                    .of(net.minecraft.world.item.crafting.Ingredient.EMPTY,
                            net.minecraft.world.item.crafting.Ingredient.of(bud),
                            net.minecraft.world.item.crafting.Ingredient.of(ModItems.GELATIN.get()));

            syntheticRecipes.add(new net.minecraft.world.item.crafting.ShapelessRecipe(
                    new ResourceLocation(Drugcraft.MODID, "jei_gummy_" + getBudName(bud)),
                    "drugcraft:gummies",
                    net.minecraft.world.item.crafting.CraftingBookCategory.MISC,
                    output,
                    inputs));
        }

        // 3. Modular Joints (Paper + Bud)
        for (Item bud : buds) {
            ItemStack output = ItemStack.EMPTY;
            String budName = getBudName(bud);

            if (bud == ModItems.MARIJUANA.get())
                output = new ItemStack(ModItems.CANNABIS_JOINT.get());
            else if (bud == ModItems.PURPLE_HAZE.get())
                output = new ItemStack(ModItems.PURPLE_HAZE_JOINT.get());
            else if (bud == ModItems.BLAZE_KUSH.get())
                output = new ItemStack(ModItems.BLAZE_KUSH_JOINT.get());
            else if (bud == ModItems.FUJIYAMA.get())
                output = new ItemStack(ModItems.FUJIYAMA_JOINT.get());

            if (!output.isEmpty()) {
                net.minecraft.core.NonNullList<net.minecraft.world.item.crafting.Ingredient> inputs = net.minecraft.core.NonNullList
                        .of(net.minecraft.world.item.crafting.Ingredient.EMPTY,
                                net.minecraft.world.item.crafting.Ingredient.of(bud),
                                net.minecraft.world.item.crafting.Ingredient.of(net.minecraft.world.item.Items.PAPER));

                syntheticRecipes.add(new net.minecraft.world.item.crafting.ShapelessRecipe(
                        new ResourceLocation(Drugcraft.MODID, "jei_joint_" + budName),
                        "drugcraft:joints",
                        net.minecraft.world.item.crafting.CraftingBookCategory.MISC,
                        output,
                        inputs));
            }
        }

        registration.addRecipes(RecipeTypes.CRAFTING, syntheticRecipes);
    }

    private String getBudName(Item bud) {
        if (bud == ModItems.MARIJUANA.get())
            return "marijuana";
        if (bud == ModItems.PURPLE_HAZE.get())
            return "purple_haze";
        if (bud == ModItems.BLAZE_KUSH.get())
            return "blaze_kush";
        if (bud == ModItems.FUJIYAMA.get())
            return "fujiyama";
        return "unknown";
    }

    @Override
    public void registerRecipeCatalysts(IRecipeCatalystRegistration registration) {
        registration.addRecipeCatalyst(
                new ItemStack(ModBlocks.CHEMISTRY_STATION.get()),
                ChemistryStationCategory.RECIPE_TYPE);

        registration.addRecipeCatalyst(
                new ItemStack(ModBlocks.EXTRACTOR.get()),
                ExtractorCategory.RECIPE_TYPE);
    }

}
