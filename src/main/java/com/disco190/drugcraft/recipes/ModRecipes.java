package com.disco190.drugcraft.recipes;

import com.disco190.drugcraft.Drugcraft;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModRecipes {
    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.RECIPE_SERIALIZERS, Drugcraft.MODID);

    public static final RegistryObject<RecipeSerializer<BudInfusionRecipe>> BUD_INFUSION_SERIALIZER =
            SERIALIZERS.register("bud_infusion", BudInfusionSerializer::new);

    public static final RegistryObject<RecipeSerializer<ModularJointRecipe>> MODULAR_JOINT_SERIALIZER =
            SERIALIZERS.register("modular_joint", ModularJointSerializer::new);


    public static void register(IEventBus eventBus) {
        SERIALIZERS.register(eventBus);
    }
}
