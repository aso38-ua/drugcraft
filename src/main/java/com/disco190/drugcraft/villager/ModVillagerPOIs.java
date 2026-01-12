package com.disco190.drugcraft.villager;

import com.disco190.drugcraft.Drugcraft;
import com.disco190.drugcraft.blocks.ModBlocks;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;

public class ModVillagerPOIs {

    public static final DeferredRegister<PoiType> POI_TYPES =
            DeferredRegister.create(ForgeRegistries.POI_TYPES, Drugcraft.MODID);

    public static final RegistryObject<PoiType> DEALER_POI =
            POI_TYPES.register("dealer_poi",
                    () -> new PoiType(
                            Set.copyOf(
                                    ModBlocks.CHEMISTRY_STATION.get()
                                            .getStateDefinition()
                                            .getPossibleStates()
                            ),
                            1,  // ticketCount
                            1   // searchDistance
                    )
            );

    // NO necesitas registerBlockStates()
}
