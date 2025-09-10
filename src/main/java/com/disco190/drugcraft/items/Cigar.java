package com.disco190.drugcraft.items;

import com.disco190.drugcraft.sound.ModSounds;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;

public class Cigar extends SmokingItem {
    public Cigar(Item.Properties properties) {
        super(properties.durability(5)); // 5 caladas
    }

}
