package com.disco190.drugcraft.blockentities;

import com.disco190.drugcraft.blocks.ModBlocks;
import com.disco190.drugcraft.item.ModItems;
import com.disco190.drugcraft.registry.ModBlockEntities;
import com.disco190.drugcraft.util.DrugType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class TrayWithLiquidBlockEntity extends BlockEntity {

    private int progress = 0;
    private static final int MAX_PROGRESS = 200; // 10 segundos
    private DrugType drugType = DrugType.METH; // por defecto
    private ItemStack storedLiquid = ItemStack.EMPTY;

    public TrayWithLiquidBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.TRAY_WITH_LIQUID.get(), pos, state);
    }

    public void tick() {
        if (level == null || level.isClientSide) return;

        if (progress < MAX_PROGRESS) {
            progress++;
            setChanged();
        } else {
            transformToSolid(level, worldPosition);
        }
    }

    private void transformToSolid(Level level, BlockPos pos) {
        // Cambiar bloque a sólido con drug_type correcto
        level.setBlock(pos,
                ModBlocks.TRAY_WITH_SOLID.get().defaultBlockState()
                        .setValue(com.disco190.drugcraft.blocks.TrayWithSolidBlock.DRUG_TYPE, drugType),
                3);

        BlockEntity newBE = level.getBlockEntity(pos);
        if (newBE instanceof TrayWithSolidBlockEntity solidBE) {
            // Resultado sólido
            ItemStack result = switch (drugType) {
                case METH -> new ItemStack(ModItems.METH.get(), 2);
                case DMT  -> new ItemStack(ModItems.DMT.get(), 1);
            };

            solidBE.setStoredDrug(result, drugType);
        }
    }

    public void setStoredLiquid(ItemStack stack) {
        this.storedLiquid = stack.copy();

        // detectar tipo de droga por el ítem
        if (stack.getItem() == com.disco190.drugcraft.item.ModItems.LIQUID_DMT.get()) {
            this.drugType = DrugType.DMT;
        } else {
            this.drugType = DrugType.METH;
        }

        setChanged();
    }

    public ItemStack getStoredLiquid() {
        return storedLiquid.copy();
    }
}
