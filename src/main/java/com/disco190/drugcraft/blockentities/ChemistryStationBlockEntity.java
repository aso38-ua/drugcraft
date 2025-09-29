package com.disco190.drugcraft.blockentities;

import com.disco190.drugcraft.items.LiquidMethItem;
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
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.items.ItemStackHandler;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Random;

public class ChemistryStationBlockEntity extends BlockEntity implements net.minecraft.world.MenuProvider {

    private final ItemStackHandler input = new ItemStackHandler(3) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
            // Si cambian inputs, reiniciamos cocinado y estado guardado (ya no válido)
            cookTime = 0;
            currentResult = ItemStack.EMPTY;
        }
    };

    private final ItemStackHandler output = new ItemStackHandler(1) {
        @Override
        protected void onContentsChanged(int slot) {
            setChanged();
        }
    };

    private int cookTime = 0;
    private int cookTimeTotal = 400;
    private ItemStack currentResult = ItemStack.EMPTY; // resultado fijado para la tanda actual (incluye pureza)

    public ChemistryStationBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.CHEMISTRY_STATION.get(), pos, state);
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.put("Input", input.serializeNBT());
        tag.put("Output", output.serializeNBT());
        tag.putInt("CookTime", cookTime);
        if (!currentResult.isEmpty()) tag.put("CurrentResult", currentResult.serializeNBT());
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        input.deserializeNBT(tag.getCompound("Input"));
        output.deserializeNBT(tag.getCompound("Output"));
        cookTime = tag.getInt("CookTime");
        if (tag.contains("CurrentResult")) {
            currentResult = ItemStack.of(tag.getCompound("CurrentResult"));
        } else {
            currentResult = ItemStack.EMPTY;
        }
    }

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

    // -----------------------
    // Helpers
    // -----------------------
    private static boolean sameItemAndQuality(ItemStack a, ItemStack b) {
        if (a.isEmpty() || b.isEmpty()) return false;
        if (a.getItem() != b.getItem()) return false;
        // Compare "Quality" tag (default "low" if missing)
        String qa = LiquidMethItem.getQuality(a);
        String qb = LiquidMethItem.getQuality(b);
        return qa.equals(qb);
    }

    // Calcula (y guarda) el currentResult — si ya existe lo devuelve directamente
    public ItemStack computeOrGetCurrentResult() {
        // Si ya tenemos resultado fijado para la tanda, devolver copia
        if (!currentResult.isEmpty()) return currentResult.copy();

        // Comprueba receta base para los inputs
        List<ItemStack> inputs = List.of(
                input.getStackInSlot(0),
                input.getStackInSlot(0),
                input.getStackInSlot(1),
                input.getStackInSlot(2)
        );
        ItemStack base = ChemistryStationRecipes.getResult(inputs);
        if (base.isEmpty()) return ItemStack.EMPTY;

        // Si es LiquidMethItem, decide pureza:
        if (base.getItem() instanceof LiquidMethItem) {
            ItemStack out = output.getStackInSlot(0);
            int purity;
            if (!out.isEmpty() && sameItemAndQuality(out, base)) {
                // Si en la salida hay el mismo item con la misma calidad, reutilizamos SU pureza
                purity = LiquidMethItem.getPurity(out);
            } else {
                // Sino, generamos pureza aleatoria según calidad
                String quality = LiquidMethItem.getQuality(base);
                Random rand = new Random();
                purity = switch (quality) {
                    case "very_high" -> 90 + rand.nextInt(11);
                    case "high" -> 70 + rand.nextInt(21);
                    case "medium" -> 40 + rand.nextInt(30);
                    case "low" -> 10 + rand.nextInt(31);
                    case "burnt" -> rand.nextInt(10);
                    default -> 0;
                };
            }
            LiquidMethItem.setPurity(base, purity);
        }

        // Guardamos currentResult (con pureza ya fijada) y devolvemos
        currentResult = base.copy();
        return currentResult.copy();
    }

    private void consumeInputs() {
        for (int i = 0; i < input.getSlots(); i++) {
            input.extractItem(i, 1, false);
        }
    }

    // -----------------------
    // Tick
    // -----------------------
    public static void tick(Level level, BlockPos pos, BlockState state, ChemistryStationBlockEntity be) {
        if (be == null) return;

        ItemStack outputSlot = be.output.getStackInSlot(0);
        ItemStack computed = be.computeOrGetCurrentResult();

        // Si no hay receta válida, resetear
        if (computed.isEmpty()) {
            if (be.cookTime != 0 || !be.currentResult.isEmpty()) {
                be.cookTime = 0;
                be.currentResult = ItemStack.EMPTY;
                be.setChanged();
            }
            return;
        }

        // Solo cocinar si la salida está vacía o es apilable con la misma calidad
        boolean outputAccepts = outputSlot.isEmpty() || sameItemAndQuality(outputSlot, computed);
        if (!outputAccepts) {
            // no podemos poner resultado en la salida (ocupada por otro item/quality), reiniciar cook
            if (be.cookTime != 0) {
                be.cookTime = 0;
                be.setChanged();
            }
            return;
        }

        // Incrementa tiempo de cocinado
        be.cookTime++;
        if (be.cookTime == 1) {
            level.playSound(null, pos,
                    net.minecraft.sounds.SoundEvents.BREWING_STAND_BREW,
                    net.minecraft.sounds.SoundSource.BLOCKS,
                    1.0F, 1.0F);
        }

        if (level.isClientSide) {
            double x = pos.getX() + 0.5;
            double y = pos.getY() + 1.1;
            double z = pos.getZ() + 0.5;
            level.addParticle(net.minecraft.core.particles.ParticleTypes.SMOKE, x, y, z, 0, 0.05, 0);
        }

        // Cuando termina:
        if (be.cookTime >= be.cookTimeTotal) {
            be.cookTime = 0;

            // Recompute para estar seguros (pero currentResult ya contiene la pureza fijada)
            ItemStack toAdd = be.currentResult.copy();

            if (outputSlot.isEmpty()) {
                // ponemos directamente
                be.output.setStackInSlot(0, toAdd);
                be.consumeInputs();
            } else if (sameItemAndQuality(outputSlot, toAdd)) {
                // apilamos: respetamos pureza del output (ya lo hicimos cuando generamos currentResult)
                int space = outputSlot.getMaxStackSize() - outputSlot.getCount();
                if (space > 0) {
                    int add = Math.min(toAdd.getCount(), space);
                    ItemStack newOut = outputSlot.copy();
                    newOut.grow(add);
                    be.output.setStackInSlot(0, newOut);
                    be.consumeInputs();
                }
            }

            // Reset estado de tanda para la próxima ejecución
            be.currentResult = ItemStack.EMPTY;
            be.setChanged();
        }
    }

    public void dropContents() {
        if (level == null || level.isClientSide) return;

        for (int i = 0; i < input.getSlots(); i++) {
            ItemStack stack = input.getStackInSlot(i);
            if (!stack.isEmpty()) {
                Block.popResource(level, worldPosition, stack);
                input.setStackInSlot(i, ItemStack.EMPTY);
            }
        }

        for (int i = 0; i < output.getSlots(); i++) {
            ItemStack stack = output.getStackInSlot(i);
            if (!stack.isEmpty()) {
                Block.popResource(level, worldPosition, stack);
                output.setStackInSlot(i, ItemStack.EMPTY);
            }
        }
    }

    // (opcional) mantenemos el método si alguna GUI lo llama, pero NO debe forzar el crafteo si cookTime < cookTimeTotal
    public void craftCurrentRecipeIfReady() {
        if (cookTime < cookTimeTotal) return;
        // El tick hace ya el trabajo; esta función la dejamos para compatibilidad (no hace nada extra).
    }

    public void setCookTime(int value) { this.cookTime = value; }
    public void setCookTimeTotal(int value) { this.cookTimeTotal = value; }
}
