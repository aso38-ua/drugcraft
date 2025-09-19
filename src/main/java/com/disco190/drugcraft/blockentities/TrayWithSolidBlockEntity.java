package com.disco190.drugcraft.blockentities;

import com.disco190.drugcraft.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class TrayWithSolidBlockEntity extends BlockEntity {

    private ItemStack storedMeth = ItemStack.EMPTY; // Aquí guardamos la meth sólida con NBT

    public TrayWithSolidBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.TRAY_WITH_SOLID.get(), pos, state);
    }

    // Guardar los datos en NBT del bloque
    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        if (!storedMeth.isEmpty()) {
            tag.put("StoredMeth", storedMeth.save(new CompoundTag()));
        }
    }

    // Cargar los datos al entrar al mundo
    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if (tag.contains("StoredMeth")) {
            storedMeth = ItemStack.of(tag.getCompound("StoredMeth"));
        }
    }

    public void setStoredMeth(ItemStack stack) {
        this.storedMeth = stack.copy();
    }

    public ItemStack getStoredMeth() {
        return storedMeth.copy();
    }

    public void clearStoredMeth() {
        this.storedMeth = ItemStack.EMPTY;
    }

    public void tick() {
        // Aquí podrías meter lógica de secado si quieres (contadores, tiempo, etc.)
    }
}
