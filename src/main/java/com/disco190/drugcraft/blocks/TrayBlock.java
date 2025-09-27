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

import javax.annotation.Nullable; // Added import

public class TrayBlock extends BaseEntityBlock {

    private static final VoxelShape SHAPE = Shapes.or(
            Block.box(0, 0, 0, 16, 1, 16),
            Block.box(0, 1, 0, 1, 3, 16),
            Block.box(15, 1, 0, 16, 3, 16),
            Block.box(1, 1, 0, 15, 3, 1),
            Block.box(1, 1, 15, 15, 3, 16)
    );

    public TrayBlock(Properties properties) {
        super(properties.strength(1.0f).sound(SoundType.WOOD).noOcclusion());
    }

    @Override
    public VoxelShape getShape(BlockState state, BlockGetter level, BlockPos pos, CollisionContext context) {
        return SHAPE;
    }

    @Override
    public RenderShape getRenderShape(BlockState state) {
        return RenderShape.MODEL;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new TrayBlockEntity(pos, state);
    }

    // This method handles right-click interactions, such as placing liquid meth/DMT
    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos,
                                 Player player, InteractionHand hand, BlockHitResult hit) {

        if (level.isClientSide) return InteractionResult.SUCCESS;

        BlockEntity be = level.getBlockEntity(pos);
        if (!(be instanceof TrayBlockEntity tray)) return InteractionResult.PASS;

        ItemStack held = player.getItemInHand(hand);

        // Liquid Meth
        if (held.getItem() == ModItems.LIQUID_METH.get() && tray.isEmpty()) {
            placeLiquidInTray(pos, level, held, DrugType.METH);
            return InteractionResult.SUCCESS;
        }

        // Blue Liquid Meth
        if (held.getItem() == ModItems.BLUE_LIQUID_METH.get() && tray.isEmpty()) {
            placeLiquidInTray(pos, level, held, DrugType.BLUE_METH);
            return InteractionResult.SUCCESS;
        }

        // Liquid DMT (sin NBT)
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

    // ------------------------
// Método auxiliar (fuera de use)
// ------------------------
    private void placeLiquidInTray(BlockPos pos, Level level, ItemStack stack, DrugType type) {
        level.setBlock(pos, ModBlocks.TRAY_WITH_LIQUID.get()
                .defaultBlockState().setValue(TrayWithLiquidBlock.DRUG_TYPE, type), 3);

        BlockEntity liquidBE = level.getBlockEntity(pos);
        if (liquidBE instanceof TrayWithLiquidBlockEntity entity) {
            entity.setStoredLiquid(stack.copy()); // ✅ Copia con NBT
        }

        stack.shrink(1);
    }





    // This method handles dropping the contents when the block is broken
    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.hasBlockEntity() && (!state.is(newState.getBlock()) || !newState.hasBlockEntity())) {
            BlockEntity be = level.getBlockEntity(pos);
            if (be instanceof TrayBlockEntity tray) {
                // Al romper el bloque, dropea el contenido
                popResource(level, pos, tray.getContent());
            }
        }
        super.onRemove(state, level, pos, newState, isMoving);
    }

    // This is the getTicker method, which was referencing the non-existent tick method.
    // It now correctly points to the new static method in TrayBlockEntity.
    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        if (level.isClientSide) {
            return null;
        }

        // CORRECTED: This line now references the newly added static tick method in TrayBlockEntity.
        return createTickerHelper(type, ModBlockEntities.TRAY.get(), TrayBlockEntity::tick);
    }
}
