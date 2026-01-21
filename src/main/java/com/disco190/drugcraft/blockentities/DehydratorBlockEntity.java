package com.disco190.drugcraft.blockentities;

import com.disco190.drugcraft.menu.DehydratorMenu;
import com.disco190.drugcraft.recipes.DehydratorRecipes;
import com.disco190.drugcraft.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemStackHandler;

public class DehydratorBlockEntity extends BlockEntity implements MenuProvider {

    private final ItemStackHandler itemHandler = new ItemStackHandler(3);
    private int progress = 0;
    private static final int MAX_PROGRESS = 200;

    public DehydratorBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.DEHYDRATOR.get(), pos, state);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, DehydratorBlockEntity entity) {
        if (level.isClientSide)
            return;

        if (entity.hasRecipe()) {
            entity.progress++;
            if (entity.progress >= MAX_PROGRESS) {
                entity.craftItem();
                entity.progress = 0;
            }
        } else {
            entity.progress = 0;
        }
    }

    private boolean hasRecipe() {
        ItemStack input = itemHandler.getStackInSlot(0);

        if (input.isEmpty())
            return false;

        ItemStack result = DehydratorRecipes.getResult(input);
        if (result.isEmpty())
            return false;

        ItemStack output = itemHandler.getStackInSlot(2);

        // Output vacío → OK
        if (output.isEmpty())
            return true;

        // Output compatible y con espacio
        return output.getItem() == result.getItem()
                && output.getCount() < output.getMaxStackSize();
    }

    private void craftItem() {
        ItemStack input = itemHandler.getStackInSlot(0);
        ItemStack result = DehydratorRecipes.getResult(input);

        if (result.isEmpty())
            return;

        // Consumir input
        itemHandler.extractItem(0, 1, false);

        // Insertar output
        ItemStack output = itemHandler.getStackInSlot(2);
        if (output.isEmpty()) {
            itemHandler.setStackInSlot(2, result.copy());
        } else {
            output.grow(result.getCount());
        }
    }

    public ItemStackHandler getItemHandler() {
        return itemHandler;
    }

    public int getProgress() {
        return progress;
    }

    public int getMaxProgress() {
        return MAX_PROGRESS;
    }

    public void setProgress(int value) {
        this.progress = value;
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Dehydrator");
    }

    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new DehydratorMenu(id, inventory, this);
    }

    @Override
    protected void saveAdditional(net.minecraft.nbt.CompoundTag tag) {
        tag.put("inventory", itemHandler.serializeNBT());
        tag.putInt("progress", progress);
        super.saveAdditional(tag);
    }

    @Override
    public void load(net.minecraft.nbt.CompoundTag tag) {
        super.load(tag);
        itemHandler.deserializeNBT(tag.getCompound("inventory"));
        progress = tag.getInt("progress");
    }
}
