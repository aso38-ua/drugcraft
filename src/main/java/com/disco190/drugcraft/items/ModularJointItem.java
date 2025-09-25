package com.disco190.drugcraft.items;

import com.disco190.drugcraft.effects.ModEffects;
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
        if (otherHand.getItem() != Items.FLINT_AND_STEEL && otherHand.getItem() != ModItems.IRON_LIGHTER.get() && otherHand.getItem() != ModItems.GOLD_LIGHTER.get() && otherHand.getItem() != ModItems.DIAMOND_LIGHTER.get()) {
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
            else if (this == ModItems.FUJIYAMA_JOINT.get()) setBudType(stack, "fujiyama");
            else if (this == ModItems.BLAZE_KUSH_JOINT.get()) setBudType(stack, "blaze_kush");
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
                    player.addEffect(new MobEffectInstance(ModEffects.SMOKED.get(), 1600, 0));
                }
                case "marijuana" -> {
                    player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 600, 1));
                    player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, 400, 0));
                    player.addEffect(new MobEffectInstance(MobEffects.HUNGER, 600, 0));
                    player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 200, 0));
                    player.addEffect(new MobEffectInstance(ModEffects.SMOKED.get(), 1600, 0));
                }
                case "fujiyama" -> {
                    player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 1600, 2));
                    player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 1200, 1));
                    player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 800, 1));
                    player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 1200, 1));
                    player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 400, 0));
                    player.addEffect(new MobEffectInstance(ModEffects.SMOKED.get(), 1600, 0));
                }

                case "blaze_kush" -> {
                    player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 1600, 0)); // protección fuego
                    player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 200, 0));
                    player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 800, 1));    // regeneración suave
                    player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 400, 0));       // ligero mareo
                    player.addEffect(new MobEffectInstance(ModEffects.SMOKED.get(), 1600, 0));   // tu efecto custom
                }


            }

            // Efectos de modificadores
            // -------------------- Efectos de modificadores --------------------
            switch (getModifier(stack)) {
                case "glowing" -> player.addEffect(new MobEffectInstance(MobEffects.GLOWING, 600, 0));
                case "speedy" -> player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 600, 1));
                case "strong" -> player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 600, 1));
                case "regenerative" -> player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 300, 1));
                case "infernal" -> {
                    player.addEffect(new MobEffectInstance(MobEffects.FIRE_RESISTANCE, 1200, 0));
                    player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 600, 0));
                }
                case "resistant" -> player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 800, 1));
                case "warped" -> player.addEffect(new MobEffectInstance(MobEffects.NIGHT_VISION, 800, 0));
                case "dreamy" -> {
                    player.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 1200, 0));
                    player.addEffect(new MobEffectInstance(MobEffects.CONFUSION, 400, 0));
                }
                case "warping" -> {
                    player.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 200, 0));
                    player.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SPEED, 400, 2));
                }
                case "chaotic" -> {
                    // Aleatorio: cada vez aplica un efecto diferente
                    int roll = world.random.nextInt(16);
                    switch (roll) {
                        case 0 -> player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_BOOST, 600, 2));
                        case 1 -> player.addEffect(new MobEffectInstance(MobEffects.WEAKNESS, 600, 1));
                        case 2 -> player.addEffect(new MobEffectInstance(MobEffects.REGENERATION, 600, 2));
                        case 3 -> player.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 600, 1));
                        case 4 -> player.addEffect(new MobEffectInstance(MobEffects.WATER_BREATHING, 600, 0));
                        case 5 -> player.addEffect(new MobEffectInstance(MobEffects.BAD_OMEN, 600, 0));
                        case 6 -> player.addEffect(new MobEffectInstance(MobEffects.LUCK, 600, 1));
                        case 7 -> player.addEffect(new MobEffectInstance(MobEffects.UNLUCK, 600, 1));
                        case 8 -> player.addEffect(new MobEffectInstance(MobEffects.INVISIBILITY, 600, 0));
                        case 9 -> player.addEffect(new MobEffectInstance(MobEffects.JUMP, 600, 2));
                        case 10 -> player.addEffect(new MobEffectInstance(MobEffects.DIG_SPEED, 600, 2));
                        case 11 -> player.addEffect(new MobEffectInstance(MobEffects.WITHER, 600, 1));
                        case 12 -> player.addEffect(new MobEffectInstance(MobEffects.SLOW_FALLING, 600, 0));
                        case 13 -> player.addEffect(new MobEffectInstance(MobEffects.SATURATION, 600, 2));
                        case 14 -> player.addEffect(new MobEffectInstance(MobEffects.POISON, 600, 1));
                        case 15 -> player.addEffect(new MobEffectInstance(MobEffects.CONDUIT_POWER, 600, 1));
                    }
                }
            }


            stack.shrink(1);
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

    public static int getDefaultColor(String budType) {
        return switch (budType) {
            case "purple_haze" -> 0x6A0DAD; // morado
            case "marijuana"   -> 0x808080; // gris
            case "blaze_kush"  -> 0xFF6600; // naranja fuego
            case "fujiyama"    -> 0x87CEEB; // azul celeste
            default            -> 0x808080; // fallback gris
        };
    }


    public static int getColor(ItemStack stack) {
        if (stack.hasTag() && stack.getTag().contains("Color")) {
            return stack.getTag().getInt("Color");
        }
        String budType = getBudType(stack);
        return getDefaultColor(budType);
    }


    @Override
    public ItemStack getDefaultInstance() {
        ItemStack stack = super.getDefaultInstance();
        if (getBudType(stack).isEmpty()) {
            if (this == ModItems.PURPLE_HAZE_JOINT.get()) setBudType(stack, "purple_haze");
            else if (this == ModItems.CANNABIS_JOINT.get()) setBudType(stack, "marijuana");
            else if (this == ModItems.FUJIYAMA_JOINT.get()) setBudType(stack, "fujiyama");
            else if (this == ModItems.BLAZE_KUSH_JOINT.get()) setBudType(stack, "blaze_kush");
        }
        // aplicar color por defecto del tipo
        String budType = getBudType(stack);
        stack.getOrCreateTag().putInt("Color", getDefaultColor(budType));
        return stack;
    }


}
