package com.disco190.drugcraft.registry;

import com.disco190.drugcraft.Drugcraft;
import com.disco190.drugcraft.menu.ChemistryStationMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.common.extensions.IForgeMenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModMenuTypes {
    public static final DeferredRegister<MenuType<?>> MENU_TYPES =
            DeferredRegister.create(ForgeRegistries.MENU_TYPES, Drugcraft.MODID);

    public static final RegistryObject<MenuType<ChemistryStationMenu>> CHEMISTRY_STATION =
            MENU_TYPES.register("chemistry_station",
                    () -> IForgeMenuType.create(ChemistryStationMenu::new));

}
