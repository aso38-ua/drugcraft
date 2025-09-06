package com.disco190.drugcraft.item;

import com.disco190.drugcraft.Drugcraft;
import com.disco190.drugcraft.blocks.ModBlocks;
import com.disco190.drugcraft.effects.ModEffects;
import com.disco190.drugcraft.items.CannabisJointItem;
import com.disco190.drugcraft.items.CigaretteItem;
import com.disco190.drugcraft.items.MooshroomsItem;
import com.disco190.drugcraft.items.PackOfCigaretteItem;
import com.disco190.drugcraft.items.*;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTabs;
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

    // Semillas
    public static final RegistryObject<Item> MARIJUANA_SEEDS = ITEMS.register("marijuanaseeds",
            () -> new ItemNameBlockItem(ModBlocks.MARIJUANA_CROP.get(), new Item.Properties()));

    public static final RegistryObject<Item> PURPLE_HAZE_SEEDS = ITEMS.register("purple_haze_seeds",
            () -> new ItemNameBlockItem(ModBlocks.PURPLE_HAZE_CROP.get(), new Item.Properties()));

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

    public static final RegistryObject<Item> PACK_OF_CIGARETTES = ITEMS.register("pack_of_cigarettes",
            () -> new PackOfCigaretteItem(new Item.Properties()));

    public static final RegistryObject<Item> CARDBOARD = ITEMS.register("cardboard",
            () -> new Item(new Item.Properties()));
  
    //======================= LEAN ===========================================

    // Item bayas de Ephedra (se planta como arbusto)
    public static final RegistryObject<Item> EPHEDRA_BERRIES = ITEMS.register("ephedra_berries",
            () -> new EphedraBerryItem(new Item.Properties()));

    public static final RegistryObject<Item> EPHEDRINE = ITEMS.register("ephedrine",
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


    public static final RegistryObject<Item> CHEMISTRY_STATION_ITEM = ITEMS.register("chemistry_station",
            () -> new BlockItem(ModBlocks.CHEMISTRY_STATION.get(), new Item.Properties()));




    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
