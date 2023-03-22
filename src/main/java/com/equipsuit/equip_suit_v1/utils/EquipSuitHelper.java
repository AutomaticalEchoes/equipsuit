package com.equipsuit.equip_suit_v1.utils;

import com.equipsuit.equip_suit_v1.modInterface.EquipSuit;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.List;

public class EquipSuitHelper{
    public static void SuitChange(Player player,  EquipSuit suit){
        List<ItemStack> list=List.copyOf(suit.slotItems);
        for(int i=0;i<list.size();i++){
            int slotNum= suit.SLOTS_NUMS.get(i);
            ItemStack playerItemStack=player.getInventory().getItem(slotNum);
            suit.slotItems.set(i,playerItemStack);
            player.getInventory().setItem(slotNum, list.get(i));
        }
        suit.save();
    }
    public static void SuitChangeWithoutOff(Player player,EquipSuit suit){
        List<ItemStack> list=List.copyOf(suit.slotItems);
        for(int i=0;i<list.size();i++){
            int slotNum = suit.SLOTS_NUMS.get(i);
            if(slotNum == 40) continue;
            ItemStack playerItemStack=player.getInventory().getItem(slotNum);
            suit.slotItems.set(i,playerItemStack);
            player.getInventory().setItem(slotNum, list.get(i));
        }
       suit.save();
    }



}
