package com.disco190.drugcraft.blockentities;

import com.disco190.drugcraft.item.ModItems;
import com.disco190.drugcraft.menu.DehydratorMenu;
import com.disco190.drugcraft.recipes.DehydratorRecipes;
import com.disco190.drugcraft.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.items.ItemStackHandler;

public class DehydratorBlockEntity extends BlockEntity implements MenuProvider {

    private final ItemStackHandler itemHandler = new ItemStackHandler(3) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };

    private int progress = 0;
    private static final int MAX_PROGRESS = 200;

    private int litTime;
    private int maxLitTime;

    protected final ContainerData data;

    public DehydratorBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.DEHYDRATOR.get(), pos, state);
        this.data = new ContainerData() {
            @Override
            public int get(int index) {
                return switch (index) {
                    case 0 -> DehydratorBlockEntity.this.progress;
                    case 1 -> DehydratorBlockEntity.MAX_PROGRESS;
                    case 2 -> DehydratorBlockEntity.this.litTime;
                    case 3 -> DehydratorBlockEntity.this.maxLitTime;
                    default -> 0;
                };
            }

            @Override
            public void set(int index, int value) {
                switch (index) {
                    case 0 -> DehydratorBlockEntity.this.progress = value;
                    case 2 -> DehydratorBlockEntity.this.litTime = value;
                    case 3 -> DehydratorBlockEntity.this.maxLitTime = value;
                }
            }

            @Override
            public int getCount() {
                return 4;
            }
        };
    }

    public static void tick(Level level, BlockPos pos, BlockState state, DehydratorBlockEntity entity) {
        if (level.isClientSide)
            return;

        boolean isLit = entity.isLit();
        boolean changed = false;

        if (entity.isLit()) {
            entity.litTime--;
        }

        if (entity.hasRecipe()) {
            if (!entity.isLit()) {
                // Try to consume fuel
                ItemStack fuel = entity.itemHandler.getStackInSlot(1);
                int burnTime = ForgeHooks.getBurnTime(fuel, null);
                if (burnTime > 0) {
                    entity.litTime = burnTime;
                    entity.maxLitTime = burnTime;
                    fuel.shrink(1);
                    entity.itemHandler.setStackInSlot(1, fuel);
                    changed = true;
                }
            }

            if (entity.isLit()) {
                entity.progress++;
                if (entity.progress >= MAX_PROGRESS) {
                    entity.craftItem();
                    entity.progress = 0;
                }
            } else {
                if (entity.progress > 0) {
                    entity.progress = Math.max(0, entity.progress - 2);
                }
            }
        } else {
            entity.progress = 0;
        }

        if (isLit != entity.isLit()) {
            changed = true;
            // Here you would typically update block state to show litigation texture
        }

        if (changed) {
            setChanged(level, pos, state);
        }
    }

    private boolean isLit() {
        return this.litTime > 0;
    }

    private boolean hasRecipe() {
        ItemStack input = itemHandler.getStackInSlot(0);
        if (input.isEmpty()) return false;

        ItemStack result = DehydratorRecipes.getResult(input);
        if (result.isEmpty()) return false;

        // --- Lógica de tamaño de lote personalizada ---
        int maxBatch;
        if (input.is(ModItems.LIQUID_HEROIN.get())) {
            maxBatch = 1; // Heroína: de 1 en 1
        } else {
            maxBatch = 4; // Tabaco y otros: de 4 en 4
        }

        // Calculamos cuánto podemos procesar realmente según lo que hay en el slot
        int batchSize = Math.min(input.getCount(), maxBatch);
        int resultCount = result.getCount() * batchSize;

        ItemStack output = itemHandler.getStackInSlot(2);
        if (output.isEmpty()) return true;
        if (output.getItem() != result.getItem()) return false;

        return output.getCount() + resultCount <= output.getMaxStackSize();
    }

    private void craftItem() {
        ItemStack input = itemHandler.getStackInSlot(0);
        ItemStack result = DehydratorRecipes.getResult(input);

        if (result.isEmpty()) return;

        // --- Misma lógica de lote ---
        int maxBatch;
        if (input.is(ModItems.LIQUID_HEROIN.get())) {
            maxBatch = 1;
        } else {
            maxBatch = 4;
        }

        int batchSize = Math.min(input.getCount(), maxBatch);

        // Consumimos solo la cantidad calculada (1 o hasta 4)
        itemHandler.extractItem(0, batchSize, false);

        // El resultado es el producto base multiplicado por el lote
        int totalOutputCount = result.getCount() * batchSize;
        ItemStack outputStack = result.copy();
        outputStack.setCount(totalOutputCount);

        // Insertar en el slot de salida
        ItemStack output = itemHandler.getStackInSlot(2);
        if (output.isEmpty()) {
            itemHandler.setStackInSlot(2, outputStack);
        } else {
            output.grow(totalOutputCount);
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
        return new DehydratorMenu(id, inventory, this, this.data);
    }

    @Override
    protected void saveAdditional(net.minecraft.nbt.CompoundTag tag) {
        tag.put("inventory", itemHandler.serializeNBT());
        tag.putInt("progress", progress);
        tag.putInt("litTime", litTime);
        tag.putInt("maxLitTime", maxLitTime);
        super.saveAdditional(tag);
    }

    @Override
    public void load(net.minecraft.nbt.CompoundTag tag) {
        super.load(tag);
        itemHandler.deserializeNBT(tag.getCompound("inventory"));
        progress = tag.getInt("progress");
        litTime = tag.getInt("litTime");
        maxLitTime = tag.getInt("maxLitTime");
    }
}
