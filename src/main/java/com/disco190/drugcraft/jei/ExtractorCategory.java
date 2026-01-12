package com.disco190.drugcraft.jei;

import com.disco190.drugcraft.Drugcraft;
import com.disco190.drugcraft.blocks.ModBlocks;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import mezz.jei.api.recipe.RecipeType;


public class ExtractorCategory implements IRecipeCategory<ExtractorJeiRecipe> {

    public static final RecipeType<ExtractorJeiRecipe> RECIPE_TYPE =
            RecipeType.create(Drugcraft.MODID, "extractor", ExtractorJeiRecipe.class);

    private final IDrawable background;
    private final IDrawable icon;

    public ExtractorCategory(IGuiHelper guiHelper) {
        ResourceLocation texture =
                new ResourceLocation(Drugcraft.MODID, "textures/gui/extractor.png");

        this.background = guiHelper.createDrawable(texture, 40, 0, 140, 80);
        this.icon = guiHelper.createDrawableIngredient(
                VanillaTypes.ITEM_STACK,
                new ItemStack(ModBlocks.EXTRACTOR.get())
        );
    }

    @Override
    public RecipeType<ExtractorJeiRecipe> getRecipeType() {
        return RECIPE_TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("gui.drugcraft.extractor");
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder,
                          ExtractorJeiRecipe recipe,
                          IFocusGroup focuses) {

        builder.addSlot(RecipeIngredientRole.INPUT, 13, 40)
                .addItemStack(recipe.getInput());

        builder.addSlot(RecipeIngredientRole.OUTPUT, 80, 40)
                .addItemStack(recipe.getOutput());
    }
}

