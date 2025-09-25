package com.disco190.drugcraft.datagen;

import com.disco190.drugcraft.Drugcraft;
import com.disco190.drugcraft.datagen.provider.ModWorldGenProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Drugcraft.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {

    @SubscribeEvent
    public static void gatherData(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        generator.addProvider(true, new ModWorldGenProvider(generator.getPackOutput(), event.getLookupProvider()));
    }
}