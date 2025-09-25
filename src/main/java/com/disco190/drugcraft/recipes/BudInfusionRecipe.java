package com.disco190.drugcraft.recipes;

import com.disco190.drugcraft.items.ModularBudItem;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.Level;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class BudInfusionRecipe implements CraftingRecipe {

    private final ResourceLocation id;

    // Mapa centralizado de modificadores -> nombre + color
    private static final Map<Item, ModifierData> MODIFIERS = new HashMap<>();

    static {
        MODIFIERS.put(Items.GLOWSTONE_DUST, new ModifierData("glowing", 0xFFD700));
        MODIFIERS.put(Items.REDSTONE, new ModifierData("speedy", 0xFF0000));
        MODIFIERS.put(Items.SUGAR, new ModifierData("sweet", 0x00FF00));
        MODIFIERS.put(Items.GHAST_TEAR, new ModifierData("regenerative", 0x87CEFA));
        MODIFIERS.put(Items.BLAZE_POWDER, new ModifierData("infernal", 0xFF6600));
        MODIFIERS.put(Items.MAGMA_CREAM, new ModifierData("resistant", 0xFF4500));
        MODIFIERS.put(Items.ENDER_PEARL, new ModifierData("warped", 0x551A8B));
        MODIFIERS.put(Items.PHANTOM_MEMBRANE, new ModifierData("dreamy", 0xC0C0C0));
        MODIFIERS.put(Items.CHORUS_FRUIT, new ModifierData("warping", 0xDA70D6));
        MODIFIERS.put(Items.DRAGON_BREATH, new ModifierData("chaotic", 0xFF1493));
    }

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
            if (MODIFIERS.containsKey(stack.getItem())) {
                hasModifier = true;
            }
        }
        return hasBud && hasModifier;
    }

    @Override
    public ItemStack assemble(CraftingContainer container, RegistryAccess registryAccess) {
        ItemStack bud = ItemStack.EMPTY;
        ItemStack modifierItem = ItemStack.EMPTY;

        for (int i = 0; i < container.getContainerSize(); i++) {
            ItemStack stack = container.getItem(i);
            if (stack.getItem() instanceof ModularBudItem) bud = stack.copy();
            if (MODIFIERS.containsKey(stack.getItem())) modifierItem = stack;
        }

        if (!bud.isEmpty() && !modifierItem.isEmpty()) {
            ModifierData data = MODIFIERS.get(modifierItem.getItem());
            ModularBudItem.setModifier(bud, data.name(), data.color());
        }

        return bud;
    }

    @Override
    public boolean canCraftInDimensions(int w, int h) {
        return w * h >= 2;
    }

    @Override
    public ItemStack getResultItem(RegistryAccess registryAccess) {
        return ItemStack.EMPTY; // dinámico según ingrediente
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

    // Pequeño record para agrupar datos de modificador
    private record ModifierData(String name, int color) {}
}
