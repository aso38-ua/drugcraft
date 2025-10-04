package com.disco190.drugcraft.recipes;

import com.disco190.drugcraft.item.ModItems;
import com.disco190.drugcraft.items.MorphineItem; // Tu clase de Morfina
import net.minecraft.core.NonNullList;
import net.minecraft.core.RegistryAccess;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.CraftingBookCategory;
import net.minecraft.world.item.crafting.CustomRecipe;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.level.Level;

import com.google.gson.JsonObject; // Necesario para el Serializador de JSON

// La jeringuilla vacía se asume que es ModItems.SYRINGE.get()
// El resultado se asume que es ModItems.SYRINGE_WITH_MORPHINE.get()

public class SyringeCraftingRecipe extends CustomRecipe {

    // NBT Tag (debe coincidir con la de MorphineItem)
    private static final String QUALITY_TAG = "Quality";

    // Asumimos que la jeringuilla vacía es ModItems.SYRINGE
    private static final ItemStack SYRINGE_ITEM = new ItemStack(ModItems.SYRINGE.get());

    public SyringeCraftingRecipe(ResourceLocation id, CraftingBookCategory category) {
        super(id, category);
    }

    // Comprobación del patrón: Morfina + Jeringuilla Vacía
    @Override
    public boolean matches(CraftingContainer container, Level level) {

        boolean foundMorphine = false;
        boolean foundSyringe = false;

        for (int i = 0; i < container.getContainerSize(); i++) {
            ItemStack stack = container.getItem(i);

            if (stack.isEmpty()) continue;

            // 1. Buscamos Morfina que tenga NBT de calidad
            if (stack.getItem() instanceof MorphineItem) {
                // Verificamos que tenga una calidad definida
                if (MorphineItem.getQuality(stack).equals("unknown")) continue;

                if (foundMorphine) return false;
                foundMorphine = true;
            }
            // 2. Buscamos la Jeringuilla Vacía
            else if (stack.is(ModItems.SYRINGE.get())) {
                if (foundSyringe) return false;
                foundSyringe = true;
            }
            // 3. Cualquier otro ítem invalida la receta
            else {
                return false;
            }
        }

        return foundMorphine && foundSyringe;
    }

    // Lógica de Transferencia de NBT
    @Override
    public ItemStack assemble(CraftingContainer container, RegistryAccess registryAccess) {

        ItemStack morphineStack = ItemStack.EMPTY;

        // 1. Encontrar la Morfina (para obtener el NBT)
        for (int i = 0; i < container.getContainerSize(); i++) {
            ItemStack stack = container.getItem(i);
            if (stack.getItem() instanceof MorphineItem) {
                morphineStack = stack;
                break;
            }
        }

        if (morphineStack.isEmpty()) return ItemStack.EMPTY;

        // 2. Crear el ItemStack de resultado
        ItemStack resultStack = new ItemStack(ModItems.SYRINGE_WITH_MORPHINE.get());

        // 3. TRANSFERIR el NBT de Calidad de la Morfina al Resultado
        String quality = MorphineItem.getQuality(morphineStack);
        if (!quality.equals("unknown")) {
            CompoundTag tag = resultStack.getOrCreateTag();
            tag.putString(QUALITY_TAG, quality);
        }

        return resultStack;
    }

    @Override
    public boolean canCraftInDimensions(int width, int height) {
        return width * height >= 2;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipes.SYRINGE_CRAFTING_SERIALIZER.get();
    }

    // Devuelve la jeringuilla vacía como artículo restante
    @Override
    public NonNullList<ItemStack> getRemainingItems(CraftingContainer container) {
        NonNullList<ItemStack> remaining = NonNullList.withSize(container.getContainerSize(), ItemStack.EMPTY);
        // Consumimos la Morfina y la Jeringuilla Vacía se reemplaza por la Jeringuilla con Morfina.
        // Como el crafteo consume ambos ítems, no es necesario devolver nada extra aquí.
        // Si quisieras que se devolviera la jeringuilla vacía, la lógica es más compleja, pero dado tu Item
        // de resultado, el resultado es el SyringeWithMorphine.
        return remaining;
    }

    // --- SERIALIZADOR INTERNO ---
    // Usamos el serializador interno, ya que no tienes SimpleRecipeSerializer.
    public static class Serializer implements RecipeSerializer<SyringeCraftingRecipe> {

        @Override
        public SyringeCraftingRecipe fromJson(ResourceLocation id, JsonObject jsonObject) {
            // Recetas de CustomRecipe no tienen formato JSON, simplemente se registran.
            // Si necesitas categorías, puedes leerlas aquí.
            CraftingBookCategory category = CraftingBookCategory.CODEC.byName(jsonObject.get("category").getAsString(), CraftingBookCategory.MISC);
            return new SyringeCraftingRecipe(id, category);
        }

        @Override
        public SyringeCraftingRecipe fromNetwork(ResourceLocation id, FriendlyByteBuf buffer) {
            CraftingBookCategory category = buffer.readEnum(CraftingBookCategory.class);
            return new SyringeCraftingRecipe(id, category);
        }

        @Override
        public void toNetwork(FriendlyByteBuf buffer, SyringeCraftingRecipe recipe) {
            buffer.writeEnum(recipe.category());
        }
    }
}