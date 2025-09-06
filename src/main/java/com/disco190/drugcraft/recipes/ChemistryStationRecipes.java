package com.disco190.drugcraft.recipes;

import com.disco190.drugcraft.item.ModItems;
import com.disco190.drugcraft.items.LiquidMethItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.*;

public class ChemistryStationRecipes {

    private static final Map<List<Item>, ItemStack> recipes = new HashMap<>();

    static {
        // --- Receta de baja calidad ---
        {
            ItemStack low = new ItemStack(ModItems.LIQUID_METH.get());
            LiquidMethItem.setQuality(low, "low");

            recipes.put(
                    Arrays.asList(ModItems.ACID.get(), ModItems.PHOSPHOR.get(), ModItems.EPHEDRINE.get()),
                    createLiquidMeth("low")
            );
        }

        // --- Receta de calidad media ---
        /*{
            ItemStack medium = new ItemStack(ModItems.LIQUID_METH.get());
            LiquidMethItem.setQuality(medium, "medium");

            recipes.put(
                    Arrays.asList(ModItems.ACID.get(), ModItems.PHOSPHOR.get(), ModItems.EPHEDRINE.get()),
                    medium
            );
        }*/

        // --- Receta de alta calidad ---
        /*{
            ItemStack high = new ItemStack(ModItems.LIQUID_METH.get());
            LiquidMethItem.setQuality(high, "high");

            recipes.put(
                    Arrays.asList(ModItems.ACID.get(), ModItems.PHOSPHOR.get(), ModItems.PURE_EPHEDRINE.get()),
                    high
            );
        }*/

        // Ejemplo de otra receta: ACID crafting
        recipes.put(
                Arrays.asList(
                        Items.GUNPOWDER,
                        ModItems.PHOSPHOR.get(),
                        Items.POTION
                ),
                new ItemStack(ModItems.ACID.get())
        );
    }

    private static ItemStack createLiquidMeth(String quality) {
        ItemStack result = new ItemStack(ModItems.LIQUID_METH.get());
        LiquidMethItem.setQuality(result, quality);
        return result;
    }



    // Devuelve el resultado de la receta según los items de entrada
    public static ItemStack getResult(List<ItemStack> inputs) {
        for (Map.Entry<List<Item>, ItemStack> entry : recipes.entrySet()) {
            List<Item> recipe = entry.getKey();
            boolean matches = true;
            for (Item r : recipe) {
                boolean found = false;
                for (ItemStack s : inputs) {
                    if (s.getItem() == r) {
                        found = true;
                        break;
                    }
                }
                if (!found) {
                    matches = false;
                    break;
                }
            }
            if (matches) {
                ItemStack base = entry.getValue();
                ItemStack result = new ItemStack(base.getItem(), base.getCount());
                if (base.hasTag()) {
                    result.setTag(base.getTag().copy());
                }
                return result;
            }

        }
        return ItemStack.EMPTY;
    }

}
