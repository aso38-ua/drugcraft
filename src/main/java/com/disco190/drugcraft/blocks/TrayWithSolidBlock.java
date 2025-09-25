package com.disco190.drugcraft.blocks;

import com.disco190.drugcraft.blockentities.TrayWithSolidBlockEntity;
import com.disco190.drugcraft.registry.ModBlockEntities;
import com.disco190.drugcraft.util.DrugType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
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
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;

public class TrayWithSolidBlock extends BaseEntityBlock {

    public static final EnumProperty<DrugType> DRUG_TYPE = EnumProperty.create("drug_type", DrugType.class);

    private static final VoxelShape SHAPE = Shapes.or(
            Block.box(1, 1, 3, 15, 2, 13), // base
            Block.box(1, 2, 3, 15, 3, 4),   // norte pared
            Block.box(1, 2, 12, 15, 3, 13), // sur pared
            Block.box(1, 2, 3, 2, 3, 13),   // oeste pared
            Block.box(14, 2, 3, 15, 3, 13)  // este pared
    );

    public TrayWithSolidBlock() {
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
        return new TrayWithSolidBlockEntity(pos, state);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return type == ModBlockEntities.TRAY_WITH_SOLID.get()
                ? (lvl, pos, st, be) -> ((TrayWithSolidBlockEntity) be).tick()
                : null;
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos,
                                 Player player, InteractionHand hand, BlockHitResult hit) {

        if (level.isClientSide) return InteractionResult.SUCCESS;

        BlockEntity be = level.getBlockEntity(pos);
        if (!(be instanceof TrayWithSolidBlockEntity tray)) return InteractionResult.PASS;

        ItemStack held = player.getItemInHand(hand);

        if (held.getItem() == net.minecraft.world.item.Items.STICK) {
            ItemStack drug = tray.getStoredDrug();
            if (!drug.isEmpty()) {
                popResource(level, pos, drug);
                tray.clearStoredDrug();

                level.playSound(null, pos,
                        net.minecraft.sounds.SoundEvents.GLASS_BREAK,
                        net.minecraft.sounds.SoundSource.BLOCKS,
                        1.0f, 1.0f);

                // vuelve a la bandeja vacía
                level.setBlock(pos, com.disco190.drugcraft.blocks.ModBlocks.TRAY.get().defaultBlockState(), 3);
                return InteractionResult.SUCCESS;
            }
        }

        return InteractionResult.PASS;
    }

    @Override
    public VoxelShape getShape(BlockState state, net.minecraft.world.level.BlockGetter level, BlockPos pos,
                               net.minecraft.world.phys.shapes.CollisionContext context) {
        return SHAPE;
    }
}
