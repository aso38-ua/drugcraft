package com.disco190.drugcraft.jei;

import com.disco190.drugcraft.Drugcraft;
import com.disco190.drugcraft.blocks.ModBlocks;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.RecipeType;
import mezz.jei.api.recipe.category.IRecipeCategory;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class ChemistryStationCategory implements IRecipeCategory<ChemistryStationJeiRecipe> {

    public static final RecipeType<ChemistryStationJeiRecipe> RECIPE_TYPE =
            RecipeType.create(Drugcraft.MODID, "chemistry_station", ChemistryStationJeiRecipe.class);

    private final IDrawable background;
    private final IDrawable icon;
    private final Component localizedName;

    public ChemistryStationCategory(IGuiHelper guiHelper) {
        ResourceLocation texture = new ResourceLocation(Drugcraft.MODID, "textures/gui/chemistry_station.png");
        this.background = guiHelper.createDrawable(texture, 0, 0, 140, 80);
        this.icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.CHEMISTRY_STATION.get()));
        this.localizedName = Component.translatable("gui." + Drugcraft.MODID + ".chemistry_station");
    }

    @Override
    public RecipeType<ChemistryStationJeiRecipe> getRecipeType() {
        return RECIPE_TYPE;
    }

    @Override
    public Component getTitle() {
        return localizedName;
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
    public void setRecipe(IRecipeLayoutBuilder builder, ChemistryStationJeiRecipe recipe, IFocusGroup focuses) {
        List<ItemStack> ingredients = recipe.getIngredients();

        // Slots de los ingredientes (coordenadas relativas a la GUI)
        builder.addSlot(RecipeIngredientRole.INPUT, 15, 15) // primer ingrediente
                .addIngredients(VanillaTypes.ITEM_STACK, List.of(ingredients.get(0)));

        builder.addSlot(RecipeIngredientRole.INPUT, 38, 33) // segundo ingrediente
                .addIngredients(VanillaTypes.ITEM_STACK, List.of(ingredients.get(1)));

        builder.addSlot(RecipeIngredientRole.INPUT, 15, 52) // tercer ingrediente
                .addIngredients(VanillaTypes.ITEM_STACK, List.of(ingredients.get(2)));

        // Slot de resultado (coordenadas relativas a la GUI)
        builder.addSlot(RecipeIngredientRole.OUTPUT, 88, 33)
                .addItemStack(recipe.getResult());
    }


}
