package com.disco190.drugcraft.recipes;

import com.disco190.drugcraft.item.ModItems;
import com.disco190.drugcraft.items.LiquidMethItem;
import com.disco190.drugcraft.items.MorphineItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.*;

public class ChemistryStationRecipes {

    private static final Map<List<Item>, ItemStack> recipes = new HashMap<>();

    static {
        // --- Receta de media calidad ---

        {
            ItemStack medium = new ItemStack(ModItems.BLUE_LIQUID_METH.get());
            LiquidMethItem.setQuality(medium, "very_high");

            recipes.put(
                    Arrays.asList(ModItems.ACID.get(), ModItems.PHOSPHOR.get(), ModItems.METHYLAMINE.get()),
                    createBlueLiquidMeth("very_high")
            );
        }

        {
            ItemStack medium = new ItemStack(ModItems.LIQUID_METH.get());
            LiquidMethItem.setQuality(medium, "medium");

            recipes.put(
                    Arrays.asList(ModItems.ACID.get(), ModItems.PHOSPHOR.get(), ModItems.EPHEDRINE.get()),
                    createLiquidMeth("medium")
            );
        }

        {
            ItemStack medium = new ItemStack(ModItems.LIQUID_METH.get());
            LiquidMethItem.setQuality(medium, "low");

            recipes.put(
                    Arrays.asList(Items.GUNPOWDER, ModItems.PHOSPHOR.get(), ModItems.EPHEDRINE.get()),
                    createLiquidMeth("low")
            );
        }

        recipes.put(
                Arrays.asList(
                        ModItems.OPIUM_LATEX.get(),   // materia prima
                        ModItems.ACID.get(),          // reactivo ácido
                        ModItems.PHOSPHOR.get()       // catalizador
                ),
                new ItemStack(ModItems.OPIUM.get())
        );

        recipes.put(
                Arrays.asList(
                        ModItems.OPIUM.get(),   // materia prima
                        ModItems.EPHEDRINE.get(),
                        Items.GLASS_BOTTLE
                ),
                createMorphine("medium")
        );

        recipes.put(
                Arrays.asList(
                        ModItems.OPIUM.get(),   // materia prima
                        ModItems.PURE_EPHEDRINE.get(),
                        Items.GLASS_BOTTLE
                ),
                createMorphine("high")
        );


        {
            ItemStack medium = new ItemStack(ModItems.LIQUID_METH.get());
            LiquidMethItem.setQuality(medium, "burnt");

            recipes.put(
                    Arrays.asList(Items.GUNPOWDER, Items.REDSTONE, ModItems.EPHEDRA_BERRIES.get()),
                    createLiquidMeth("burnt")
            );
        }

        // --- Receta de media calidad ---
        {
            ItemStack medium = new ItemStack(ModItems.LIQUID_METH.get());
            LiquidMethItem.setQuality(medium, "high");

            recipes.put(
                    Arrays.asList(ModItems.ACID.get(), ModItems.PHOSPHOR.get(), ModItems.PURE_EPHEDRINE.get()),
                    createLiquidMeth("high")
            );
        }

        // Ejemplo de otra receta: ACID crafting
        recipes.put(
                Arrays.asList(
                        Items.GUNPOWDER,
                        ModItems.PHOSPHOR.get(),
                        Items.POTION
                ),
                new ItemStack(ModItems.ACID.get())
        );

        recipes.put(
                Arrays.asList(
                        ModItems.EPHEDRINE.get(),   // ephedrine base (ya existente en tu mod)
                        Items.GLOWSTONE_DUST,       // purificación / catalizador
                        Items.REDSTONE              // reactivo/catalizador adicional (coste)
                ),
                new ItemStack(ModItems.PURE_EPHEDRINE.get())
        );

        recipes.put(
                Arrays.asList(
                        ModItems.PURE_EPHEDRINE.get(), // requiere la pure_ephedrine (cadena de crafting)
                        Items.BLAZE_POWDER,            // energía/calor (elemento valioso)
                        Items.GUNPOWDER                // componente reactivo / peligroso (ajusta si quieres)
                ),
                new ItemStack(ModItems.METHYLAMINE.get())
        );
    }

    private static ItemStack createLiquidMeth(String quality) {
        ItemStack stack = new ItemStack(ModItems.LIQUID_METH.get());
        LiquidMethItem.setQuality(stack, quality);

        // ELIMINAR ESTE BLOQUE DE ASIGNACIÓN DE PUREZA:
    /*
    Random rand = new Random();
    int purity = switch (quality) {
        case "very_high" -> 90 + rand.nextInt(11); // 90–100
        case "high" -> 70 + rand.nextInt(21);   // 70–90
        case "medium" -> 40 + rand.nextInt(30); // 50–69
        case "low" -> 10 + rand.nextInt(31);    // 10–40
        case "burnt" -> rand.nextInt(10);       // 0–10
        default -> 0;
    };
    LiquidMethItem.setPurity(stack, purity);
    */

        // Opcional: Si LiquidMethItem requiere que 'Purity' esté presente,
        // podrías establecerla a 0, pero lo mejor es dejar que la máquina lo haga.
        // LiquidMethItem.setPurity(stack, 0);

        return stack;
    }

    private static ItemStack createBlueLiquidMeth(String quality) {
        ItemStack stack = new ItemStack(ModItems.BLUE_LIQUID_METH.get());
        LiquidMethItem.setQuality(stack, quality);

        Random rand = new Random();
        int purity = switch (quality) {
            case "very_high" -> 90 + rand.nextInt(11);
            case "high" -> 70 + rand.nextInt(21);
            case "medium" -> 40 + rand.nextInt(30);
            case "low" -> 10 + rand.nextInt(31);
            case "burnt" -> rand.nextInt(10);
            default -> 0;
        };
        LiquidMethItem.setPurity(stack, purity);
        return stack;
    }


    private static ItemStack createMorphine(String quality) {
        ItemStack stack = new ItemStack(ModItems.MORPHINE.get());
        MorphineItem.setQuality(stack, quality);
        return stack;
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

                // DEVOLVER SOLO UNA COPIA LIMPIA DEL ÍTEM DE LA RECETA BASE
                ItemStack result = base.copy();

                // NO ES NECESARIO HACER UNA COPIA PROFUNDA DEL TAG SI ASUMIMOS
                // QUE LA RECETA BASE SOLO TIENE LA CALIDAD.
                // Si el ítem base tuviera NBT, sí se necesita copiar el NBT también.
                // Puesto que ya estás haciendo base.copy() y los tags se copian con base.copy()
                // si el stack no está vacío, no hay problema, siempre que la pureza sea 0.

                return result;
            }
        }
        return ItemStack.EMPTY;
    }

    public static Map<ItemStack, List<ItemStack>> getAllRecipes() {
        Map<ItemStack, List<ItemStack>> jeiRecipes = new HashMap<>();

        for (Map.Entry<List<Item>, ItemStack> entry : recipes.entrySet()) {
            List<ItemStack> ingredients = new ArrayList<>();
            for (Item item : entry.getKey()) {
                ingredients.add(new ItemStack(item));
            }
            jeiRecipes.put(entry.getValue().copy(), ingredients);
        }

        return jeiRecipes;
    }

}
