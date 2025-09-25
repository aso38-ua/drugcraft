package com.disco190.drugcraft.worldgen;

import com.disco190.drugcraft.Drugcraft;
import com.disco190.drugcraft.blocks.BlazeKushCropBlock;
import com.disco190.drugcraft.blocks.ModBlocks;
import com.disco190.drugcraft.blocks.EphedraBushBlock; // Asegúrate de importar tu clase de bloque
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.SimpleBlockConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.TreeConfiguration;
import net.minecraft.world.level.levelgen.feature.featuresize.TwoLayersFeatureSize;
import net.minecraft.world.level.levelgen.feature.foliageplacers.BlobFoliagePlacer;
import net.minecraft.world.level.levelgen.feature.stateproviders.BlockStateProvider;
import net.minecraft.world.level.levelgen.feature.trunkplacers.StraightTrunkPlacer;
import net.minecraft.util.valueproviders.ConstantInt;

public class ModConfiguredFeatures {

    public static final ResourceKey<ConfiguredFeature<?, ?>> MIMOSA_KEY = registerKey("mimosa");

    public static final ResourceKey<ConfiguredFeature<?, ?>> EPHEDRA_BUSH_KEY = registerKey("ephedra_bush");

    public static final ResourceKey<ConfiguredFeature<?, ?>> BLAZE_KUSH_KEY = registerKey("blaze_kush");

    public static final ResourceKey<ConfiguredFeature<?, ?>> WILD_TOBACCO_KEY =
            registerKey("wild_tobacco");

    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
        register(context, MIMOSA_KEY, Feature.TREE, new TreeConfiguration.TreeConfigurationBuilder(
                BlockStateProvider.simple(ModBlocks.MIMOSA_LOG.get()),
                new StraightTrunkPlacer(5, 4, 3),
                BlockStateProvider.simple(ModBlocks.MIMOSA_LEAVES.get()),
                new BlobFoliagePlacer(ConstantInt.of(3), ConstantInt.of(2), 3),
                new TwoLayersFeatureSize(1, 0, 2)).build()
        );

        context.register(EPHEDRA_BUSH_KEY,
                new ConfiguredFeature<>(Feature.RANDOM_PATCH,
                        FeatureUtils.simpleRandomPatchConfiguration(
                                1, // menos intentos dentro del parche
                                PlacementUtils.onlyWhenEmpty(
                                        Feature.SIMPLE_BLOCK,
                                        new SimpleBlockConfiguration(
                                                BlockStateProvider.simple(
                                                        ModBlocks.EPHEDRA_BUSH.get()
                                                                .defaultBlockState()
                                                                .setValue(EphedraBushBlock.AGE, 3)
                                                )
                                        )
                                )
                        )
                )
        );

        context.register(BLAZE_KUSH_KEY,
                new ConfiguredFeature<>(
                        Feature.RANDOM_PATCH,
                        FeatureUtils.simpleRandomPatchConfiguration(
                                4, // número de intentos como en el JSON
                                PlacementUtils.onlyWhenEmpty(
                                        Feature.SIMPLE_BLOCK,
                                        new SimpleBlockConfiguration(
                                                BlockStateProvider.simple(
                                                        ModBlocks.BLAZE_KUSH_NATURAL.get()
                                                                .defaultBlockState()
                                                )
                                        )
                                )
                        )
                )
        );

        context.register(WILD_TOBACCO_KEY,
                new ConfiguredFeature<>(
                        Feature.RANDOM_PATCH,
                        FeatureUtils.simpleRandomPatchConfiguration(
                                6,
                                PlacementUtils.onlyWhenEmpty(
                                        Feature.SIMPLE_BLOCK,
                                        new SimpleBlockConfiguration(
                                                BlockStateProvider.simple(ModBlocks.WILD_TOBACCO.get().defaultBlockState())
                                        )
                                )
                        )
                )
        );




    }

    private static ResourceKey<ConfiguredFeature<?, ?>> registerKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, new ResourceLocation(Drugcraft.MODID, name));
    }

    private static <FC extends net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration, F extends Feature<FC>> void register(BootstapContext<ConfiguredFeature<?, ?>> context,
                                                                                                                                                    ResourceKey<ConfiguredFeature<?, ?>> key, F feature, FC configuration){
        context.register(key, new ConfiguredFeature<>(feature, configuration));
    }
}
