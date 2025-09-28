package com.disco190.drugcraft.blocks;

import com.disco190.drugcraft.item.ModItems;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.core.Direction;
import net.minecraft.core.BlockPos;


public class WildMarijuanaBlock extends FlowerBlock {
    public WildMarijuanaBlock() {
        super(() -> MobEffects.MOVEMENT_SPEED, 100,
                BlockBehaviour.Properties
                        .of()
                        .mapColor(MapColor.PLANT)
                        .sound(SoundType.GRASS)
                        .instabreak()
                        .noCollission()
                        .offsetType(BlockBehaviour.OffsetType.XZ)
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

    @Override
    public ItemStack getCloneItemStack(BlockGetter world, BlockPos pos, BlockState state) {
        // Devuelve la semilla correspondiente
        return new ItemStack(ModItems.MARIJUANA_SEEDS.get());
    }

}

