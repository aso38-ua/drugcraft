package com.disco190.drugcraft.client;

import com.disco190.drugcraft.blocks.ModBlocks;
import com.disco190.drugcraft.registry.ModBlockEntities;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.EntityRenderersEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class ModRenderLayers {

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            // Aquí registras los bloques que deben renderizarse como cutout
            ItemBlockRenderTypes.setRenderLayer(ModBlocks.PEYOTE_CACTUS.get(), RenderType.cutout());
        });
    }

}
