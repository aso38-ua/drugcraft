package com.disco190.drugcraft.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;


public class WildMooshroomsBlock extends FlowerBlock {
    public WildMooshroomsBlock() {
        super(() -> MobEffects.MOVEMENT_SPEED, 100,
                Properties
                        .of()
                        .mapColor(MapColor.PLANT)
                        .sound(SoundType.GRASS)
                        .instabreak()
                        .noCollission()
                        .offsetType(OffsetType.XZ)
                        .pushReaction(PushReaction.DESTROY)
        );
    }

    @Override
    public int getEffectDuration() {
        return 100;
    }

    @Override
    public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
        return 100;
    }

    @Override
    public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
        return 60;
    }

}

