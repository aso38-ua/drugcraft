package com.disco190.drugcraft.blocks;

import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CactusBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.LevelReader;

public class PeyoteCactusBlock extends CactusBlock {

    public PeyoteCactusBlock(Properties properties) {
        super(properties);
    }

    // Dar efecto al tocarlo
    @Override
    public void entityInside(BlockState state, Level level, BlockPos pos, Entity entity) {
        if (!level.isClientSide && entity instanceof LivingEntity living) {
            living.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 100, 0));
        }
        super.entityInside(state, level, pos, entity);
    }

    // Mantener el comportamiento de cactus vanilla: se puede colocar sobre arena o sobre otro peyote
    @Override
    public boolean canSurvive(BlockState state, LevelReader world, BlockPos pos) {
        BlockState below = world.getBlockState(pos.below());
        return below.is(Blocks.SAND) || below.is(Blocks.RED_SAND) || below.is(this);
    }

    @Override
    public void playerWillDestroy(Level world, BlockPos pos, BlockState state, Player player) {
        super.playerWillDestroy(world, pos, state, player);

        BlockPos abovePos = pos.above();
        while (world.getBlockState(abovePos).is(this)) {
            BlockState aboveState = world.getBlockState(abovePos);
            world.destroyBlock(abovePos, true); // true hace que suelte el item
            abovePos = abovePos.above();
        }
    }


}
