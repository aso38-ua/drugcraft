package com.disco190.drugcraft.items;

import com.disco190.drugcraft.item.ModItems;
import com.disco190.drugcraft.sound.ModSounds;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class Pipe extends SmokingItem {
    public Pipe(Item.Properties properties) {
        super(properties.durability(4)); // 4 caladas
    }
}
