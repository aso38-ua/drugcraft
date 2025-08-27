package com.disco190.drugcraft.item;

import com.disco190.drugcraft.Drugcraft;
import com.disco190.drugcraft.blocks.ModBlocks;
import com.disco190.drugcraft.items.CannabisJointItem;
import com.disco190.drugcraft.items.EphedraBerryItem;
import com.disco190.drugcraft.items.MooshroomsItem;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemNameBlockItem;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Drugcraft.MODID);

    //======================= MARIJUANA ===========================================
    // Cogollo
    public static final RegistryObject<Item> MARIJUANA = ITEMS.register("marijuana",
            () -> new Item(new Item.Properties()));

    // Semillas
    public static final RegistryObject<Item> MARIJUANA_SEEDS = ITEMS.register("marijuanaseeds",
            () -> new ItemNameBlockItem(ModBlocks.MARIJUANA_CROP.get(), new Item.Properties()));

    // Porro
    public static final RegistryObject<Item> CANNABIS_JOINT = ITEMS.register("cannabis_joint",
            () -> new CannabisJointItem(new Item.Properties()
                     // 3 caladas
                    .food(new FoodProperties.Builder()
                            .nutrition(0)       // no afecta hambre
                            .saturationMod(0f)  // no da saturación
                            .alwaysEat()        // permite usar siempre
                            .build())
            ));

    //======================== TOBACCO ===========================================
    public static final RegistryObject<Item> TOBACCO_SEEDS = ITEMS.register("tobacco_seeds",
            () -> new ItemNameBlockItem(ModBlocks.TOBACCO_CROP.get(), new Item.Properties()));

    public static final RegistryObject<Item> VOLADO_LEAF = ITEMS.register("volado_leaf",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> SECO_LEAF = ITEMS.register("seco_leaf",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> LIGERO_LEAF = ITEMS.register("ligero_leaf",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> CORONA_LEAF = ITEMS.register("corona_leaf",
            () -> new Item(new Item.Properties()));

    //======================= MOOSHROOMS ===========================================
    // Seta alucinógena
    public static final RegistryObject<Item> MOOSHROOMS = ITEMS.register("mooshrooms",
            () -> new MooshroomsItem(new Item.Properties()
                    .food(new FoodProperties.Builder()
                            .nutrition(2)
                            .saturationMod(0.3f)
                            .alwaysEat()
                            .build())));


    // Item bayas de Ephedra (se planta como arbusto)
    public static final RegistryObject<Item> EPHEDRA_BERRIES = ITEMS.register("ephedra_berries",
            () -> new EphedraBerryItem(new Item.Properties()));


    // Semillas
    public static final RegistryObject<Item> MOOSHROOMS_SEEDS = ITEMS.register("mooshrooms_seeds",
            () -> new ItemNameBlockItem(ModBlocks.MOOSHROOMS_CROP.get(), new Item.Properties()));


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
