package com.disco190.drugcraft.blockentities;

import com.disco190.drugcraft.registry.ModBlockEntities;
import com.disco190.drugcraft.util.DrugType;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class TrayWithSolidBlockEntity extends BlockEntity {

    private ItemStack storedDrug = ItemStack.EMPTY;
    private DrugType drugType = DrugType.METH; // por defecto METH

    public TrayWithSolidBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.TRAY_WITH_SOLID.get(), pos, state);
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        if (!storedDrug.isEmpty()) {
            tag.put("StoredDrug", storedDrug.save(new CompoundTag()));
        }
        tag.putString("DrugType", drugType.getSerializedName());
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        if (tag.contains("StoredDrug")) {
            storedDrug = ItemStack.of(tag.getCompound("StoredDrug"));
        }
        if (tag.contains("DrugType")) {
            try {
                drugType = DrugType.valueOf(tag.getString("DrugType").toUpperCase());
            } catch (IllegalArgumentException ignored) {
                drugType = DrugType.METH; // fallback
            }
        }
    }

    public void setStoredDrug(ItemStack stack, DrugType type) {
        this.storedDrug = stack.copy();
        this.drugType = type;
        System.out.println("[DEBUG] setStoredDrug: " + stack.getItem() + " NBT=" + stack.getTag());
        setChanged();
    }



    public ItemStack getStoredDrug() {
        // Asegurarse de que la copia devuelta contenga el NBT
        return storedDrug.copy();
    }

    public void clearStoredDrug() {
        this.storedDrug = ItemStack.EMPTY;
        setChanged();
    }

    public boolean isEmpty() {
        return storedDrug.isEmpty();
    }
}
