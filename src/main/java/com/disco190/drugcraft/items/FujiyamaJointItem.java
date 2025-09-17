package com.disco190.drugcraft.items;

import com.disco190.drugcraft.effects.ModEffects;
import com.disco190.drugcraft.sound.ModSounds;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import org.joml.Vector3f;


public class FujiyamaJointItem extends Item {

    public FujiyamaJointItem(Properties properties) {
        super(properties.stacksTo(64)); // hasta 16 porros por stack
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW; // animación al fumar
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 32; // duración de cada calada
    }

    @Override
    public void onUseTick(Level world, LivingEntity entity, ItemStack stack, int count) {
        if (world.isClientSide && entity instanceof Player player) {
            int color = stack.hasTag() ? stack.getTag().getInt("Color") : 0x6A0DAD; // morado por defecto
            float r = ((color >> 16) & 0xFF) / 255f;
            float g = ((color >> 8) & 0xFF) / 255f;
            float b = (color & 0xFF) / 255f;

            if (world.random.nextFloat() < 0.3f) {
                double yaw = Math.toRadians(player.getYRot());
                double forwardX = -Math.sin(yaw) * 0.3;
                double forwardZ = Math.cos(yaw) * 0.3;
                boolean rightHand = player.getUsedItemHand() == InteractionHand.MAIN_HAND;
                double sideX = Math.cos(yaw) * 0.2;
                double sideZ = Math.sin(yaw) * 0.2;
                double offsetX = forwardX + (rightHand ? sideX : -sideX);
                double offsetZ = forwardZ + (rightHand ? sideZ : -sideZ);
                double x = player.getX() + offsetX;
                double y = player.getY() + player.getEyeHeight() - 0.2;
                double z = player.getZ() + offsetZ;

                world.addParticle(
                        new DustParticleOptions(new Vector3f(r, g, b), 1.5f),
                        x, y, z,
                        world.random.nextGaussian() * 0.005,
                        0.02,
                        world.random.nextGaussian() * 0.005
                );
            }
        }
    }




    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        ItemStack otherHand = player.getItemInHand(hand == InteractionHand.MAIN_HAND ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND);

        // Comprueba que la otra mano tenga el mechero
        if (otherHand.getItem() != Items.FLINT_AND_STEEL) {
            if (!world.isClientSide) {
                player.displayClientMessage(Component.literal("Necesitas un mechero en la otra mano!"), true);
            }
            return InteractionResultHolder.fail(stack);
        }

        // Sonido al empezar a fumar
        if (!world.isClientSide) {
            SoundEvent sound = ModSounds.JOINT_SMOKE.get();
            world.playSound(null, player.getX(), player.getY(), player.getZ(),
                    sound, player.getSoundSource(), 1.0F, 1.0F);
        }

        // Inicia animación y uso
        player.startUsingItem(hand);
        return InteractionResultHolder.sidedSuccess(stack, world.isClientSide());
    }


    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity entityLiving) {
        if (!world.isClientSide && entityLiving instanceof Player player) {
            // Efectos base del Purple Haze
            player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 1600, 2));
            player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 1200, 1));
            player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 800, 1));
            player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 1200, 1));
            player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 400, 0));
            player.addEffect(new MobEffectInstance(ModEffects.SMOKED.get(), 1600, 0));

            // 🔥 Leer modificadores del NBT
            if (stack.hasTag() && stack.getTag().contains("Modifier")) {
                String modifier = stack.getTag().getString("Modifier");

                switch (modifier) {
                    case "glowing" -> {
                        player.addEffect(new MobEffectInstance(MobEffects.GLOWING, 400, 0));
                    }
                    case "speedy" -> {
                        player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 400, 1));
                    }
                    case "strong" -> {
                        player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 400, 1));
                    }
                    // aquí puedes añadir más modificadores
                }
            }
        }

        // Caladas
        int caladas = stack.getOrCreateTag().getInt("Caladas");
        caladas++;
        stack.getOrCreateTag().putInt("Caladas", caladas);

        if (caladas >= 3) {
            stack.shrink(1);
        }

        return stack;
    }


}
