package com.disco190.drugcraft.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.TallFlowerBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;

public class WildTobaccoBlock extends TallFlowerBlock {
    public WildTobaccoBlock() {
        super(BlockBehaviour.Properties.copy(Blocks.SUNFLOWER) // copia propiedades de planta alta vanilla
                .instabreak()
                .noCollission()
                .offsetType(BlockBehaviour.OffsetType.XZ));
    }

    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter world, BlockPos pos) {
        return state.is(Blocks.GRASS_BLOCK)
                || state.is(Blocks.DIRT)
                || state.is(Blocks.COARSE_DIRT)
                || state.is(Blocks.SAND)
                || state.is(Blocks.FARMLAND);
    }
}
