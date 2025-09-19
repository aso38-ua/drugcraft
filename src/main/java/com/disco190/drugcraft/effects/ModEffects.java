package com.disco190.drugcraft.effects;

import com.disco190.drugcraft.Drugcraft;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModEffects {

    public static final DeferredRegister<MobEffect> EFFECTS =
            DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, Drugcraft.MODID);

    public static final RegistryObject<MobEffect> HALLUCINATION =
            EFFECTS.register("hallucination", HallucinationEffect::new);

    public static void register(IEventBus bus) {
        EFFECTS.register(bus);
    }

    public static final RegistryObject<MobEffect> SMOKED = EFFECTS.register("smoked", SmokedEffect::new);

}
