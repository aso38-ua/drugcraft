package com.disco190.drugcraft.items;

import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.List;

public class MorphineItem extends Item {

    private static final String QUALITY_TAG = "Quality";

    public MorphineItem(Properties properties) {
        super(properties);
    }

    public static void setQuality(ItemStack stack, String quality) {
        CompoundTag tag = stack.getOrCreateTag();
        tag.putString(QUALITY_TAG, quality);
    }

    public static String getQuality(ItemStack stack) {
        CompoundTag tag = stack.getTag();
        return tag != null && tag.contains(QUALITY_TAG) ? tag.getString(QUALITY_TAG) : "unknown";
    }

    @Override
    public void appendHoverText(ItemStack stack, @Nullable Level level, List<Component> tooltip, TooltipFlag flag) {
        super.appendHoverText(stack, level, tooltip, flag);

        String quality = getQuality(stack);

        tooltip.add(Component.literal("Quality: " + quality).withStyle(ChatFormatting.AQUA));
    }

}
