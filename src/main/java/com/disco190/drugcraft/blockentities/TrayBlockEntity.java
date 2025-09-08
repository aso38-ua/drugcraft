package com.disco190.drugcraft.blockentities;

import com.disco190.drugcraft.item.ModItems;
import com.disco190.drugcraft.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
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

    public static <T extends BlockEntity> BlockEntityTicker<T> getTicker() {
        return (level, pos, state, be) -> { /* nada más, tray vacía no evoluciona sola */ };
    }
}
