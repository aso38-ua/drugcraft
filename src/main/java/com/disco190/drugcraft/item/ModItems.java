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

    public static final RegistryObject<Item> PURPLE_HAZE = ITEMS.register("purple_haze",
            () -> new Item(new Item.Properties()));

    // Semillas
    public static final RegistryObject<Item> MARIJUANA_SEEDS = ITEMS.register("marijuanaseeds",
            () -> new ItemNameBlockItem(ModBlocks.MARIJUANA_CROP.get(), new Item.Properties()));

    public static final RegistryObject<Item> PURPLE_HAZE_SEEDS = ITEMS.register("purple_haze_seeds",
            () -> new ItemNameBlockItem(ModBlocks.PURPLE_HAZE_CROP.get(), new Item.Properties()));

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

    public static final RegistryObject<Item> PURPLE_HAZE_JOINT = ITEMS.register("purple_haze_joint",
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

    public static final RegistryObject<Item> TRIPA = ITEMS.register("tripa",
        () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> CAPOTE = ITEMS.register("capote",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> CAPA = ITEMS.register("capa",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> CURED_TRIPA = ITEMS.register("cured_tripa",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> CURED_CAPOTE = ITEMS.register("cured_capote",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> CURED_CAPA = ITEMS.register("cured_capa",
            () -> new Item(new Item.Properties()));


    public static final RegistryObject<Item> CIGARETTE = ITEMS.register("cigarette",
            () -> new CigaretteItem(new Item.Properties()
                    .food(new FoodProperties.Builder()
                            .nutrition(0)       // no afecta hambre
                            .saturationMod(0f)  // no da saturación
                            .alwaysEat()        // permite usar siempre
                            .build())
            ));

    public static final RegistryObject<Item> MALMORO_CIGARETTE = ITEMS.register("malmoro_cigarette",
            () -> new MalmoroCigaretteItem(new Item.Properties()
                    .food(new FoodProperties.Builder()
                            .nutrition(0)       // no afecta hambre
                            .saturationMod(0f)  // no da saturación
                            .alwaysEat()        // permite usar siempre
                            .build())
            ));

    public static final RegistryObject<Item> MALMORO_GOLD_CIGARETTE = ITEMS.register("malmoro_gold_cigarette",
            () -> new MalmoroGoldCigaretteItem(new Item.Properties()
                    .food(new FoodProperties.Builder()
                            .nutrition(0)       // no afecta hambre
                            .saturationMod(0f)  // no da saturación
                            .alwaysEat()        // permite usar siempre
                            .build())
            ));

    public static final RegistryObject<Item> CAMELLO_CIGARETTE = ITEMS.register("camello_cigarette",
            () -> new CamelloCigaretteItem(new Item.Properties()
                    .food(new FoodProperties.Builder()
                            .nutrition(0)       // no afecta hambre
                            .saturationMod(0f)  // no da saturación
                            .alwaysEat()        // permite usar siempre
                            .build())
            ));

    public static final RegistryObject<Item> LUCKY_STROKE_CIGARETTE = ITEMS.register("lucky_stroke_cigarette",
            () -> new LuckyStrokeCigaretteItem(new Item.Properties()
                    .food(new FoodProperties.Builder()
                            .nutrition(0)       // no afecta hambre
                            .saturationMod(0f)  // no da saturación
                            .alwaysEat()        // permite usar siempre
                            .build())
            ));

    public static final RegistryObject<Item> HORIZONTE_CIGARETTE = ITEMS.register("horizonte_cigarette",
            () -> new HorizonteCigaretteItem(new Item.Properties()
                    .food(new FoodProperties.Builder()
                            .nutrition(2)       // cura el hambre
                            .saturationMod(0f)  // no da saturación
                            .alwaysEat()        // permite usar siempre
                            .build())
            ));



    public static final RegistryObject<Item> CARDBOARD = ITEMS.register("cardboard",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> ROLLING_TOBACCO = ITEMS.register("rolling_tobacco",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> WOOD_PIPE = ITEMS.register("wood_pipe",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> WISDOM_PIPE = ITEMS.register("wisdom_pipe",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> LOADED_WOOD_PIPE = ITEMS.register("loaded_wood_pipe",
            () -> new LoadedWoodPipeItem(new Item.Properties()));

    public static final RegistryObject<Item> LOADED_WISDOM_PIPE = ITEMS.register("loaded_wisdom_pipe",
            () -> new LoadedWisdomPipeItem(new Item.Properties()));


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


    public static final RegistryObject<Item> ESPLENDIDO_CIGAR = ITEMS.register("esplendido_cigar",
            () -> new EsplendidoCigarItem(new Item.Properties()));

    public static final RegistryObject<Item> DON_JAVIER_CIGAR = ITEMS.register("don_javier_cigar",
            () -> new DonJavierCigarItem(new Item.Properties()));

    public static final RegistryObject<Item> TORITO_CIGAR = ITEMS.register("torito_cigar",
            () -> new ToritoCigarItem(new Item.Properties()));

    public static final RegistryObject<Item> ROLLING_CIGARETTE = ITEMS.register("rolling_cigarette",
            () -> new RollingCigaretteItem(new Item.Properties()));


  
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


    //========================== SEMEN ========================================================

    public static final RegistryObject<Item> HORSE_SEMEN = ITEMS.register("horse_semen",
            () -> new HorseSemenItem(new Item.Properties()
                    .food(new FoodProperties.Builder()
                            .nutrition(20)       // cura el hambre
                            .saturationMod(1f)  // no da saturación
                            .alwaysEat()        // permite usar siempre
                            .build())
            ));
}


