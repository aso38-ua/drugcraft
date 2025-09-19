package com.disco190.drugcraft.worldgen;

import com.disco190.drugcraft.Drugcraft;
import net.minecraft.core.Holder;
import net.minecraft.core.HolderGetter;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import net.minecraft.world.level.levelgen.placement.PlacedFeature;
import net.minecraft.world.level.levelgen.placement.PlacementModifier;

import java.util.List;

public class ModPlacedFeatures {

    public static final ResourceKey<PlacedFeature> MIMOSA_PLACED_KEY = registerKey("mimosa_placed");

    public static void bootstrap(BootstapContext<PlacedFeature> context) {
        HolderGetter<ConfiguredFeature<?, ?>> configuredFeatures = context.lookup(Registries.CONFIGURED_FEATURE);

        register(context, MIMOSA_PLACED_KEY, configuredFeatures.getOrThrow(ModConfiguredFeatures.MIMOSA_KEY),
                List.of()); // Puedes añadir modificadores de colocación aquí si quieres, como altura, etc.
    }

    private static ResourceKey<PlacedFeature> registerKey(String name) {
        return ResourceKey.create(Registries.PLACED_FEATURE, new ResourceLocation(Drugcraft.MODID, name));
    }

    private static void register(BootstapContext<PlacedFeature> context, ResourceKey<PlacedFeature> key, Holder<ConfiguredFeature<?, ?>> configuredFeature,
                                 List<PlacementModifier> placementModifiers) {
        context.register(key, new PlacedFeature(configuredFeature, placementModifiers));
    }
}