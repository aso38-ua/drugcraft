package com.disco190.drugcraft.sound;

import com.disco190.drugcraft.Drugcraft;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.util.ForgeSoundType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;
import net.minecraftforge.eventbus.api.IEventBus;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, Drugcraft.MODID);

    public static final RegistryObject<SoundEvent> JOINT_SMOKE =
            registerSoundEvents("joint_smoke");


    private static RegistryObject<SoundEvent> registerSoundEvents(String jointSmoke) {
        return SOUND_EVENTS.register(jointSmoke, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(Drugcraft.MODID, jointSmoke)));
    }


    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}
