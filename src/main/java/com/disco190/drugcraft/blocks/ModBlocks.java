package com.disco190.drugcraft.blocks;

import com.disco190.drugcraft.Drugcraft;
import com.disco190.drugcraft.item.ModItems;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, Drugcraft.MODID);

    // Aquí registrarás el cultivo, con null por ahora para compilar
    public static final RegistryObject<Block> MARIJUANA_CROP = BLOCKS.register("marijuana_crop",
            () -> new MarijuanaCropBlock(() -> ModItems.MARIJUANA_SEEDS.get()));



    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
