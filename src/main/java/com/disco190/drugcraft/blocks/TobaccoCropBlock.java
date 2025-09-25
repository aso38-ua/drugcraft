package com.disco190.drugcraft.blocks;

import com.disco190.drugcraft.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.VoxelShape;
import net.minecraft.world.phys.shapes.Shapes;


import java.util.function.Supplier;

public class TobaccoCropBlock extends CropBlock {

    public static final IntegerProperty AGE = IntegerProperty.create("age", 0, 7);
    private static final VoxelShape[] SHAPE_BY_AGE = new VoxelShape[]{
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 2.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 4.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 6.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 8.0D, 16.0D), // Age 3, bottom part is complete
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 10.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 12.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 14.0D, 16.0D),
            Block.box(0.0D, 0.0D, 0.0D, 16.0D, 16.0D, 16.0D) // Age 7, plant is full height
    };

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

    // Sobrescribimos el método para controlar el crecimiento natural de la planta
    @Override
    public void randomTick(BlockState state, ServerLevel world, BlockPos pos, RandomSource random) {
        // Encontramos el bloque de la base para iniciar el crecimiento desde abajo
        BlockPos bottomPos = getBottomBlockPos(world, pos);
        BlockState bottomState = world.getBlockState(bottomPos);

        if (random.nextDouble() < 0.1) {
            this.growPlant(world, bottomPos, bottomState, random);
        }
    }

    @Override
    public boolean isBonemealSuccess(Level level, RandomSource random, BlockPos pos, BlockState state) {
        // Siempre devolvemos true si no está en edad máxima.
        return state.getValue(AGE) < getMaxAge();
    }

    @Override
    public void performBonemeal(ServerLevel world, RandomSource random, BlockPos pos, BlockState state) {
        // Encontramos el bloque inferior y aplicamos la lógica de crecimiento desde ahí.
        BlockPos bottomPos = getBottomBlockPos(world, pos);
        BlockState bottomState = world.getBlockState(bottomPos);

        // El bonemeal debe avanzar más rápido que el crecimiento natural
        for (int i = 0; i < 3; i++) {
            this.growPlant(world, bottomPos, bottomState, random);
            bottomState = world.getBlockState(bottomPos);
        }
    }

    private void growPlant(ServerLevel world, BlockPos bottomPos, BlockState bottomState, RandomSource random) {
        int age = bottomState.getValue(AGE);

        // Si es el bloque inferior (edad 0-2)
        if (age < 3) {
            int newAge = age + 1;
            world.setBlock(bottomPos, bottomState.setValue(AGE, newAge), 2);
            if (newAge == 3) {
                placeTopIfEmpty(world, bottomPos);
            }
        }
        // Si es el bloque superior (edad 3-6)
        else if (age >= 3 && age < 7) {
            BlockPos abovePos = bottomPos.above();
            BlockState aboveState = world.getBlockState(abovePos);

            // Si la parte superior existe y es de la misma planta, la hacemos crecer
            if (aboveState.getBlock() == this) {
                int aboveAge = aboveState.getValue(AGE);
                if (aboveAge >= 4 && aboveAge < 7) {
                    int newAge = aboveAge + 1;
                    world.setBlock(abovePos, aboveState.setValue(AGE, newAge), 2);
                }
            } else {
                // Si la parte superior no existe, la colocamos
                placeTopIfEmpty(world, bottomPos);
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

    // Encuentra la posición del bloque inferior de la planta
    private BlockPos getBottomBlockPos(ServerLevel world, BlockPos pos) {
        BlockPos bottomPos = pos;
        while (world.getBlockState(bottomPos.below()).getBlock() == this) {
            bottomPos = bottomPos.below();
        }
        return bottomPos;
    }

    // Método para determinar si el bloque puede sobrevivir en su posición
    @Override
    public boolean canSurvive(BlockState state, LevelReader level, BlockPos pos) {
        BlockState belowState = level.getBlockState(pos.below());
        if (state.getValue(AGE) >= 4) {
            // El bloque superior solo puede sobrevivir si el bloque de abajo es el mismo bloque de tabaco
            return belowState.getBlock() == this;
        } else {
            // El bloque inferior solo puede sobrevivir si el bloque de abajo es farmland
            return belowState.is(Blocks.FARMLAND);
        }
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE_BY_AGE[state.getValue(this.getAgeProperty())];
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
