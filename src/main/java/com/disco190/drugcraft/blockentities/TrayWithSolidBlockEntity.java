package com.disco190.drugcraft.blockentities;

import com.disco190.drugcraft.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class TrayWithSolidBlockEntity extends BlockEntity {

    public TrayWithSolidBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.TRAY_WITH_SOLID.get(), pos, state);
    }

    // Si quieres añadir futuras funcionalidades, aquí se podría poner tick() u otras variables
    public void tick() {
        // Por ahora no hace nada, solo sirve como identificador del bloque
    }
}
