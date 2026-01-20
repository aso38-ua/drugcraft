package com.disco190.drugcraft.menu;

import com.disco190.drugcraft.blockentities.ExtractorBlockEntity;
import com.disco190.drugcraft.registry.ModMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.SlotItemHandler;

import java.util.Objects;

public class ExtractorMenu extends AbstractContainerMenu {

    private final ExtractorBlockEntity blockEntity;

    // Constructor CLIENTE (red)
    public ExtractorMenu(int id, Inventory playerInv, FriendlyByteBuf buf) {
        this(id, playerInv, Objects.requireNonNull(getBE(playerInv, buf)));
    }

    private static ExtractorBlockEntity getBE(Inventory inv, FriendlyByteBuf buf) {
        BlockEntity be = inv.player.level().getBlockEntity(buf.readBlockPos());
        if (be instanceof ExtractorBlockEntity extractor) {
            return extractor;
        }
        return null;
    }


    // Constructor SERVIDOR
    public ExtractorMenu(int id, Inventory playerInv, ExtractorBlockEntity entity) {
        super(ModMenuTypes.EXTRACTOR.get(), id);
        this.blockEntity = entity;

        // ===== SLOTS DE LA MÁQUINA =====

        // Slot 0 → INPUT (opium_latex)
        this.addSlot(new SlotItemHandler(entity.getItemHandler(), 0, 51, 40));

        // Slot 2 → OUTPUT (opio)
        this.addSlot(new SlotItemHandler(entity.getItemHandler(), 2, 120, 40) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return false;
            }

            @Override
            public void onTake(Player player, ItemStack stack) {
                if (blockEntity != null) blockEntity.setChanged();
                super.onTake(player, stack);
            }
        });

        // ===== INVENTARIO DEL JUGADOR =====
        addPlayerInventory(playerInv);
        addPlayerHotbar(playerInv);

        // ===== SINCRONIZACIÓN DE PROGRESO =====
        if (blockEntity != null) {
            addDataSlot(new DataSlot() {
                @Override
                public int get() {
                    return blockEntity.getProgress();
                }

                @Override
                public void set(int value) {
                    blockEntity.setProgress(value);
                }
            });

            addDataSlot(new DataSlot() {
                @Override
                public int get() {
                    return blockEntity.getMaxProgress();
                }

                @Override
                public void set(int value) {}
            });
        }
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    // =========================
    // INVENTARIO JUGADOR
    // =========================

    private void addPlayerInventory(Inventory inv) {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                this.addSlot(new Slot(
                        inv,
                        col + row * 9 + 9,
                        8 + col * 18,
                        84 + row * 18
                ));
            }
        }
    }

    private void addPlayerHotbar(Inventory inv) {
        for (int col = 0; col < 9; col++) {
            this.addSlot(new Slot(
                    inv,
                    col,
                    8 + col * 18,
                    142
            ));
        }
    }

    // =========================
    // SHIFT + CLICK (ESTABLE)
    // =========================

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        Slot slot = this.slots.get(index);
        if (slot == null || !slot.hasItem()) return ItemStack.EMPTY;

        ItemStack stack = slot.getItem();
        ItemStack copy = stack.copy();

        final int INPUT_MENU_SLOT = 0;
        final int OUTPUT_MENU_SLOT = 1;
        final int PLAYER_INV_START = 2;
        final int PLAYER_INV_END = this.slots.size();

        // 👉 Si es output → inventario jugador
        if (index == OUTPUT_MENU_SLOT) {
            if (!moveItemStackTo(stack, PLAYER_INV_START, PLAYER_INV_END, true))
                return ItemStack.EMPTY;
            slot.onQuickCraft(stack, copy);
        }
        // 👉 Si es input → inventario jugador
        else if (index == INPUT_MENU_SLOT) {
            if (!moveItemStackTo(stack, PLAYER_INV_START, PLAYER_INV_END, false))
                return ItemStack.EMPTY;
        }
        // 👉 Si es inventario jugador → input
        else {
            if (!moveItemStackTo(stack, INPUT_MENU_SLOT, INPUT_MENU_SLOT + 1, false))
                return ItemStack.EMPTY;
        }

        if (stack.isEmpty()) slot.set(ItemStack.EMPTY);
        else slot.setChanged();

        return copy;
    }

    // ===== GETTERS PARA LA GUI =====

    public int getProgress() {
        return blockEntity != null ? blockEntity.getProgress() : 0;
    }

    public int getMaxProgress() {
        return blockEntity != null ? blockEntity.getMaxProgress() : 200;
    }
}
