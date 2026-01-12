package com.disco190.drugcraft.villager;

import com.disco190.drugcraft.Drugcraft;
import com.google.common.collect.ImmutableSet;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModVillagerProfessions {

    public static final DeferredRegister<VillagerProfession> PROFESSIONS =
            DeferredRegister.create(ForgeRegistries.VILLAGER_PROFESSIONS, Drugcraft.MODID);

    public static final RegistryObject<VillagerProfession> DEALER =
            PROFESSIONS.register("dealer",
                    () -> new VillagerProfession(
                            "dealer",
                            holder -> holder.value() == ModVillagerPOIs.DEALER_POI.get(),
                            holder -> holder.value() == ModVillagerPOIs.DEALER_POI.get(),
                            ImmutableSet.of(),
                            ImmutableSet.of(),
                            SoundEvents.VILLAGER_WORK_CLERIC
                    )
            );
}


