package com.disco190.drugcraft.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.LevelReader;

import java.util.function.Supplier;

public class BlazeKushCropBlock extends CropBlock {

    private final Supplier<Item> seedSupplier;

    public BlazeKushCropBlock(Supplier<Item> seedSupplier) {
        super(BlockBehaviour.Properties.copy(Blocks.WHEAT));
        this.seedSupplier = seedSupplier;
    }

    @Override
    protected Item getBaseSeedId() {
        return seedSupplier.get();
    }

    // Aquí le decimos dónde puede crecer (soul sand y otros bloques del Nether)
    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter world, BlockPos pos) {
        // Ahora se puede plantar en todos los bloques del Nether especificados
        return state.is(Blocks.SOUL_SAND) ||
                state.is(Blocks.NETHERRACK) ||
                state.is(Blocks.CRIMSON_NYLIUM) ||
                state.is(Blocks.WARPED_NYLIUM) ||
                state.is(Blocks.SOUL_SOIL);
    }
}
