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

    // Definición de la forma del bloque (VoxelShape)
    private static final VoxelShape SHAPE = Shapes.or(
            Block.box(1, 1, 3, 15, 2, 13), // Base de la bandeja
            Block.box(1, 2, 3, 15, 3, 4),  // Borde delantero
            Block.box(1, 2, 12, 15, 3, 13),// Borde trasero
            Block.box(1, 2, 3, 2, 3, 13),  // Borde izquierdo
            Block.box(14, 2, 3, 15, 3, 13) // Borde derecho
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
    public VoxelShape getShape(BlockState state, net.minecraft.world.level.BlockGetter level, BlockPos pos,
                               net.minecraft.world.phys.shapes.CollisionContext context) {
        return SHAPE;
    }

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new TrayWithSolidBlockEntity(pos, state);
    }

    // CORRECCIÓN: Se devuelve null aquí.
    // La bandeja sólida no tiene lógica de tiempo (como secado o cocción), por lo que no necesita un ticker.
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return null;
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos,
                                 Player player, InteractionHand hand, BlockHitResult hit) {

        if (level.isClientSide) return InteractionResult.SUCCESS;

        BlockEntity be = level.getBlockEntity(pos);
        if (!(be instanceof TrayWithSolidBlockEntity tray)) return InteractionResult.PASS;

        ItemStack held = player.getItemInHand(hand);

        // Lógica para recoger el contenido con un stick (palo)
        if (held.getItem() == net.minecraft.world.item.Items.STICK) {
            ItemStack drug = tray.getStoredDrug();
            System.out.println("[DEBUG] use(STICK): drug=" + drug + " NBT=" + drug.getTag());
            if (!drug.isEmpty()) {
                ItemStack drop = drug.copy(); // aseguramos que se dropea con NBT intacto
                popResource(level, pos, drop);
                tray.clearStoredDrug();

                // Sonido de rompimiento/recolección
                level.playSound(null, pos,
                        net.minecraft.sounds.SoundEvents.GLASS_BREAK,
                        net.minecraft.sounds.SoundSource.BLOCKS,
                        1.0f, 1.0f);

                // Volver a la bandeja vacía
                level.setBlock(pos, ModBlocks.TRAY.get().defaultBlockState(), 3);
                return InteractionResult.SUCCESS;
            }
        }

        return InteractionResult.PASS;
    }

    @Override
    public void onRemove(BlockState state, Level level, BlockPos pos, BlockState newState, boolean isMoving) {
        if (state.hasBlockEntity() && (!state.is(newState.getBlock()) || !newState.hasBlockEntity())) {
            BlockEntity be = level.getBlockEntity(pos);
            if (be instanceof TrayWithSolidBlockEntity tray) {
                // Al romper el bloque, dropea el contenido
                popResource(level, pos, tray.getStoredDrug());
            }
        }
        super.onRemove(state, level, pos, newState, isMoving);
    }
}
