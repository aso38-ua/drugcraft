package com.disco190.drugcraft.item;

import com.disco190.drugcraft.Drugcraft;
import com.disco190.drugcraft.blocks.ModBlocks;
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
                        pOutput.accept(ModItems.PURPLE_HAZE.get());
                        pOutput.accept(ModItems.MARIJUANA_SEEDS.get());
                        pOutput.accept(ModItems.PURPLE_HAZE_SEEDS.get());
                        pOutput.accept(ModItems.CANNABIS_JOINT.get());
                        pOutput.accept(ModItems.PURPLE_HAZE_JOINT.get());
                        pOutput.accept(ModItems.TOBACCO_SEEDS.get());
                        pOutput.accept(ModItems.TRIPA.get());
                        pOutput.accept(ModItems.CAPOTE.get());
                        pOutput.accept(ModItems.CAPA.get());
                        pOutput.accept(ModItems.CURED_TRIPA.get());
                        pOutput.accept(ModItems.CURED_CAPOTE.get());
                        pOutput.accept(ModItems.CURED_CAPA.get());

                        pOutput.accept(ModItems.CIGARETTE.get());
                        pOutput.accept(ModItems.MALMORO_CIGARETTE.get());
                        pOutput.accept(ModItems.MALMORO_GOLD_CIGARETTE.get());
                        pOutput.accept(ModItems.CAMELLO_CIGARETTE.get());
                        pOutput.accept(ModItems.LUCKY_STROKE_CIGARETTE.get());
                        pOutput.accept(ModItems.HORIZONTE_CIGARETTE.get());

                        pOutput.accept(ModItems.CARDBOARD.get());
                        pOutput.accept(ModItems.ROLLING_TOBACCO.get());
                        pOutput.accept(ModItems.ROLLING_CIGARETTE.get());
                        pOutput.accept(ModItems.WOOD_PIPE.get());
                        pOutput.accept(ModItems.WISDOM_PIPE.get());
                        pOutput.accept(ModItems.LOADED_WOOD_PIPE.get());
                        pOutput.accept(ModItems.LOADED_WISDOM_PIPE.get());

                        pOutput.accept(ModItems.PACK_OF_CIGARETTES.get());
                        pOutput.accept(ModItems.PACK_OF_MALMORO.get());
                        pOutput.accept(ModItems.PACK_OF_MALMORO_GOLD.get());
                        pOutput.accept(ModItems.PACK_OF_CAMELLO.get());
                        pOutput.accept(ModItems.PACK_OF_LUCKY_STROKE.get());
                        pOutput.accept(ModItems.PACK_OF_HORIZONTE.get());

                        pOutput.accept(ModItems.ESPLENDIDO_CIGAR.get());
                        pOutput.accept(ModItems.DON_JAVIER_CIGAR.get());
                        pOutput.accept(ModItems.TORITO_CIGAR.get());

                        pOutput.accept(ModItems.MOOSHROOMS.get());
                        pOutput.accept(ModItems.MOOSHROOMS_SEEDS.get());
                        pOutput.accept(ModItems.EPHEDRA_BERRIES.get());
                        pOutput.accept(ModItems.COUGH_SYRUP.get());
                        pOutput.accept(ModItems.CANDY.get());
                        pOutput.accept(ModItems.REFRESHMENT.get());
                        pOutput.accept(ModItems.LEAN.get());

                        pOutput.accept(ModItems.HORSE_SEMEN.get());

                    })
                    .build());



    public static void register(IEventBus eventBus){
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
