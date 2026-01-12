package com.disco190.drugcraft.villager;

import com.disco190.drugcraft.item.ModItems;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.MerchantOffer;

public class DealerTrades {

    public static void register() {
        VillagerTrades.TRADES.put(
                ModVillagerProfessions.DEALER.get(),
                createTrades()
        );
    }

    private static Int2ObjectMap<VillagerTrades.ItemListing[]> createTrades() {
        Int2ObjectMap<VillagerTrades.ItemListing[]> trades = new Int2ObjectOpenHashMap<>();

        // ===== NIVEL 1 – MARIHUANA =====
        trades.put(1, new VillagerTrades.ItemListing[]{
                buy(ModItems.MARIJUANA.get(), 8, 1),
                buy(ModItems.PURPLE_HAZE.get(), 6, 1),
                buy(ModItems.BLAZE_KUSH.get(), 4, 1)
        });

        // ===== NIVEL 2 – PSICODÉLICOS =====
        trades.put(2, new VillagerTrades.ItemListing[]{
                buy(ModItems.MOOSHROOMS.get(), 6, 1),
                buy(ModItems.PEYOTE.get(), 3, 1),
                buy(ModItems.AYAHUASCA.get(), 2, 2)
        });

        // ===== NIVEL 3 – LEAN =====
        trades.put(3, new VillagerTrades.ItemListing[]{
                buy(ModItems.LEAN.get(), 2, 2)
        });

        // ===== NIVEL 4 – METH =====
        trades.put(4, new VillagerTrades.ItemListing[]{
                buy(ModItems.METH.get(), 1, 3),
                buy(ModItems.BLUE_METH.get(), 1, 5)
        });

        // ===== NIVEL 5 – HARD =====
        trades.put(5, new VillagerTrades.ItemListing[]{
                buy(ModItems.MORPHINE.get(), 1, 6),
                buy(ModItems.DMT.get(), 1, 8)
        });

        return trades;
    }

    private static VillagerTrades.ItemListing buy(Item item, int count, int emeralds) {
        return (trader, rand) -> new MerchantOffer(
                new ItemStack(item, count),
                new ItemStack(Items.EMERALD, emeralds),
                12,   // usos
                8,    // XP al aldeano
                0.05F // inflación
        );
    }
}