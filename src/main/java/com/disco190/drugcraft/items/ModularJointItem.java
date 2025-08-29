package com.disco190.drugcraft.items;

import com.disco190.drugcraft.item.ModItems;
import com.disco190.drugcraft.sound.ModSounds;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.level.Level;
import net.minecraft.core.particles.DustParticleOptions;
import net.minecraft.ChatFormatting;
import org.joml.Vector3f;

import javax.annotation.Nullable;
import java.util.List;

public class ModularJointItem extends Item {

    public ModularJointItem(Properties properties) {
        super(properties.stacksTo(64));
    }

    // -------------------- Animación y duración --------------------
    @Override
    public UseAnim getUseAnimation(ItemStack stack) {
        return UseAnim.BOW;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        return 32;
    }

    // -------------------- Partículas --------------------
    @Override
    public void onUseTick(Level world, LivingEntity entity, ItemStack stack, int count) {
        if (!world.isClientSide || !(entity instanceof Player player)) return;

        // Obtener color del bud
        int color = getColor(stack);
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



    // -------------------- Uso del porro --------------------
    @Override
    public InteractionResultHolder<ItemStack> use(Level world, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        ItemStack otherHand = player.getItemInHand(hand == InteractionHand.MAIN_HAND ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND);

        // Comprobar mechero
        if (otherHand.getItem() != Items.FLINT_AND_STEEL) {
            if (!world.isClientSide)
                player.displayClientMessage(Component.literal("Necesitas un mechero en la otra mano!"), true);
            return InteractionResultHolder.fail(stack);
        }

        if (!world.isClientSide) {
            SoundEvent sound = ModSounds.JOINT_SMOKE.get();
            world.playSound(null, player.getX(), player.getY(), player.getZ(),
                    sound, player.getSoundSource(), 1.0F, 1.0F);
        }

        // Garantizar BudType y Color al iniciar el uso
        if (getBudType(stack).isEmpty()) {
            if (this == ModItems.PURPLE_HAZE_JOINT.get()) setBudType(stack, "purple_haze");
            else if (this == ModItems.CANNABIS_JOINT.get()) setBudType(stack, "marijuana");
        }
        if (getColor(stack) == 0) stack.getOrCreateTag().putInt("Color", 0x6A0DAD);

        player.startUsingItem(hand);
        return InteractionResultHolder.sidedSuccess(stack, world.isClientSide());
    }

    // -------------------- Efectos al fumar --------------------
    @Override
    public ItemStack finishUsingItem(ItemStack stack, Level world, LivingEntity entityLiving) {
        if (!world.isClientSide && entityLiving instanceof Player player) {
            int caladas = stack.getOrCreateTag().getInt("Caladas") + 1;
            stack.getOrCreateTag().putInt("Caladas", caladas);

            // Efectos base según BudType
            switch (getBudType(stack)) {
                case "purple_haze" -> {
                    player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 600, 0));
                    player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 200, 0));
                    player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 400, 0));
                    player.addEffect(new MobEffectInstance(MobEffects.ABSORPTION, 600, 0));
                }
                case "marijuana" -> {
                    player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 400, 1));
                    player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 200, 0));
                    player.addEffect(new MobEffectInstance(MobEffects.HUNGER, 600, 0));
                    player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 200, 0));
                }
            }

            // Efectos de modificadores
            switch (getModifier(stack)) {
                case "glowing" -> player.addEffect(new MobEffectInstance(MobEffects.GLOWING, 400, 0));
                case "speedy" -> player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 400, 1));
                case "strong" -> player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 400, 1));
            }

            if (caladas >= 3) stack.shrink(1);
        }
        return stack;
    }

    // -------------------- Tooltip --------------------
    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        String type = getBudType(stack);
        String mod = getModifier(stack);

        tooltip.add(Component.literal(mod.isEmpty() ? "Sin modificar" : "Infusionado con " + mod)
                .withStyle(mod.isEmpty() ? ChatFormatting.GRAY : ChatFormatting.LIGHT_PURPLE));
    }

    // -------------------- Helpers NBT --------------------
    public static void setModifier(ItemStack stack, String modifierId, int color) {
        stack.getOrCreateTag().putString("Modifier", modifierId);
        stack.getOrCreateTag().putInt("Color", color);
    }

    public static void setBudType(ItemStack stack, String type) {
        stack.getOrCreateTag().putString("BudType", type);
    }

    public static String getModifier(ItemStack stack) {
        return stack.hasTag() ? stack.getTag().getString("Modifier") : "";
    }

    public static String getBudType(ItemStack stack) {
        return stack.hasTag() ? stack.getTag().getString("BudType") : "";
    }

    public static int getColor(ItemStack stack) {
        return stack.hasTag() ? stack.getTag().getInt("Color") : 0x6A0DAD;
    }

    @Override
    public ItemStack getDefaultInstance() {
        ItemStack stack = super.getDefaultInstance();
        if (getBudType(stack).isEmpty()) {
            if (this == ModItems.PURPLE_HAZE_JOINT.get()) setBudType(stack, "purple_haze");
            else if (this == ModItems.CANNABIS_JOINT.get()) setBudType(stack, "marijuana");
        }
        if (getColor(stack) == 0) stack.getOrCreateTag().putInt("Color", 0x6A0DAD);
        return stack;
    }

}
