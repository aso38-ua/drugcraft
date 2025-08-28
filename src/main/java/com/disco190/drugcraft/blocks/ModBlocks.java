package com.disco190.drugcraft.blocks;

import com.disco190.drugcraft.Drugcraft;
import com.disco190.drugcraft.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlocks {

    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, Drugcraft.MODID);

    // Aquí registrarás el cultivo, con null por ahora para compilar
    public static final RegistryObject<Block> MARIJUANA_CROP = BLOCKS.register("marijuana_crop",
            () -> new MarijuanaCropBlock(ModItems.MARIJUANA_SEEDS));

    public static final RegistryObject<Block> PURPLE_HAZE_CROP = BLOCKS.register("purple_haze_crop",
            () -> new MarijuanaCropBlock(ModItems.PURPLE_HAZE_SEEDS));

    // Registro del bloque
    public static final RegistryObject<Block> WILD_MARIJUANA = BLOCKS.register("wild_marijuana",
            WildMarijuanaBlock::new);

    public static final RegistryObject<Block> TOBACCO_CROP = BLOCKS.register("tobacco_crop",
            () -> new TobaccoCropBlock(ModItems.TOBACCO_SEEDS));

    public static final RegistryObject<Block> MOOSHROOMS_CROP = BLOCKS.register("mooshrooms_crop",
            () -> new MooshroomsCropBlock(ModItems.MOOSHROOMS_SEEDS));

    // Planta Ephedra
    public static final RegistryObject<Block> EPHEDRA_BUSH = BLOCKS.register("ephedra_bush",
            () -> new EphedraBushBlock(BlockBehaviour.Properties.copy(Blocks.SWEET_BERRY_BUSH)));



    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
