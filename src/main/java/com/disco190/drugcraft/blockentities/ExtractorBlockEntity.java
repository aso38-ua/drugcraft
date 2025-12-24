package com.disco190.drugcraft.blockentities;

import com.disco190.drugcraft.menu.ExtractorMenu;
import com.disco190.drugcraft.recipes.ExtractorRecipes;
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
import org.jetbrains.annotations.Nullable;
import com.disco190.drugcraft.registry.ModBlockEntities;
import com.disco190.drugcraft.item.ModItems;

public class ExtractorBlockEntity extends BlockEntity implements MenuProvider {

    private final ItemStackHandler itemHandler = new ItemStackHandler(3);
    private int progress = 0;
    private static final int MAX_PROGRESS = 200;

    public ExtractorBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.EXTRACTOR.get(), pos, state);
    }

    public static void tick(Level level, BlockPos pos, BlockState state, ExtractorBlockEntity entity) {
        if (level.isClientSide) return;

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
        return !itemHandler.getStackInSlot(0).isEmpty();
    }

    private void craftItem() {
        ItemStack input = itemHandler.getStackInSlot(0);
        ItemStack output = ExtractorRecipes.getResult(input);

        if (!output.isEmpty()) {
            itemHandler.extractItem(0, 1, false); // consumimos input
            itemHandler.setStackInSlot(2, output); // ponemos output
        }
    }


    public ItemStackHandler getItemHandler() {
        return itemHandler;
    }

    @Override
    public Component getDisplayName() {
        return Component.literal("Extractor");
    }

    @Override
    public AbstractContainerMenu createMenu(int id, Inventory inventory, Player player) {
        return new ExtractorMenu(id, inventory, this);
    }

}

