package com.disco190.drugcraft.worldgen;

import com.disco190.drugcraft.Drugcraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.blockpredicates.BlockPredicate;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.*;
import net.minecraft.world.level.levelgen.Heightmap;

import java.util.List;

public class ModPlacedFeatures {

    public static final ResourceKey<PlacedFeature> MIMOSA_PLACED_KEY = registerKey("mimosa_placed");

    public static void bootstrap(BootstapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        register(context, MIMOSA_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.MIMOSA_KEY),
                List.of(
                        CountPlacement.of(6),
                        InSquarePlacement.spread(),
                        HeightmapPlacement.onHeightmap(Heightmap.Types.MOTION_BLOCKING_NO_LEAVES),
                        BlockPredicateFilter.forPredicate(
                                BlockPredicate.matchesBlocks(new BlockPos(0, -1, 0), Blocks.GRASS_BLOCK, Blocks.DIRT)
                        ),
                        BiomeFilter.biome()
                ));



    }

    private static ResourceKey<PlacedFeature> registerKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(Drugcraft.MODID, name));
    }

    private static void register(BootstapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuredFeature,
                                 List<PlacementModifier> placementModifiers) {
        context.register(key, new PlacedFeature(configuredFeature, placementModifiers));
    }
}