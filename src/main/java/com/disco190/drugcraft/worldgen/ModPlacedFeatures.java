package com.disco190.drugcraft.worldgen;

import com.disco190.drugcraft.Drugcraft;
import com.disco190.drugcraft.blocks.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.placement.PlacementUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.VerticalAnchor;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraft.world.level.levelgen.Heightmap;

import java.util.List;

public class ModPlacedFeatures {

    public static final ResourceKey<PlacedFeature> MIMOSA_PLACED_KEY = registerKey("mimosa_placed");
    public static final ResourceKey<PlacedFeature> EPHEDRA_BUSH_PLACED_KEY = registerKey("ephedra_bush_placed");
    public static final ResourceKey<PlacedFeature> BLAZE_KUSH_PLACED_KEY = registerKey("blaze_kush_placed");
    public static final ResourceKey<PlacedFeature> WILD_TOBACCO_PLACED_KEY =
            registerKey("wild_tobacco_placed");

    public static void bootstrap(BootstapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        register(context, MIMOSA_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.MIMOSA_KEY),
                List.of(
                        CountPlacement.of(2),
                        RarityFilter.onAverageOnceEvery(6),
                        InSquarePlacement.spread(),
                        HeightmapPlacement.onHeightmap(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES),
                        BlockPredicateFilter.forPredicate(
                                BlockPredicate.matchesBlocks(new BlockPos(0, -1, 0), Blocks.GRASS_BLOCK, Blocks.DIRT)
                        ),
                        BiomeFilter.biome()
                ));

        register(context, EPHEDRA_BUSH_PLACED_KEY,
                configuredFeatures.getOrThrow(ModConfiguredFeatures.EPHEDRA_BUSH_KEY),
                List.of(
                        CountPlacement.of(1),                    // 1 parche por chunk
                        RarityFilter.onAverageOnceEvery(6),      // solo en 1 de cada 4 chunks
                        InSquarePlacement.spread(),
                        HeightmapPlacement.onHeightmap(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES),
                        BlockPredicateFilter.forPredicate(
                                BlockPredicate.matchesBlocks(new BlockPos(0, -1, 0),
                                        Blocks.SAND,
                                        Blocks.RED_SAND,
                                        Blocks.GRASS_BLOCK,
                                        Blocks.DIRT,
                                        Blocks.COARSE_DIRT
                                )
                        ),
                        BiomeFilter.biome()
                )
        );

        register(context, BLAZE_KUSH_PLACED_KEY,
                configuredFeatures.getOrThrow(ModConfiguredFeatures.BLAZE_KUSH_KEY),
                List.of(
                        RarityFilter.onAverageOnceEvery(4), // más raro
                        CountPlacement.of(2),               // hasta 3 por chunk
                        InSquarePlacement.spread(),
                        HeightRangePlacement.uniform(
                                VerticalAnchor.aboveBottom(32),
                                VerticalAnchor.aboveBottom(128)
                        ),
                        BiomeFilter.biome()
                )

        );

        register(context, WILD_TOBACCO_PLACED_KEY,
                configuredFeatures.getOrThrow(ModConfiguredFeatures.WILD_TOBACCO_KEY),
                List.of(
                        RarityFilter.onAverageOnceEvery(4),
                        CountPlacement.of(5), // densidad
                        InSquarePlacement.spread(),
                        HeightmapPlacement.onHeightmap(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES),
                        BiomeFilter.biome()
                )
        );







    }

    private static ResourceKey<PlacedFeature> registerKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(Drugcraft.MODID, name));
    }

    private static void register(BootstapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuredFeature,
                                 List<PlacementModifier> placementModifiers) {
        context.register(key, new PlacedFeature(configuredFeature, placementModifiers));
    }
}