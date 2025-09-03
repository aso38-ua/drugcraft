package com.disco190.drugcraft.events;

import com.disco190.drugcraft.Drugcraft;
import com.disco190.drugcraft.item.ModItems;
import net.minecraft.world.entity.animal.horse.Horse;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Drugcraft.MODID)
public class ModEvents {

    @SubscribeEvent
    public static void onEntityInteract(PlayerInteractEvent.EntityInteract event) {
        Player player = event.getEntity();
        Level level = event.getLevel();
        ItemStack held = event.getItemStack();

        // Si hace clic con cubo vacío sobre un caballo
        if (!level.isClientSide
                && event.getTarget() instanceof Horse
                && held.getItem() == Items.BUCKET) {

            // consumir cubo
            held.shrink(1);

            // dar el ítem nuevo
            ItemStack semen = new ItemStack(ModItems.HORSE_SEMEN.get());
            if (!player.addItem(semen)) {
                player.drop(semen, false);
            }

            event.setCanceled(true);
        }
    }
/*
    private static final Random random = new Random();

    @SubscribeEvent
    public static void onBlockBreak(BlockEvent.BreakEvent event) {
        // Solo en servidor y asegurando que sea Level
        if (!(event.getLevel() instanceof Level level)) return;
        if (level.isClientSide) return;

        // Solo para hierba normal
        if (event.getState().getBlock() == Blocks.GRASS) {

            // Comprobar Silk Touch
            boolean hasSilkTouch = event.getPlayer() != null &&
                    event.getPlayer().getMainHandItem().getEnchantmentLevel(Enchantments.SILK_TOUCH) > 0;

            if (hasSilkTouch) return; // no dropear nada si Silk Touch

            // Probabilidad de drop tipo semillas de trigo
            double chance = 0.05; // 5%
            if (random.nextDouble() < chance) {
                ItemStack seedStack = new ItemStack(ModItems.MARIJUANA_SEEDS.get(), 1);

                // Comprobar Fortune
                int fortune = 0;
                if (event.getPlayer() != null)
                    fortune = event.getPlayer().getMainHandItem().getEnchantmentLevel(Enchantments.BLOCK_FORTUNE);

                int extra = random.nextInt(fortune + 1); // aumentar cantidad según Fortune
                seedStack.grow(extra);

                // Crear el drop en el mundo
                ItemEntity drop = new ItemEntity(
                        level,
                        event.getPos().getX() + 0.5,
                        event.getPos().getY() + 0.5,
                        event.getPos().getZ() + 0.5,
                        seedStack
                );
                level.addFreshEntity(drop);
            }
        }
    }*/
}
