package com.equipsuit.equip_suit_v1.api.utils;

import com.equipsuit.equip_suit_v1.api.modInterfcae.equipsuit.ContainerEquipSuit;
import com.equipsuit.equip_suit_v1.api.modInterfcae.equipsuit.EquipSuit;
import com.equipsuit.equip_suit_v1.api.modInterfcae.player.IPlayerInterface;
import com.equipsuit.equip_suit_v1.common.container.SuitContainer;
import net.minecraft.nbt.IntArrayTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class EquipSuitHelper{
    public static void SuitChange(Player player, EquipSuit suit){
        List<ItemStack> list=List.copyOf(suit.getSlotItems());
        for(int i=0;i<list.size();i++){
            int slotNum= (int) suit.getSlotsNums().get(i);
            ItemStack playerItemStack=player.getInventory().getItem(slotNum);
            suit.getSlotItems().set(i,playerItemStack);
            player.getInventory().setItem(slotNum, list.get(i));
        }
        suit.save();
    }
    public static void SuitChangeWithoutOff(Player player, EquipSuit suit){
        List<ItemStack> list=List.copyOf(suit.getSlotItems());
        for(int i=0;i<list.size();i++){
            int slotNum = (int)suit.getSlotsNums().get(i);
            if(slotNum == 40) continue;
            ItemStack playerItemStack=player.getInventory().getItem(slotNum);
            suit.getSlotItems().set(i,playerItemStack);
            player.getInventory().setItem(slotNum, list.get(i));
        }
       suit.save();
    }
    public static void ResetChangeWithoutOff(Player player, EquipSuit suit){
        List<ItemStack> list=List.copyOf(suit.getSlotItems());
        for(int i=0;i<list.size();i++){
            int slotNum = (int)suit.getSlotsNums().get(i);
            if(slotNum == 40) continue;
            ItemStack playerItemStack=player.getInventory().getItem(slotNum);
            suit.getSlotItems().set(i,playerItemStack);
            player.getInventory().setItem(slotNum, list.get(i));
        }
        suit.save();
    }


    public static void suiChange(Player player) {
        IPlayerInterface player1 = (IPlayerInterface) player;
        ArrayList<int[]> suitArrayList = player1.getSuitList();
        int focus = player1.getFocus();
        SuitContainer suitContainer= player1.getSuitContainer();
        EquipSuitHelper.SuitChangeWithoutOff(player, ContainerEquipSuit.buildInt(suitContainer,suitArrayList.get(focus)).build());
        EquipSuitHelper.SuitChangeWithoutOff(player,ContainerEquipSuit.buildInt(suitContainer,suitArrayList.get((focus + 1) % 4)).build());
        (player1).setFocus((focus + 1) % 4);
    }

}
