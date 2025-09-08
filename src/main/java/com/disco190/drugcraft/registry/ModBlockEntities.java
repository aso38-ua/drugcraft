package com.disco190.drugcraft.registry;

import com.disco190.drugcraft.Drugcraft;
import com.disco190.drugcraft.blockentities.ChemistryStationBlockEntity;
import com.disco190.drugcraft.blockentities.TrayBlockEntity;
import com.disco190.drugcraft.blocks.ModBlocks;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(ForgeRegistries.BLOCK_ENTITY_TYPES, Drugcraft.MODID);

    public static final RegistryObject<BlockEntityType<ChemistryStationBlockEntity>> CHEMISTRY_STATION =
            BLOCK_ENTITIES.register("chemistry_station",
                    () -> BlockEntityType.Builder.of(ChemistryStationBlockEntity::new, ModBlocks.CHEMISTRY_STATION.get())
                            .build(null));

    public static final RegistryObject<BlockEntityType<TrayBlockEntity>> TRAY =
            BLOCK_ENTITIES.register("tray",
                    () -> BlockEntityType.Builder.of(TrayBlockEntity::new, ModBlocks.TRAY.get())
                            .build(null));

    public static final RegistryObject<BlockEntityType<TrayBlockEntity>> TRAY_WITH_LIQUID =
            BLOCK_ENTITIES.register("tray_with_liquid",
                    () -> BlockEntityType.Builder.of(TrayBlockEntity::new, ModBlocks.TRAY_WITH_LIQUID.get())
                            .build(null));

    public static final RegistryObject<BlockEntityType<TrayBlockEntity>> TRAY_WITH_SOLID =
            BLOCK_ENTITIES.register("tray_with_solid",
                    () -> BlockEntityType.Builder.of(TrayBlockEntity::new, ModBlocks.TRAY_WITH_LIQUID.get())
                            .build(null));


}
