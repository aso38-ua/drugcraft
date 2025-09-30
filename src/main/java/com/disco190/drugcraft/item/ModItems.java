package com.disco190.drugcraft.item;

import com.disco190.drugcraft.Drugcraft;
import com.disco190.drugcraft.blocks.ModBlocks;
import com.disco190.drugcraft.effects.ModEffects;
import com.disco190.drugcraft.items.CigaretteItem;
import com.disco190.drugcraft.items.MooshroomsItem;
import com.disco190.drugcraft.items.PackOfCigaretteItem;
import com.disco190.drugcraft.items.*;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
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
            () -> new ModularBudItem(new Item.Properties()));

    public static final RegistryObject<Item> PURPLE_HAZE = ITEMS.register("purple_haze",
            () -> new ModularBudItem(new Item.Properties()));

    public static final RegistryObject<Item> BLAZE_KUSH = ITEMS.register("blaze_kush",
            () -> new ModularBudItem(new Item.Properties()));

    public static final RegistryObject<Item> FUJIYAMA = ITEMS.register("fujiyama",
            () -> new ModularBudItem(new Item.Properties()));

    // Semillas
    public static final RegistryObject<Item> MARIJUANA_SEEDS = ITEMS.register("marijuanaseeds",
            () -> new ItemNameBlockItem(ModBlocks.MARIJUANA_CROP.get(), new Item.Properties()));

    public static final RegistryObject<Item> PURPLE_HAZE_SEEDS = ITEMS.register("purple_haze_seeds",
            () -> new ItemNameBlockItem(ModBlocks.PURPLE_HAZE_CROP.get(), new Item.Properties()));

    public static final RegistryObject<Item> BLAZE_KUSH_SEEDS = ITEMS.register("blaze_kush_seeds",
            () -> new ItemNameBlockItem(ModBlocks.BLAZE_KUSH_CROP.get(), new Item.Properties()));

    // Porro
    public static final RegistryObject<Item> CANNABIS_JOINT = ITEMS.register("cannabis_joint",
            () -> new ModularJointItem(new Item.Properties()
                     // 3 caladas
                    .food(new FoodProperties.Builder()
                            .nutrition(0)       // no afecta hambre
                            .saturationMod(0f)  // no da saturación
                            .alwaysEat()        // permite usar siempre
                            .build())
            ));

    public static final RegistryObject<Item> PURPLE_HAZE_JOINT = ITEMS.register("purple_haze_joint",
            () -> new ModularJointItem(new Item.Properties()
                    .stacksTo(64) // tamaño del stack
                    .food(new FoodProperties.Builder()
                            .nutrition(0)       // no afecta hambre
                            .saturationMod(0f)  // no da saturación
                            .alwaysEat()        // permite usar siempre
                            .build())
            ));

    public static final RegistryObject<Item> BLAZE_KUSH_JOINT = ITEMS.register("blaze_kush_joint",
            () -> new ModularJointItem(new Item.Properties()
                    // 3 caladas
                    .food(new FoodProperties.Builder()
                            .nutrition(0)       // no afecta hambre
                            .saturationMod(0f)  // no da saturación
                            .alwaysEat()        // permite usar siempre
                            .build())
            ));

    public static final RegistryObject<Item> FUJIYAMA_JOINT = ITEMS.register("fujiyama_joint",
            () -> new ModularJointItem(new Item.Properties()
                    .stacksTo(64) // tamaño del stack
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

    public static final RegistryObject<Item> EPHEDRINE = ITEMS.register("ephedrine",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> METHYLAMINE = ITEMS.register("methylamine",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> PURE_EPHEDRINE = ITEMS.register("pure_ephedrine",
            () -> new Item(new Item.Properties()));

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
            () -> new RefreshmentItem(new Item.Properties()));


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


    //======================== AYAHUASCA ===========================================
    // Ayahuasca
    public static final RegistryObject<Item> AYAHUASCA = ITEMS.register("ayahuasca",
            () -> new AyahuascaItem(new Item.Properties()
                    .stacksTo(16) // no como sopa sospechosa (solo 1), que puedas acumular varias
                    .food(new FoodProperties.Builder()
                            .nutrition(6)       // llena más que los hongos (sopa)
                            .saturationMod(0.8f)
                            .alwaysEat()
                            .build())));

    //======================== PEYOTE ===========================================
    public static final RegistryObject<Item> PEYOTE_CACTUS_ITEM = ModItems.ITEMS.register("peyote_cactus",
            () -> new BlockItem(ModBlocks.PEYOTE_CACTUS.get(), new Item.Properties()));

    public static final RegistryObject<Item> PEYOTE = ITEMS.register("peyote",
            () -> new Item(new Item.Properties()
                    .food(new FoodProperties.Builder()
                            .nutrition(1) // quita un poco de hambre
                            .saturationMod(0.2f)
                            .alwaysEat() // se puede comer aunque tengas la barra llena
                            .effect(() -> new MobEffectInstance(ModEffects.HALLUCINATION.get(), 20 * 20, 0), 1.0f)
                            .effect(() -> new MobEffectInstance(MobEffects.CONFUSION, 20 * 30, 0), 1.0f) // nausea 30s
                            .effect(() -> new MobEffectInstance(MobEffects.NIGHT_VISION, 20 * 40, 0), 1.0f) // night vision 1m
                            .effect(() -> new MobEffectInstance(MobEffects.GLOWING, 20 * 30, 0), 1.0f)
                            .build())
            ));

    //======================== META ===========================================
    public static final RegistryObject<Item> ACID = ITEMS.register("acid",
            () -> new AcidItem(new Item.Properties()));

    public static final RegistryObject<Item> PHOSPHOR = ITEMS.register("phosphor",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> PSEUDO = ITEMS.register("pseudo",
            () -> new PseudoItem(new Item.Properties()
                    .food(new FoodProperties.Builder()
                            .nutrition(2)        // puntos de comida que da
                            .saturationMod(0.1f) // saturación baja
                            .alwaysEat()         // permite comerlo aunque la barra esté llena
                            .build())
                    .stacksTo(64)            // cantidad máxima en stack
            ));

    public static final RegistryObject<Item> LIQUID_METH = ITEMS.register("liquid_meth",
            () -> new LiquidMethItem(new Item.Properties()));

    public static final RegistryObject<Item> METH = ITEMS.register("meth",
            () -> new MethItem(new Item.Properties()));

    public static final RegistryObject<Item> BLUE_LIQUID_METH = ITEMS.register("blue_liquid_meth",
            () -> new LiquidMethItem(new Item.Properties()));

    public static final RegistryObject<Item> BLUE_METH = ITEMS.register("blue_meth",
            () -> new MethItem(new Item.Properties()));


    public static final RegistryObject<Item> CHEMISTRY_STATION_ITEM = ITEMS.register("chemistry_station",
            () -> new BlockItem(ModBlocks.CHEMISTRY_STATION.get(), new Item.Properties()));

    public static final RegistryObject<Item> TRAY_ITEM = ITEMS.register("tray",
            () -> new BlockItem(ModBlocks.TRAY.get(), new Item.Properties()));





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


    //=========================== LIGHTERS ========================================================
    public static final RegistryObject<Item> IRON_LIGHTER = ITEMS.register("iron_lighter",
            () -> new IronLighterItem(new Item.Properties()));

    public static final RegistryObject<Item> GOLD_LIGHTER = ITEMS.register("gold_lighter",
            () -> new GoldLighterItem(new Item.Properties()));

    public static final RegistryObject<Item> DIAMOND_LIGHTER = ITEMS.register("diamond_lighter",
            () -> new DiamondLighterItem(new Item.Properties()));

    public static final RegistryObject<Item> MIMOSA_LEAVES_ITEM = ITEMS.register("mimosa_leaves",
            () -> new BlockItem(ModBlocks.MIMOSA_LEAVES.get(), new Item.Properties()));

    public static final RegistryObject<Item> MIMOSA_SAPLING_ITEM = ITEMS.register("mimosa_sapling",
            () -> new BlockItem(ModBlocks.MIMOSA_SAPLING.get(), new Item.Properties()));

    public static final RegistryObject<Item> MIMOSA_LOG_ITEM = ITEMS.register("mimosa_log",
            () -> new BlockItem(ModBlocks.MIMOSA_LOG.get(), new Item.Properties()));

    public static final RegistryObject<Item> MIMOSA_BARK = ITEMS.register("mimosa_bark",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> LIQUID_DMT = ITEMS.register("liquid_dmt",
            () -> new Item(new Item.Properties().stacksTo(16)));

    public static final RegistryObject<Item> DMT = ITEMS.register("dmt",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> DRUG_PIPE = ITEMS.register("drug_pipe",
            () -> new DrugPipeItem(new Item.Properties().stacksTo(1).durability(64)));

    public static final RegistryObject<Item> OPIUM_LATEX = ITEMS.register("opium_latex",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> OPIUM = ITEMS.register("opium",
            () -> new OpiumItem(new Item.Properties()));

    public static final RegistryObject<Item> MORPHINE = ITEMS.register("morphine",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> SYRINGE = ITEMS.register("syringe",
            () -> new Item(new Item.Properties()));

    public static final RegistryObject<Item> SYRINGE_WITH_MORPHINE = ITEMS.register("syringe_with_morphine",
            () -> new SyringeWithMorphineItem(new Item.Properties()));


}


