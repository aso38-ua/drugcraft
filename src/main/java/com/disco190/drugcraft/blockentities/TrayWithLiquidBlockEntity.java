package com.disco190.drugcraft.blockentities;

import com.disco190.drugcraft.blocks.ModBlocks;
import com.disco190.drugcraft.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;

public class TrayWithLiquidBlockEntity extends BlockEntity {

    private int progress = 0;
    private static final int MAX_PROGRESS = 200; // 10 segundos a 20 ticks/segundo

    public TrayWithLiquidBlockEntity(BlockPos pos, net.minecraft.world.level.block.state.BlockState state) {
        super(ModBlockEntities.TRAY_WITH_LIQUID.get(), pos, state);
    }

    public void tick() {
        if (level == null || level.isClientSide) return;

        if (progress < MAX_PROGRESS) {
            progress++;
            setChanged();
        } else {
            // Cuando llega al máximo, transforma el bloque automáticamente
            level.setBlock(worldPosition, ModBlocks.TRAY_WITH_SOLID.get().defaultBlockState(), 3);
        }
    }

    public boolean isReady() {
        return progress >= MAX_PROGRESS;
    }

    public void reset() {
        progress = 0;
        setChanged();
    }
}
