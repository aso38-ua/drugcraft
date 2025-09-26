package com.disco190.drugcraft.blockentities;

import com.disco190.drugcraft.item.ModItems;
import com.disco190.drugcraft.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level; // Import Level is needed for the static tick method
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;

public class TrayBlockEntity extends BlockEntity {

    private ItemStack content = ItemStack.EMPTY;

    public TrayBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.TRAY.get(), pos, state);
    }

    public boolean isEmpty() {
        return content.isEmpty();
    }

    public ItemStack getContent() {
        return content;
    }

    public void setContent(ItemStack stack) {
        this.content = stack;
        setChanged();
    }

    public void clearContent() {
        this.content = ItemStack.EMPTY;
        setChanged();
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.put("Content", content.save(new CompoundTag()));
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        content = ItemStack.of(tag.getCompound("Content"));
    }

    // NEW: Added the required static tick method.
    // This resolves the "cannot find symbol" error when TrayBlock tries to reference it.
    // The signature must match the BlockEntityTicker interface expected by createTickerHelper.
    public static void tick(Level level, BlockPos pos, BlockState state, TrayBlockEntity blockEntity) {
        // The empty tray typically doesn't need ticking logic, but this method
        // is required for compilation if TrayBlock is trying to set up a ticker.
    }
}
