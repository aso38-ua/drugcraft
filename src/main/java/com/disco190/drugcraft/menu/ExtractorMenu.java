package com.disco190.drugcraft.menu;

import com.disco190.drugcraft.blockentities.ExtractorBlockEntity;
import com.disco190.drugcraft.registry.ModMenuTypes;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraft.network.FriendlyByteBuf;

public class ExtractorMenu extends AbstractContainerMenu {

    private final ExtractorBlockEntity blockEntity;

    public ExtractorMenu(int id, Inventory playerInv, FriendlyByteBuf buf) {
        this(
                id,
                playerInv,
                (ExtractorBlockEntity) playerInv.player.level()
                        .getBlockEntity(buf.readBlockPos())
        );
    }

    // 👉 Constructor REAL
    public ExtractorMenu(int id, Inventory playerInv, ExtractorBlockEntity entity) {
        super(ModMenuTypes.EXTRACTOR.get(), id);
        this.blockEntity = entity;

        // Slots máquina
        // Input
        this.addSlot(new SlotItemHandler(entity.getItemHandler(), 0, 56, 35));

        // Output
        this.addSlot(new SlotItemHandler(entity.getItemHandler(), 2, 116, 35) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return false;
            }
        });


        addPlayerInventory(playerInv);
        addPlayerHotbar(playerInv);
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }


    private void addPlayerInventory(Inventory inv) {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                this.addSlot(new Slot(inv, col + row * 9 + 9,
                        8 + col * 18, 84 + row * 18));
            }
        }
    }

    private void addPlayerHotbar(Inventory inv) {
        for (int col = 0; col < 9; col++) {
            this.addSlot(new Slot(inv, col, 8 + col * 18, 142));
        }
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        Slot slot = this.slots.get(index);
        if (slot == null || !slot.hasItem()) return ItemStack.EMPTY;

        ItemStack stack = slot.getItem();
        ItemStack copy = stack.copy();

        final int inputSlot = 0;
        final int outputSlot = 2;
        final int playerInvStart = 3;
        final int playerInvEnd = this.slots.size();

        // Si es slot de salida, mover al inventario del jugador
        if (index == outputSlot) {
            if (!moveItemStackTo(stack, playerInvStart, playerInvEnd, true)) return ItemStack.EMPTY;
            slot.onQuickCraft(stack, copy);
        }
        // Si es slot de input, mover al inventario
        else if (index == inputSlot) {
            if (!moveItemStackTo(stack, playerInvStart, playerInvEnd, false)) return ItemStack.EMPTY;
        }
        // Si es inventario del jugador, mover al input del bloque
        else if (index >= playerInvStart) {
            if (!moveItemStackTo(stack, inputSlot, inputSlot + 1, false)) return ItemStack.EMPTY;
        }

        if (stack.isEmpty()) slot.set(ItemStack.EMPTY);
        else slot.setChanged();

        return copy;
    }


}

