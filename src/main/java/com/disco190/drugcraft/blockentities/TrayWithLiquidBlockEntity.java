package com.disco190.drugcraft.blockentities;

import com.disco190.drugcraft.blocks.ModBlocks;
import com.disco190.drugcraft.item.ModItems;
import com.disco190.drugcraft.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class TrayWithLiquidBlockEntity extends BlockEntity {

    private int progress = 0;
    private static final int MAX_PROGRESS = 200; // 10 segundos a 20 ticks/segundo

    public TrayWithLiquidBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.TRAY_WITH_LIQUID.get(), pos, state);
    }

    public void tick() {
        if (level == null || level.isClientSide) return;

        if (progress < MAX_PROGRESS) {
            progress++;
            setChanged();
        } else {
            // Cuando llega al máximo, transforma el bloque automáticamente en sólido
            transformToSolid(level, worldPosition);
        }
    }

    private void transformToSolid(Level level, BlockPos pos) {
        // Cambiar el bloque al sólido
        level.setBlock(pos, ModBlocks.TRAY_WITH_SOLID.get().defaultBlockState(), 3);

        // Obtener la nueva BE (sólida)
        BlockEntity newBE = level.getBlockEntity(pos);
        if (newBE instanceof TrayWithSolidBlockEntity solidBE) {
            // Crear la meth sólida como ItemStack
            ItemStack solidMeth = new ItemStack(ModItems.METH.get(), 4);

            // 👉 Si quieres copiar NBT del líquido, puedes hacerlo aquí:
            // CompoundTag tag = new CompoundTag();
            // solidMeth.setTag(tag);

            // Guardar dentro de la bandeja sólida
            solidBE.setStoredMeth(solidMeth);
        }
    }

    public boolean isReady() {
        return progress >= MAX_PROGRESS;
    }

    public void reset() {
        progress = 0;
        setChanged();
    }
}
