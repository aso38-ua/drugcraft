package com.disco190.drugcraft.item;

import com.disco190.drugcraft.Drugcraft;
import com.disco190.drugcraft.blocks.ModBlocks;
import com.disco190.drugcraft.items.CannabisJointItem;
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



    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
