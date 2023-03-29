package com.equipsuit.equip_suit_v1.api.modInterfcae.player;

import com.equipsuit.equip_suit_v1.api.modInterfcae.equipsuit.ContainerEquipSuit;
import com.equipsuit.equip_suit_v1.common.container.SuitContainer;
import net.minecraft.core.NonNullList;

import java.util.HashMap;

public class SuitStackImpl implements SuitStack{
    private final HashMap<Integer,int[]> suitArrayList = new HashMap<Integer,int[]>();

    public SuitStackImpl() {
        defaultSet();
    }

    public HashMap<Integer,int[]> getSuitArrayList() {
        defaultSet();
        return suitArrayList;
    }
    public void setSuitSlotNums(int suitNum,int... slotNums){
        suitArrayList.put(suitNum,slotNums);
    }
    private void defaultSet(){
        for(int i=0;i<4;i++) {
            suitArrayList.put(i,new int[]{ i * 4, i * 4 + 1, i * 4 + 2, i * 4 + 3});
        }
    }
}
