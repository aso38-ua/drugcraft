package com.disco190.drugcraft.menu;

import com.disco190.drugcraft.blockentities.ChemistryStationBlockEntity;
import com.disco190.drugcraft.registry.ModMenuTypes;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.inventory.DataSlot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.SlotItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

public class ChemistryStationMenu extends AbstractContainerMenu {

    private final ContainerLevelAccess access;
    private final IItemHandler inputHandler;
    private final IItemHandler outputHandler;
    private final ChemistryStationBlockEntity blockEntity;

    // Constructor cliente (buffer de red)
    public ChemistryStationMenu(int id, Inventory playerInventory, FriendlyByteBuf extraData) {
        this(id, playerInventory, ContainerLevelAccess.create(
                playerInventory.player.level(),
                extraData.readBlockPos()
        ));
    }

    // Constructor servidor
    public ChemistryStationMenu(int id, Inventory playerInventory, ContainerLevelAccess access) {
        super(ModMenuTypes.CHEMISTRY_STATION.get(), id);
        this.access = access;

        ChemistryStationBlockEntity tempBE = null;
        IItemHandler inputTemp = null;
        IItemHandler outputTemp = null;

        BlockPos pos = access.evaluate((lvl, p) -> p).orElse(null);
        Level level = access.evaluate((lvl, p) -> lvl).orElse(null);

        if (level != null && pos != null) {
            BlockEntity be = level.getBlockEntity(pos);
            if (be instanceof ChemistryStationBlockEntity chemistryBe) {
                tempBE = chemistryBe;
                inputTemp = chemistryBe.getInputHandler();
                outputTemp = chemistryBe.getOutputHandler();
            }
        }

        this.blockEntity = tempBE;
        this.inputHandler = inputTemp;
        this.outputHandler = outputTemp;

        // --- Slots de la Chemistry Station ---
        this.addSlot(new SlotItemHandler(inputHandler, 0, 15, 15));
        this.addSlot(new SlotItemHandler(inputHandler, 1, 15, 52));
        this.addSlot(new SlotItemHandler(inputHandler, 2, 38, 33));

        this.addSlot(new SlotItemHandler(outputHandler, 0, 145, 39) {
            @Override
            public boolean mayPlace(ItemStack stack) { return false; }

            @Override
            public void onTake(Player player, ItemStack stack) {
                if (blockEntity != null) {
                    blockEntity.craftCurrentRecipe();
                }
                super.onTake(player, stack);
            }
        });

        // --- Slots del inventario del jugador ---
        int startX = 8, startY = 84;
        for (int row = 0; row < 3; ++row) {
            for (int col = 0; col < 9; ++col) {
                this.addSlot(new SlotItemHandler(new InvWrapper(playerInventory), col + row * 9 + 9,
                        startX + col * 18, startY + row * 18));
            }
        }

        // --- Hotbar ---
        int hotbarY = 142;
        for (int col = 0; col < 9; ++col) {
            this.addSlot(new SlotItemHandler(new InvWrapper(playerInventory), col,
                    startX + col * 18, hotbarY));
        }

        // --- DataSlots para sincronizar cookTime ---
        if (blockEntity != null) {
            addDataSlot(new DataSlot() {
                @Override
                public int get() { return blockEntity.getCookTime(); }
                @Override
                public void set(int value) { blockEntity.setCookTime(value); }
            });

            addDataSlot(new DataSlot() {
                @Override
                public int get() { return blockEntity.getCookTimeTotal(); }
                @Override
                public void set(int value) { blockEntity.setCookTimeTotal(value); }
            });
        }
    }

    @Override
    public void slotsChanged(net.minecraft.world.Container inventory) {
        if (blockEntity != null && inputHandler != null && outputHandler != null) {
            ItemStack result = blockEntity.getResultForInputs();
            outputHandler.insertItem(0, result, false);
        }
        broadcastChanges();
    }

    @Override
    public ItemStack quickMoveStack(Player player, int index) {
        return ItemStack.EMPTY; // Mejorable después
    }

    @Override
    public boolean stillValid(Player player) { return true; }

    public int getCookTime() { return blockEntity != null ? blockEntity.getCookTime() : 0; }
    public int getCookTimeTotal() { return blockEntity != null ? blockEntity.getCookTimeTotal() : 100; }

    public IItemHandler getInputHandler() { return inputHandler; }
    public IItemHandler getOutputHandler() { return outputHandler; }
    public ChemistryStationBlockEntity getBlockEntity() { return blockEntity; }
}
