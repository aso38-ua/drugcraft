package com.disco190.drugcraft.menu;

import com.disco190.drugcraft.blockentities.DehydratorBlockEntity;
import com.disco190.drugcraft.registry.ModMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.SlotItemHandler;

import java.util.Objects;

public class DehydratorMenu extends AbstractContainerMenu {

    private final DehydratorBlockEntity blockEntity;
    private final ContainerData data;

    // Client
    public DehydratorMenu(int id, Inventory playerInv, FriendlyByteBuf buf) {
        this(id, playerInv, Objects.requireNonNull(getBE(playerInv, buf)), new SimpleContainerData(4));
    }

    private static DehydratorBlockEntity getBE(Inventory inv, FriendlyByteBuf buf) {
        BlockEntity be = inv.player.level().getBlockEntity(buf.readBlockPos());
        if (be instanceof DehydratorBlockEntity dehydrator) {
            return dehydrator;
        }
        return null;
    }

    // Server
    public DehydratorMenu(int id, Inventory playerInv, DehydratorBlockEntity entity, ContainerData data) {
        super(ModMenuTypes.DEHYDRATOR.get(), id);
        this.blockEntity = entity;
        this.data = data;

        // Input Slot 0
        this.addSlot(new SlotItemHandler(entity.getItemHandler(), 0, 56, 17));

        // Fuel Slot 1
        this.addSlot(new SlotItemHandler(entity.getItemHandler(), 1, 56, 53));

        // Output Slot 2
        this.addSlot(new SlotItemHandler(entity.getItemHandler(), 2, 116, 35) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return false;
            }

            @Override
            public void onTake(Player player, ItemStack stack) {
                if (blockEntity != null)
                    blockEntity.setChanged();
                super.onTake(player, stack);
            }
        });

        addPlayerInventory(playerInv);
        addPlayerHotbar(playerInv);

        addDataSlots(data);
    }

    @Override
    public boolean stillValid(Player player) {
        return true;
    }

    private void addPlayerInventory(Inventory inv) {
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 9; col++) {
                this.addSlot(new Slot(inv, col + row * 9 + 9, 8 + col * 18, 84 + row * 18));
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
        if (slot == null || !slot.hasItem())
            return ItemStack.EMPTY;

        ItemStack stack = slot.getItem();
        ItemStack copy = stack.copy();

        final int INPUT_SLOT = 0;
        final int FUEL_SLOT = 1;
        final int OUTPUT_SLOT = 2;
        final int PLAYER_INV_START = 3;
        final int PLAYER_INV_END = this.slots.size();

        if (index == OUTPUT_SLOT) {
            if (!moveItemStackTo(stack, PLAYER_INV_START, PLAYER_INV_END, true))
                return ItemStack.EMPTY;
            slot.onQuickCraft(stack, copy);
        } else if (index == INPUT_SLOT || index == FUEL_SLOT) {
            if (!moveItemStackTo(stack, PLAYER_INV_START, PLAYER_INV_END, false))
                return ItemStack.EMPTY;
        } else {
            // Check if it's fuel
            if (net.minecraftforge.common.ForgeHooks.getBurnTime(stack, null) > 0) {
                if (!moveItemStackTo(stack, FUEL_SLOT, FUEL_SLOT + 1, false)) {
                    // If fuel slot full or failed, try input slot if valid input
                    if (!moveItemStackTo(stack, INPUT_SLOT, INPUT_SLOT + 1, false))
                        return ItemStack.EMPTY;
                }
            } else {
                // Not fuel, try input
                if (!moveItemStackTo(stack, INPUT_SLOT, INPUT_SLOT + 1, false))
                    return ItemStack.EMPTY;
            }
        }

        if (stack.isEmpty())
            slot.set(ItemStack.EMPTY);
        else
            slot.setChanged();

        return copy;
    }

    public int getProgress() {
        return data.get(0);
    }

    public int getMaxProgress() {
        return data.get(1);
    }

    public int getLitTime() {
        return data.get(2);
    }

    public int getMaxLitTime() {
        return data.get(3);
    }

    public boolean isLit() {
        return data.get(2) > 0;
    }
}
