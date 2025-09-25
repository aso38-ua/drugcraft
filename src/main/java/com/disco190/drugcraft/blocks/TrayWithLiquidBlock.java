package com.disco190.drugcraft.blocks;

import com.disco190.drugcraft.blockentities.TrayWithLiquidBlockEntity;
import com.disco190.drugcraft.registry.ModBlockEntities;
import com.disco190.drugcraft.util.DrugType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition;
import net.minecraft.world.level.block.state.properties.EnumProperty;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class TrayWithLiquidBlock extends BaseEntityBlock {

    public static final EnumProperty<DrugType> DRUG_TYPE = EnumProperty.create("drug_type", DrugType.class);

    private static final VoxelShape SHAPE = Shapes.or(
            Block.box(1, 1, 3, 15, 2, 13),
            Block.box(1, 2, 3, 15, 3, 4),
            Block.box(1, 2, 12, 15, 3, 13),
            Block.box(1, 2, 3, 2, 3, 13),
            Block.box(14, 2, 3, 15, 3, 13)
    );

    public TrayWithLiquidBlock() {
        super(Properties.of().strength(1.0f).sound(SoundType.WOOD).noOcclusion());
        this.registerDefaultState(this.stateDefinition.any().setValue(DRUG_TYPE, DrugType.METH));
    }

    @Override
    protected void createBlockStateDefinition(StateDefinition.Builder<Block, BlockState> builder) {
        builder.add(DRUG_TYPE);
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new TrayWithLiquidBlockEntity(pos, state);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return type == ModBlockEntities.TRAY_WITH_LIQUID.get()
                ? (lvl, pos, st, be) -> ((TrayWithLiquidBlockEntity) be).tick()
                : null;
    }

    @Override
    public VoxelShape getShape(BlockState state, net.minecraft.world.level.BlockGetter level, BlockPos pos,
                               net.minecraft.world.phys.shapes.CollisionContext context) {
        return SHAPE;
    }
}
