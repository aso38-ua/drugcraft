package com.disco190.drugcraft.events;

import com.disco190.drugcraft.Drugcraft;
import com.disco190.drugcraft.blocks.ModBlocks;
import com.disco190.drugcraft.item.ModItems;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.AxeItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.ToolActions;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.level.BlockEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Drugcraft.MODID)
public class ModEvents {

    /**
     * Cubo vacío + clic derecho en caballo = Horse Semen
     */
    @SubscribeEvent
    public static void onEntityInteract(PlayerInteractEvent.EntityInteract event) {
        Player player = event.getEntity();
        Level level = event.getLevel();
        ItemStack held = event.getItemStack();

        if (!level.isClientSide
                && event.getTarget() instanceof Horse
                && held.getItem() == Items.BUCKET) {

            held.shrink(1); // consumir cubo

            ItemStack semen = new ItemStack(ModItems.HORSE_SEMEN.get());
            if (!player.addItem(semen)) {
                player.drop(semen, false);
            }

            event.setCanceled(true);
        }
    }

    /**
     * Al pelar Mimosa Log con hacha → se convierte en Stripped Jungle Log + dropea Mimosa Bark
     */
    @SubscribeEvent
    public static void onBlockToolModification(BlockEvent.BlockToolModificationEvent event) {
        if (event.getToolAction() == ToolActions.AXE_STRIP
                && event.getState().is(ModBlocks.MIMOSA_LOG.get())) {

            // Cambiar el bloque al Jungle Stripped Log
            event.setFinalState(Blocks.STRIPPED_JUNGLE_LOG.defaultBlockState());

            // Dar el bark al jugador
            Player player = event.getPlayer();
            if (player != null && !event.getLevel().isClientSide()) {
                ItemStack bark = new ItemStack(ModItems.MIMOSA_BARK.get());
                if (!player.addItem(bark)) {
                    player.drop(bark, false);
                }
            }
        }
    }
}
