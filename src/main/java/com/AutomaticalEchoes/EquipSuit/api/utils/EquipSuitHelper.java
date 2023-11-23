package com.AutomaticalEchoes.EquipSuit.api.utils;

import com.AutomaticalEchoes.EquipSuit.api.modInterfcae.baseSlot.BaseSlot;
import com.AutomaticalEchoes.EquipSuit.api.modInterfcae.equipsuit.EquipSuit;
import com.AutomaticalEchoes.EquipSuit.api.modInterfcae.player.IPlayerInterface;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.function.BiConsumer;

public class EquipSuitHelper{
    public static void EquipChange(ServerPlayer serverPlayer,EquipSuit equipSuit){
        equipSuit.right().forEach(new BiConsumer<String, BaseSlot>() {
            @Override
            public void accept(String s, BaseSlot rightSlot) {
                Container rightContainer = rightSlot.ContainerType().getContainer(serverPlayer);
                ItemStack itemRight = rightContainer.getItem(rightSlot.getSlotNum());

                BaseSlot leftSlot = equipSuit.left().get(s);
                Container leftContainer = leftSlot.ContainerType().getContainer(serverPlayer);
                ItemStack itemLeft = leftContainer.getItem(leftSlot.getSlotNum());

                leftSlot.onChange(serverPlayer,itemRight);
                rightSlot.onChange(serverPlayer,itemLeft);

            }
        });
    }

    public static boolean SuitChange(ServerPlayer player) {
        IPlayerInterface player1 = (IPlayerInterface) player;
        int focus = player1.getFocus();
        return SuitChange(player,focus,(focus + 1) % 4);
    }

    public static boolean SuitChange(ServerPlayer player, int targetNum){
        IPlayerInterface player1 = (IPlayerInterface) player;
        int focus = player1.getFocus();
        return SuitChange(player,focus,targetNum);
    }

    public static boolean SuitChange(ServerPlayer player, int oldNum , int targetNum ) {
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

    public static void SingleChange(ServerPlayer player){
        IPlayerInterface player1 = (IPlayerInterface) player;
        int focus = player1.getFocus();
        ArrayList<EquipSuit> equipSuitList = player1.getSuitStack().getEquipSuitList();
        EquipChange(player, equipSuitList.get(focus));
    }

}
