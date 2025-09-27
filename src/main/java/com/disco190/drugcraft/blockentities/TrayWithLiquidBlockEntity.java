package com.disco190.drugcraft.blockentities;

import com.disco190.drugcraft.blocks.ModBlocks;
import com.disco190.drugcraft.blocks.TrayWithSolidBlock;
import com.disco190.drugcraft.item.ModItems;
import com.disco190.drugcraft.items.MethItem; // Necesario para usar setPurity
import com.disco190.drugcraft.registry.ModBlockEntities;
import com.disco190.drugcraft.util.DrugType;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
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
        System.out.println("[DEBUG] transformToSolid: drugType=" + drugType + " storedLiquid=" + storedLiquid + " NBT=" + storedLiquid.getTag());

        level.setBlock(pos, ModBlocks.TRAY_WITH_SOLID.get()
                        .defaultBlockState()
                        .setValue(TrayWithSolidBlock.DRUG_TYPE, drugType),
                3);

        BlockEntity newBE = level.getBlockEntity(pos);
        if (newBE instanceof TrayWithSolidBlockEntity solidBE) {
            ItemStack result = switch (drugType) {
                case METH      -> new ItemStack(ModItems.METH.get(), 2);
                case DMT       -> new ItemStack(ModItems.DMT.get(), 1);
                case BLUE_METH -> new ItemStack(ModItems.BLUE_METH.get(), 2);
            };

            if (!storedLiquid.isEmpty() && storedLiquid.hasTag()) {
                result.setTag(storedLiquid.getTag().copy());
            }

            System.out.println("[DEBUG] transformToSolid: resultItem=" + result + " NBT=" + result.getTag());

            solidBE.setStoredDrug(result, drugType);
        }
    }



    public void setStoredLiquid(ItemStack stack) {
        this.storedLiquid = stack.copy();
        System.out.println("[DEBUG] setStoredLiquid: " + stack.getItem() + " NBT=" + stack.getTag());

        if (stack.getItem() == ModItems.LIQUID_DMT.get()) {
            this.drugType = DrugType.DMT;
        } else if (stack.getItem() == ModItems.BLUE_LIQUID_METH.get()) {
            this.drugType = DrugType.BLUE_METH;
        } else {
            this.drugType = DrugType.METH;
        }

        setChanged();
    }


    // Getters y Save/Load

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putInt("Progress", progress);
        tag.putString("DrugType", drugType.getSerializedName());
        if (!storedLiquid.isEmpty()) {
            tag.put("StoredLiquid", storedLiquid.save(new CompoundTag()));
        }
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        progress = tag.getInt("Progress");
        if (tag.contains("DrugType")) {
            try {
                drugType = DrugType.valueOf(tag.getString("DrugType").toUpperCase());
            } catch (IllegalArgumentException ignored) {
                drugType = DrugType.METH; // fallback
            }
        }
        if (tag.contains("StoredLiquid")) {
            storedLiquid = ItemStack.of(tag.getCompound("StoredLiquid"));
        }
    }

    public int getProgress() {
        return progress;
    }
}
