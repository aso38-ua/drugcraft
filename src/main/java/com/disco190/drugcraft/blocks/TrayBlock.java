package com.disco190.drugcraft.blocks;

import com.disco190.drugcraft.blockentities.TrayBlockEntity;
import com.disco190.drugcraft.blockentities.TrayWithLiquidBlockEntity;
import com.disco190.drugcraft.item.ModItems;
import com.disco190.drugcraft.registry.ModBlockEntities;
import com.disco190.drugcraft.util.DrugType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.BaseEntityBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class TrayBlock extends BaseEntityBlock {

    private static final VoxelShape SHAPE = Shapes.or(
            Block.box(1, 0, 3, 15, 1, 13), // base
            Block.box(1, 1, 3, 15, 2, 4),   // norte
            Block.box(1, 1, 12, 15, 2, 13), // sur
            Block.box(1, 1, 3, 2, 2, 13),   // oeste
            Block.box(14, 1, 3, 15, 2, 13)  // este
    );

    public TrayBlock() {
        super(BlockBehaviour.Properties.of()
                .strength(1.0f)
                .sound(SoundType.WOOD));
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new TrayBlockEntity(pos, state);
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos,
                                 Player player, InteractionHand hand, BlockHitResult hit) {

        if(level.isClientSide) return InteractionResult.SUCCESS;

        BlockEntity be = level.getBlockEntity(pos);
        if(!(be instanceof TrayBlockEntity tray)) return InteractionResult.PASS;

        ItemStack held = player.getItemInHand(hand);

        // Si mete líquido válido (meth o dmt)
        if (held.getItem() == ModItems.LIQUID_METH.get() && tray.isEmpty()) {
            level.setBlock(pos, ModBlocks.TRAY_WITH_LIQUID.get()
                    .defaultBlockState().setValue(TrayWithLiquidBlock.DRUG_TYPE, DrugType.METH), 3);

            BlockEntity liquidBE = level.getBlockEntity(pos);
            if (liquidBE instanceof TrayWithLiquidBlockEntity entity) {
                entity.setStoredLiquid(new ItemStack(ModItems.LIQUID_METH.get()));
            }

            held.shrink(1);
            return InteractionResult.SUCCESS;
        }

        if (held.getItem() == ModItems.LIQUID_DMT.get() && tray.isEmpty()) {
            level.setBlock(pos, ModBlocks.TRAY_WITH_LIQUID.get()
                    .defaultBlockState().setValue(TrayWithLiquidBlock.DRUG_TYPE, DrugType.DMT), 3);

            BlockEntity liquidBE = level.getBlockEntity(pos);
            if (liquidBE instanceof TrayWithLiquidBlockEntity entity) {
                entity.setStoredLiquid(new ItemStack(ModItems.LIQUID_DMT.get()));
            }

            held.shrink(1);
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.PASS;
    }


    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return type == ModBlockEntities.TRAY.get()
                ? (BlockEntityTicker<T>) TrayBlockEntity.getTicker()
                : null;
    }
}
