package com.disco190.drugcraft.items;

import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class ModularBudItem extends Item {

    public ModularBudItem(Properties props) {
        super(props);
    }

    // Guardamos un efecto y color dentro del NBT
    public static void setModifier(ItemStack stack, String modifierId, int color) {
        stack.getOrCreateTag().putString("Modifier", modifierId);
        stack.getOrCreateTag().putInt("Color", color);
    }

    public static String getModifier(ItemStack stack) {
        return stack.hasTag() ? stack.getTag().getString("Modifier") : "none";
    }

    public static int getColor(ItemStack stack) {
        return stack.hasTag() ? stack.getTag().getInt("Color") : 0x6A0DAD; // morado base
    }

    public static String getBudType(ItemStack bud) {
        if (bud.isEmpty()) return "marijuana";

        var key = bud.getItem().builtInRegistryHolder().key().location();
        if (key != null) {
            return key.getPath(); // ej: "purple_haze_bud"
        }
        return "marijuana";
    }


    public static void setBudType(ItemStack bud, String budType) {
        bud.getOrCreateTag().putString("BudType", budType);
    }


    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        String mod = getModifier(stack);
        if (!"none".equals(mod)) {
            tooltip.add(Component.literal("Infusionado con " + mod).withStyle(ChatFormatting.LIGHT_PURPLE));
        } else {
            tooltip.add(Component.literal("Sin modificar").withStyle(ChatFormatting.GRAY));
        }
    }
}
