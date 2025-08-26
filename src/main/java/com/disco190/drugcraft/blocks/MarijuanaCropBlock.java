package com.disco190.drugcraft.blocks;

import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

import java.util.function.Supplier;

public class MarijuanaCropBlock extends CropBlock {

    // Campo privado para guardar la semilla
    private final Supplier<Item> seedSupplier;

    // Constructor recibe un Supplier<Item>
    public MarijuanaCropBlock(Supplier<Item> seedSupplier) {
        super(BlockBehaviour.Properties.copy(Blocks.WHEAT));
        this.seedSupplier = seedSupplier;
    }

    @Override
    protected Item getBaseSeedId() {
        return seedSupplier.get();
    }
}
