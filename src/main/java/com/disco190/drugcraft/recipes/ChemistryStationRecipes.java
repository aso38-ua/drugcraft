package com.disco190.drugcraft.recipes;

import com.disco190.drugcraft.item.ModItems;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.*;

public class ChemistryStationRecipes {

    private static final Map<List<Item>, ItemStack> recipes = new HashMap<>();

    static {
        // Ejemplo: ACID + PHOSPHOR + PSEUDO → MET
        recipes.put(Arrays.asList(ModItems.ACID.get(), ModItems.PHOSPHOR.get(), ModItems.PSEUDO.get()),
                new ItemStack(ModItems.ACID.get())); //CAMBIAR POR META CUANDO LA TENGA

        recipes.put(
                Arrays.asList(
                        Items.GUNPOWDER,        // Pólvora
                        ModItems.PHOSPHOR.get(), // Fósforo de tu mod
                        Items.POTION              // Agua / frasco de agua (se puede crear un item de agua del mod)
                ),
                new ItemStack(ModItems.ACID.get()) // Resultado: ácido
        );
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
            if (matches) return entry.getValue().copy();
        }
        return ItemStack.EMPTY;
    }

}
