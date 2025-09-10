package com.disco190.drugcraft.blockentities;

import com.disco190.drugcraft.menu.ChemistryStationMenu;
import com.disco190.drugcraft.recipes.ChemistryStationRecipes;
import com.disco190.drugcraft.registry.ModBlockEntities;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemStackHandler;

import java.util.List;

public class ChemistryStationBlockEntity extends BlockEntity implements net.minecraft.world.MenuProvider {

    // Inventarios
    private final ItemStackHandler input = new ItemStackHandler(3) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            cookTime = 0; // reinicia si cambian los inputs
        }
    };

    private final ItemStackHandler output = new ItemStackHandler(1) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };

    private int cookTime = 0;
    private int cookTimeTotal = 400; // ticks (20 = 1 segundo)

    public ChemistryStationBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.CHEMISTRY_STATION.get(), pos, state);
    }

    // ✅ Guardar en NBT
    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.put("Input", input.serializeNBT());
        tag.put("Output", output.serializeNBT());
        tag.putInt("CookTime", cookTime);
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        input.deserializeNBT(tag.getCompound("Input"));
        output.deserializeNBT(tag.getCompound("Output"));
        cookTime = tag.getInt("CookTime");
    }

    // === Getters para el menú ===
    public ItemStackHandler getInputHandler() { return input; }
    public ItemStackHandler getOutputHandler() { return output; }
    public int getCookTime() { return cookTime; }
    public int getCookTimeTotal() { return cookTimeTotal; }

    @Override
    public Component getDisplayName() {
        return Component.literal("Chemistry Station");
    }

    @Override
    public AbstractContainerMenu createMenu(int id, Inventory playerInventory, Player player) {
        return new ChemistryStationMenu(id, playerInventory, ContainerLevelAccess.create(level, worldPosition));
    }

    // === Lógica de recetas ===
    public ItemStack getResultForInputs() {
        List<ItemStack> inputs = List.of(
                input.getStackInSlot(0),
                input.getStackInSlot(1),
                input.getStackInSlot(2)
        );
        return ChemistryStationRecipes.getResult(inputs);
    }

    public void craftCurrentRecipe() {
        ItemStack result = getResultForInputs();
        if (!result.isEmpty()) {
            for (int i = 0; i < input.getSlots(); i++) {
                input.extractItem(i, 1, false);
            }
            setChanged();
        }
    }

    public static void tick(Level level, BlockPos pos, BlockState state, ChemistryStationBlockEntity blockEntity) {
        ItemStack result = blockEntity.getResultForInputs();

        if (!result.isEmpty()) {
            blockEntity.cookTime++;
            System.out.println("[DEBUG] cookTime: " + blockEntity.cookTime + "/" + blockEntity.cookTimeTotal);

            // 🔊 Sonido al empezar
            if (blockEntity.cookTime == 1) {
                level.playSound(null, pos,
                        net.minecraft.sounds.SoundEvents.BREWING_STAND_BREW,
                        net.minecraft.sounds.SoundSource.BLOCKS,
                        1.0F, 1.0F);
            }

            // 💨 Partículas mientras cocina (solo cliente)
            if (level.isClientSide) {
                double x = pos.getX() + 0.5;
                double y = pos.getY() + 1.1;
                double z = pos.getZ() + 0.5;
                level.addParticle(net.minecraft.core.particles.ParticleTypes.SMOKE, x, y, z, 0, 0.05, 0);
            }

            // ✅ Termina proceso
            if (blockEntity.cookTime >= blockEntity.cookTimeTotal) {
                blockEntity.cookTime = 0;
                blockEntity.craftCurrentRecipe();
                blockEntity.output.setStackInSlot(0, result.copy());
                setChanged(level, pos, state);
            }
        } else {
            blockEntity.cookTime = 0;
        }
    }

    // --- Necesarios para DataSlot ---
    public void setCookTime(int value) {
        this.cookTime = value;
    }

    public void setCookTimeTotal(int value) {
        this.cookTimeTotal = value;
    }

}
