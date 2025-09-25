package com.disco190.drugcraft.datagen.provider;

import com.disco190.drugcraft.Drugcraft;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.RegistrySetBuilder;
import net.minecraft.data.PackOutput;
import net.minecraftforge.common.data.DatapackBuiltinEntriesProvider;

import java.util.Set;
import java.util.concurrent.CompletableFuture;

// Importa tus clases de registro de generación de mundo
import com.disco190.drugcraft.worldgen.ModConfiguredFeatures;
import com.disco190.drugcraft.worldgen.ModPlacedFeatures;

public class ModWorldGenProvider extends DatapackBuiltinEntriesProvider {

    public static final RegistrySetBuilder BUILDER = new RegistrySetBuilder()
            .add(net.minecraft.core.registries.Registries.CONFIGURED_FEATURE, ModConfiguredFeatures::bootstrap)
            .add(net.minecraft.core.registries.Registries.PLACED_FEATURE, ModPlacedFeatures::bootstrap);

    public ModWorldGenProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, BUILDER, Set.of(Drugcraft.MODID));
    }
}