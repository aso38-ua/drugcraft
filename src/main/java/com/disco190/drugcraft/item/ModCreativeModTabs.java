package com.disco190.drugcraft.item;

import com.disco190.drugcraft.Drugcraft;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, Drugcraft.MODID);

    public static final RegistryObject<CreativeModeTab> DRUGCRAFT_TAB = CREATIVE_MODE_TABS.register("ganjacraft_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.MARIJUANA.get()))
                    .title(Component.translatable("creativetab.drugcraft_tab"))
                    .displayItems((itemDisplayParameters, pOutput) -> {
                        pOutput.accept(ModItems.MARIJUANA.get());
                        pOutput.accept(ModItems.MARIJUANA_SEEDS.get());
                        pOutput.accept(ModItems.CANNABIS_JOINT.get());
                        pOutput.accept(ModItems.TOBACCO_SEEDS.get());
                        pOutput.accept(ModItems.VOLADO_LEAF.get());
                        pOutput.accept(ModItems.MOOSHROOMS.get());
                        pOutput.accept(ModItems.MOOSHROOMS_SEEDS.get());
                        pOutput.accept(ModItems.EPHEDRA_BERRIES.get());
                        pOutput.accept(ModItems.COUGH_SYRUP.get());
                        pOutput.accept(ModItems.CANDY.get());
                        pOutput.accept(ModItems.REFRESHMENT.get());

                    })
                    .build());



    public static void register(IEventBus eventBus){
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
