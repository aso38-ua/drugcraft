package com.disco190.drugcraft.blocks;

import com.disco190.drugcraft.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;

import java.util.function.Supplier;

public class TobaccoCropBlock extends CropBlock {

    public static final IntegerProperty AGE = IntegerProperty.create("age", 0, 7);

    private final Supplier<Item> seedSupplier;

    public TobaccoCropBlock(Supplier<Item> seedSupplier) {
        super(BlockBehaviour.Properties.copy(Blocks.WHEAT));
        this.seedSupplier = seedSupplier;
        this.registerDefaultState(this.stateDefinition.any().setValue(AGE, 0));
    }

    @Override
    protected Item getBaseSeedId() {
        return seedSupplier.get();
    }

    @Override
    public IntegerProperty getAgeProperty() {
        return AGE;
    }

    @Override
    public int getMaxAge() {
        return 7;
    }

    @Override
    public BlockState getStateForAge(int age) {
        return this.defaultBlockState().setValue(AGE, age);
    }

    // Crecimiento natural
    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        int age = state.getValue(AGE);

        // Bloque inferior 0-3
        if (age < 3) {
            super.randomTick(state, world, pos, random);

            // Si alcanza 3, coloca el bloque superior
            if (state.getValue(AGE) == 3) {
                placeTopIfEmpty(world, pos);
            }
        }
        // Edad 3: colocar parte superior si no existe
        else if (age == 3) {
            placeTopIfEmpty(world, pos);
        }
        // Bloque superior 4-7
        else if (age >= 4 && age < 7) {
            if (random.nextInt(5) == 0) {
                world.setBlock(pos, state.setValue(AGE, age + 1), 2);
            }
        }
    }

    private void placeTopIfEmpty(ServerLevel world, BlockPos pos) {
        BlockPos above = pos.above();
        BlockState aboveState = world.getBlockState(above);
        if (aboveState.isAir()) {
            world.setBlock(above, this.defaultBlockState().setValue(AGE, 4), 2);
        }
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
        int age = state.getValue(AGE);
        return age >= 4 && age < 7; // solo bloque superior puede crecer
    }

    @Override
    public void performBonemeal(ServerLevel world, RandomSource random, BlockPos pos, BlockState state) {
        int age = state.getValue(AGE);
        if (age >= 4 && age < 7) {
            int newAge = Math.min(age + 1 + random.nextInt(2), 7);
            world.setBlock(pos, state.setValue(AGE, newAge), 2);
            world.gameEvent(net.minecraft.world.level.gameevent.GameEvent.BLOCK_CHANGE, pos, GameEvent.Context.of(state));
        }
    }


    @Override
    public void playerWillDestroy(Level world, BlockPos pos, BlockState state, net.minecraft.world.entity.player.Player player) {
        // Al romper bloque inferior, destruye bloque superior si existe
        BlockPos above = pos.above();
        BlockState aboveState = world.getBlockState(above);
        if (aboveState.getBlock() == this) {
            world.destroyBlock(above, false);
        }
        super.playerWillDestroy(world, pos, state, player);
    }

    @Override
    public ItemStack getCloneItemStack(BlockGetter level, BlockPos pos, BlockState state) {
        return new ItemStack(ModItems.TOBACCO_SEEDS.get());
    }
}
