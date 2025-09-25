package com.disco190.drugcraft.blocks;

import com.disco190.drugcraft.Drugcraft;
import com.disco190.drugcraft.item.ModItems;
import com.disco190.drugcraft.worldgen.tree.MimosaTreeGrower;
import net.minecraft.world.level.block.*;
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
            () -> new PurpleHazeCropBlock(ModItems.PURPLE_HAZE_SEEDS));

    public static final RegistryObject<Block> BLAZE_KUSH_CROP = BLOCKS.register("blaze_kush_crop",
            () -> new BlazeKushCropBlock(ModItems.BLAZE_KUSH_SEEDS));

    // Registro del bloque
    public static final RegistryObject<Block> WILD_MARIJUANA = BLOCKS.register("wild_marijuana",
            WildMarijuanaBlock::new);

    // Registro del bloque
    public static final RegistryObject<Block> WILD_PURPLE_HAZE = BLOCKS.register("wild_purple_haze",
            WildPurpleHazeBlock::new);

    public static final RegistryObject<Block> BLAZE_KUSH_NATURAL = BLOCKS.register("blaze_kush_natural",
            BlazeKushNaturalBlock::new);

    public static final RegistryObject<Block> WILD_TOBACCO = BLOCKS.register("wild_tobacco",
            WildTobaccoBlock::new);

    // Registro del bloque
    public static final RegistryObject<Block> WILD_MOOSHROOMS = BLOCKS.register("wild_mooshrooms",
            WildMooshroomsBlock::new);

    public static final RegistryObject<Block> TOBACCO_CROP = BLOCKS.register("tobacco_crop",
            () -> new TobaccoCropBlock(ModItems.TOBACCO_SEEDS::get));

    public static final RegistryObject<Block> MOOSHROOMS_CROP = BLOCKS.register("mooshrooms_crop",
            () -> new MooshroomsCropBlock(ModItems.MOOSHROOMS_SEEDS));

    // Planta Ephedra
    public static final RegistryObject<Block> EPHEDRA_BUSH = BLOCKS.register("ephedra_bush",
            () -> new EphedraBushBlock(BlockBehaviour.Properties.copy(Blocks.SWEET_BERRY_BUSH)));

    public static final RegistryObject<Block> PEYOTE_CACTUS = BLOCKS.register("peyote_cactus",
            () -> new PeyoteCactusBlock(BlockBehaviour.Properties.copy(Blocks.CACTUS)));

    public static final RegistryObject<Block> CHEMISTRY_STATION = BLOCKS.register("chemistry_station",
            () -> new ChemistryStationBlock());

    public static final RegistryObject<Block> TRAY =
            BLOCKS.register("tray", TrayBlock::new);

    public static final RegistryObject<Block> TRAY_WITH_LIQUID = BLOCKS.register("tray_with_liquid",
            TrayWithLiquidBlock::new);

    public static final RegistryObject<Block> TRAY_WITH_SOLID = BLOCKS.register("tray_with_solid",
            TrayWithSolidBlock::new);

    // MIMOSA LEAVES
    public static final RegistryObject<Block> MIMOSA_LEAVES = BLOCKS.register("mimosa_leaves",
            () -> new LeavesBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LEAVES)));

    // MIMOSA SAPLING
    public static final RegistryObject<Block> MIMOSA_SAPLING = BLOCKS.register("mimosa_sapling",
            () -> new SaplingBlock(new MimosaTreeGrower(), BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING)));

    // MIMOSA LOG
    public static final RegistryObject<Block> MIMOSA_LOG = BLOCKS.register("mimosa_log",
            () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG)));


    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
