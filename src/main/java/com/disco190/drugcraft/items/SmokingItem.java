package com.disco190.drugcraft.items;

import com.disco190.drugcraft.Drugcraft;
import com.disco190.drugcraft.item.ModItems;
import com.disco190.drugcraft.sound.ModSounds;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.UseAnim;
import net.minecraft.world.level.Level;
import org.joml.Vector3f;

public class SmokingItem extends Item {
    public SmokingItem(Item.Properties properties) {
        super(properties);
    }

    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW; // animación al fumar
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 32; // duración de cada calada
    }

    // -------------------- Partículas --------------------
    @Override
    public void onUseTick(Level world, LivingEntity entity, ItemStack stack, int count) {
        if (!world.isClientSide || !(entity instanceof Player player)) return;

        // Obtener color del bud
        int color = 0x4B4B4B;
        float r = ((color >> 16) & 0xFF) / 255f;
        float g = ((color >> 8) & 0xFF) / 255f;
        float b = (color & 0xFF) / 255f;

        // Probabilidad de generar partícula
        if (world.random.nextFloat() >= 0.3f) return;

        // Determinar mano que sostiene el porro
        InteractionHand porroHand = player.getMainHandItem() == stack ? InteractionHand.MAIN_HAND : InteractionHand.OFF_HAND;
        boolean isRight = porroHand == InteractionHand.MAIN_HAND;

        // Calcular posición de la punta del porro
        double yaw = Math.toRadians(player.getYRot());
        double pitch = Math.toRadians(player.getXRot());

        // Offset lateral y hacia delante desde la mano
        double forward = 0.25; // distancia delante de la mano
        double lateral = isRight ? 0.1 : -0.1;
        double x = player.getX() - Math.sin(yaw) * forward + Math.cos(yaw) * lateral;
        double y = player.getY() + player.getEyeHeight() - 0.1 + world.random.nextDouble() * 0.2;
        double z = player.getZ() + Math.cos(yaw) * forward + Math.sin(yaw) * lateral;

        // Velocidad de subida del humo
        double velocityY = 0.03 + world.random.nextDouble() * 0.04;

        // Crear partícula con color del bud
        world.addParticle(
                new DustParticleOptions(new Vector3f(r, g, b), 1.5f),
                x, y, z,
                world.random.nextGaussian() * 0.005,
                velocityY,
                world.random.nextGaussian() * 0.005
        );
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {

        ItemStack stack = player.getItemInHand(hand);
        //if (stack.getDamageValue() == 0) {              //Solo necesitas mechero la primera calada
            ItemStack otherHand = player.getItemInHand(hand == InteractionHand.MAIN_HAND ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND);

            if (otherHand.getItem() != Items.FLINT_AND_STEEL && otherHand.getItem() != ModItems.IRON_LIGHTER.get() && otherHand.getItem() != ModItems.GOLD_LIGHTER.get() && otherHand.getItem() != ModItems.DIAMOND_LIGHTER.get()) {
                if (!world.isClientSide) {
                    player.displayClientMessage(Component.literal("Necesitas un mechero en la otra mano!"), true);
                }
                return InteractionResultHolder.fail(stack);
            }
        //}

        if (!world.isClientSide) {
            SoundEvent sound = ModSounds.JOINT_SMOKE.get();
            world.playSound(null, player.getX(), player.getY(), player.getZ(),
                    sound, player.getSoundSource(), 1.0F, 1.0F);
        }
        else {
            for (int i = 0; i < 5; i++) { // cantidad de humo (ajusta este número)
                double offsetX = (world.random.nextDouble() - 0.5) * 0.2;
                double offsetY = world.random.nextDouble() * 0.1 + 0.5;
                double offsetZ = (world.random.nextDouble() - 0.5) * 0.2;

                world.addParticle(
                        ParticleTypes.CAMPFIRE_SIGNAL_SMOKE, // o CAMPFIRE_SIGNAL_SMOKE
                        player.getX(),
                        player.getY() + 1.5, // altura boca
                        player.getZ(),
                        offsetX, offsetY, offsetZ
                );
            }
        }


        player.startUsingItem(hand);
        return InteractionResultHolder.sidedSuccess(stack, world.isClientSide());
    }



}
