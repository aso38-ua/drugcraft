package com.disco190.drugcraft.blocks;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;

import java.util.function.Supplier;

public class MooshroomsCropBlock extends CropBlock {

    // Campo privado para guardar la semilla
    private final Supplier<Item> seedSupplier;

    // Constructor recibe un Supplier<Item>
    public MooshroomsCropBlock(Supplier<Item> seedSupplier) {
        super(Properties.copy(Blocks.WHEAT));
        this.seedSupplier = seedSupplier;
    }

    @Override
    protected Item getBaseSeedId() {
        return seedSupplier.get();
    }

    @Override
    public int getMaxAge() {
        return 5;
    }
}
