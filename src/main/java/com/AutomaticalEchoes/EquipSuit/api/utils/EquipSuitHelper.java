package com.AutomaticalEchoes.EquipSuit.api.utils;

import com.AutomaticalEchoes.EquipSuit.api.modInterfcae.equipsuit.ContainerEquipSuit;
import com.AutomaticalEchoes.EquipSuit.api.modInterfcae.equipsuit.EquipSuit;
import com.AutomaticalEchoes.EquipSuit.api.modInterfcae.player.IPlayerInterface;
import com.AutomaticalEchoes.EquipSuit.common.container.SuitContainer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class EquipSuitHelper{
    public static void EquipChange(Container container, EquipSuit equipSuit ,@Nullable int... exceptNums){
        List<ItemStack> list=List.copyOf(equipSuit.getSlotItems());
        for(int i=0;i<list.size();i++){
            int slotNum= (int) equipSuit.getSlotsNums().get(i);
            int finalI = i;
            if(exceptNums !=null && exceptNums.length != 0  && Arrays.stream(exceptNums).anyMatch(value -> value == finalI)) continue;
            ItemStack playerItemStack=container.getItem(slotNum);
            equipSuit.getSlotItems().set(i,playerItemStack);
            container.setItem(slotNum, list.get(i));
        }
        equipSuit.save();
    }

    public static void EquipChange(Player player, EquipSuit suit, @Nullable int... exceptNums){
        EquipChange(player.getInventory(),suit,exceptNums);
    }

    public static boolean SuitChange(Player player) {
        IPlayerInterface player1 = (IPlayerInterface) player;
        int focus = player1.getFocus();
        return SuitChange(player,focus,(focus + 1) % 4,null);
    }

    public static boolean SuitChange(Player player, int targetNum){
        IPlayerInterface player1 = (IPlayerInterface) player;
        int focus = player1.getFocus();
        return SuitChange(player,focus,targetNum,null);
    }

    public static boolean SuitChange(Player player, int oldNum , int targetNum , @Nullable Container container ,@Nullable int...except) {
        try {
            targetNum = targetNum < 4 ? targetNum : 0 ;
            IPlayerInterface player1 = (IPlayerInterface) player;
            ArrayList<int[]> suitArrayList = player1.getSuitList();
            SuitContainer suitContainer= player1.getSuitContainer();
            if(container!=null){
                EquipChange(container, ContainerEquipSuit.buildInt(suitContainer,suitArrayList.get(oldNum)).build(),except);
                EquipChange(container, ContainerEquipSuit.buildInt(suitContainer,suitArrayList.get(targetNum)).build(),except);
                return true;
            }
            EquipChange(player, ContainerEquipSuit.buildInt(suitContainer,suitArrayList.get(oldNum)).build(),except);
            EquipChange(player, ContainerEquipSuit.buildInt(suitContainer,suitArrayList.get(targetNum)).build(),except);
            return true;
        }catch (Exception e){
            return false;
        }
    }
    // 一定要用两次哦！
    public static void SingleChange(Player player){
        IPlayerInterface player1 = (IPlayerInterface) player;
        int focus = player1.getFocus();
        SuitContainer suitContainer= player1.getSuitContainer();
        ArrayList<int[]> suitArrayList = player1.getSuitList();
        EquipChange(player,ContainerEquipSuit.buildInt(suitContainer,suitArrayList.get(focus)).build());
    }

}
