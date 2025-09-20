package com.disco190.drugcraft.worldgen;

import com.disco190.drugcraft.Drugcraft;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraftforge.common.world.BiomeModifier;
import net.minecraft.core.Registry;
import net.minecraftforge.registries.ForgeRegistries; // <-- Añadida esta importación

public class ModBiomeModifiers {
    public static final ResourceKey<BiomeModifier> MIMOSA_MODIFIER_KEY =
            ResourceKey.create(ForgeRegistries.Keys.BIOME_MODIFIERS, new ResourceLocation(Drugcraft.MODID, "mimosa_modifier")); // <-- Corrección
}