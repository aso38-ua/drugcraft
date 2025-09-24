package com.disco190.drugcraft.blocks;

import net.minecraft.core.Direction;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.FlowerBlock;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.core.BlockPos;
import java.util.function.Supplier; // Importa Supplier

public class BlazeKushNaturalBlock extends FlowerBlock {
    public BlazeKushNaturalBlock() {
        // Usa el constructor moderno de FlowerBlock con Suppliers.
        super(() -> MobEffects.MOVEMENT_SPEED, 100, // Estos valores son solo de ejemplo, puedes ajustarlos.
                BlockBehaviour.Properties.copy(Blocks.CRIMSON_FUNGUS) // Copia propiedades de un hongo para que se genere correctamente
                        .instabreak()
                        .noCollission()
                        .offsetType(BlockBehaviour.OffsetType.XZ)
                        .pushReaction(PushReaction.DESTROY)
        );
    }

    // Aquí le decimos dónde puede crecer (soul sand y otros bloques del Nether)
    @Override
    protected boolean mayPlaceOn(BlockState state, BlockGetter world, BlockPos pos) {
        // Ahora se puede plantar en los bloques del Nether especificados
        return state.is(Blocks.NETHERRACK) || state.is(Blocks.SOUL_SAND) || state.is(Blocks.SOUL_SOIL) || state.is(Blocks.CRIMSON_NYLIUM) || state.is(Blocks.WARPED_NYLIUM);
    }

    @Override
    public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
        return 100;
    }

    @Override
    public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
        return 60;
    }
}
