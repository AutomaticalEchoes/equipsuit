package com.automaticalechoes.equipsuit.api.utils;

import com.automaticalechoes.equipsuit.api.modInterfcae.baseSlot.BaseSlot;
import com.automaticalechoes.equipsuit.api.modInterfcae.equipsuit.EquipSuit;
import com.automaticalechoes.equipsuit.api.modInterfcae.player.IPlayerInterface;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.function.BiConsumer;

public class EquipSuitHelper{
    public static void EquipChange(EntityPlayer serverPlayer, EquipSuit equipSuit){
        equipSuit.right().forEach(new BiConsumer<String, BaseSlot>() {
            @Override
            public void accept(String s, BaseSlot rightSlot) {
                IInventory rightContainer = rightSlot.ContainerType().getContainer(serverPlayer);
                ItemStack itemRight = rightContainer.getStackInSlot(rightSlot.getSlotNum());

                BaseSlot leftSlot = equipSuit.left().get(s);
                IInventory leftContainer = leftSlot.ContainerType().getContainer(serverPlayer);
                ItemStack itemLeft = leftContainer.getStackInSlot(leftSlot.getSlotNum());

                leftSlot.onChange(serverPlayer,itemRight);
                rightSlot.onChange(serverPlayer,itemLeft);
            }
        });
    }

    public static boolean SuitChange(EntityPlayer player) {
        IPlayerInterface player1 = (IPlayerInterface) player;
        int focus = player1.getFocus();
        return SuitChange(player,focus,(focus + 1) % 4);
    }

    public static boolean SuitChange(EntityPlayer player, int targetNum){
        IPlayerInterface player1 = (IPlayerInterface) player;
        int focus = player1.getFocus();
        return SuitChange(player,focus,targetNum);
    }

    public static boolean SuitChange(EntityPlayer player, int oldNum , int targetNum ) {
        try {
            targetNum = targetNum < 4 ? targetNum : 0 ;
            IPlayerInterface player1 = (IPlayerInterface) player;
            ArrayList<EquipSuit> equipSuitList = player1.getSuitStack().getEquipSuitList();

            EquipChange(player, equipSuitList.get(oldNum));
            EquipChange(player, equipSuitList.get(targetNum));
            return true;
        }catch (Exception e){
            return false;
        }
    }
    // 一定要用两次哦！
    public static void SingleChange(EntityPlayer player){
        IPlayerInterface player1 = (IPlayerInterface) player;
        int focus = player1.getFocus();
        ArrayList<EquipSuit> equipSuitList = player1.getSuitStack().getEquipSuitList();
        EquipChange(player, equipSuitList.get(focus));
    }

}
