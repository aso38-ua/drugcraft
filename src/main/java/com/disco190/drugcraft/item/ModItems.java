package com.disco190.drugcraft.item;

import com.disco190.drugcraft.Drugcraft;
import com.disco190.drugcraft.blocks.ModBlocks;
import com.disco190.drugcraft.items.CannabisJointItem;
import com.disco190.drugcraft.items.CigaretteItem;
import com.disco190.drugcraft.items.MooshroomsItem;
import com.disco190.drugcraft.items.PackOfCigaretteItem;
import com.disco190.drugcraft.items.*;
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

    public static final RegistryObject<Item> CIGARETTE = ITEMS.register("cigarette",
            () -> new CigaretteItem(new Item.Properties()
                    // 3 caladas
                    .food(new FoodProperties.Builder()
                            .nutrition(0)       // no afecta hambre
                            .saturationMod(0f)  // no da saturación
                            .alwaysEat()        // permite usar siempre
                            .build())
            ));

    public static final RegistryObject<Item> MALMORO_CIGARETTE = ITEMS.register("malmoro_cigarette",
            () -> new MalmoroCigaretteItem(new Item.Properties()
                    // 3 caladas
                    .food(new FoodProperties.Builder()
                            .nutrition(0)       // no afecta hambre
                            .saturationMod(0f)  // no da saturación
                            .alwaysEat()        // permite usar siempre
                            .build())
            ));

    public static final RegistryObject<Item> MALMORO_GOLD_CIGARETTE = ITEMS.register("malmoro_gold_cigarette",
            () -> new MalmoroGoldCigaretteItem(new Item.Properties()
                    // 3 caladas
                    .food(new FoodProperties.Builder()
                            .nutrition(0)       // no afecta hambre
                            .saturationMod(0f)  // no da saturación
                            .alwaysEat()        // permite usar siempre
                            .build())
            ));

    public static final RegistryObject<Item> CAMELLO_CIGARETTE = ITEMS.register("camello_cigarette",
            () -> new CamelloCigaretteItem(new Item.Properties()
                    // 3 caladas
                    .food(new FoodProperties.Builder()
                            .nutrition(0)       // no afecta hambre
                            .saturationMod(0f)  // no da saturación
                            .alwaysEat()        // permite usar siempre
                            .build())
            ));

    public static final RegistryObject<Item> LUCKY_STROKE_CIGARETTE = ITEMS.register("lucky_stroke_cigarette",
            () -> new LuckyStrokeCigaretteItem(new Item.Properties()
                    // 3 caladas
                    .food(new FoodProperties.Builder()
                            .nutrition(0)       // no afecta hambre
                            .saturationMod(0f)  // no da saturación
                            .alwaysEat()        // permite usar siempre
                            .build())
            ));

    public static final RegistryObject<Item> HORIZONTE_CIGARETTE = ITEMS.register("horizonte_cigarette",
            () -> new HorizonteCigaretteItem(new Item.Properties()
                    // 3 caladas
                    .food(new FoodProperties.Builder()
                            .nutrition(2)       // cura el hambre
                            .saturationMod(0f)  // no da saturación
                            .alwaysEat()        // permite usar siempre
                            .build())
            ));

    public static final RegistryObject<Item> CARDBOARD = ITEMS.register("cardboard",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> PACK_OF_CIGARETTES = ITEMS.register("pack_of_cigarettes",
            () -> new PackOfCigaretteItem(new Item.Properties()));

    public static final RegistryObject<Item> PACK_OF_MALMORO = ITEMS.register("pack_of_malmoro",
            () -> new PackOfMalmoroItem(new Item.Properties()));

    public static final RegistryObject<Item> PACK_OF_MALMORO_GOLD = ITEMS.register("pack_of_malmoro_gold",
            () -> new PackOfMalmoroGoldItem(new Item.Properties()));

    public static final RegistryObject<Item> PACK_OF_CAMELLO = ITEMS.register("pack_of_camello",
            () -> new PackOfCamelloItem(new Item.Properties()));

    public static final RegistryObject<Item> PACK_OF_LUCKY_STROKE = ITEMS.register("pack_of_lucky_stroke",
            () -> new PackOfLuckyStrokeItem(new Item.Properties()));

    public static final RegistryObject<Item> PACK_OF_HORIZONTE = ITEMS.register("pack_of_horizonte",
            () -> new PackOfHorizonteItem(new Item.Properties()));


  
    //======================= LEAN ===========================================

    // Item bayas de Ephedra (se planta como arbusto)
    public static final RegistryObject<Item> EPHEDRA_BERRIES = ITEMS.register("ephedra_berries",
            () -> new EphedraBerryItem(new Item.Properties()));

    public static final RegistryObject<Item> COUGH_SYRUP = ITEMS.register("cough_syrup",
            () -> new CoughSyrupItem(new Item.Properties()
                    .stacksTo(16)
                    .food(new FoodProperties.Builder()
                            .nutrition(1)
                            .saturationMod(0.1F)
                            .alwaysEat()
                            .effect(() -> new MobEffectInstance(MobEffects.HEAL, 1, 0), 1.0F)
                            .effect(() -> new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 20 * 10, 0), 1.0F)
                            .effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 20 * 5, 0), 0.5F)
                            .build())
            ));



    public static final RegistryObject<Item> CANDY = ITEMS.register("candy",
            () -> new Item(new Item.Properties()
                    .food(new FoodProperties.Builder()
                            .nutrition(2) // medio muslito
                            .saturationMod(0.3F) // poca saturación
                            .effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 100, 2), 1.0F) // 5s Velocidad I
                            .build())
            ));

    public static final RegistryObject<Item> REFRESHMENT = ITEMS.register("refreshment",
            () -> new Item(new Item.Properties()
                    .food(new FoodProperties.Builder()
                            .nutrition(3) // poca comida
                            .saturationMod(0.4f) // no da tanta saturación
                            .alwaysEat()
                            .effect(() -> new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 200, 0), 1.0f) // 10s de speed I
                            .effect(() -> new MobEffectInstance(MobEffects.SATURATION, 20, 0), 1.0f) // 1s de saturación leve
                            .build()
                    )
            ));

    public static final RegistryObject<Item> LEAN = ITEMS.register("lean",
            () -> new LeanItem(new Item.Properties().stacksTo(16)));




    //======================= MOOSHROOMS ===========================================
    // Seta alucinógena
    public static final RegistryObject<Item> MOOSHROOMS = ITEMS.register("mooshrooms",
            () -> new MooshroomsItem(new Item.Properties()
                    .food(new FoodProperties.Builder()
                            .nutrition(2)
                            .saturationMod(0.3f)
                            .alwaysEat()
                            .build())));


    // Semillas
    public static final RegistryObject<Item> MOOSHROOMS_SEEDS = ITEMS.register("mooshrooms_seeds",
            () -> new ItemNameBlockItem(ModBlocks.MOOSHROOMS_CROP.get(), new Item.Properties()));


    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
