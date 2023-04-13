package com.equipsuit.equip_suit_v1.api.modInterfcae.player;

import com.equipsuit.equip_suit_v1.api.config.EquipSlotConfig;
import com.equipsuit.equip_suit_v1.api.modInterfcae.equipsuit.EquipSuit;

import java.util.ArrayList;

public class SuitStackImpl implements SuitStack{
    private ArrayList<int[]> suitArrayList = new ArrayList<>();

    public SuitStackImpl() {
        defaultSet();
    }

    public ArrayList<int[]> getSuitArrayList() {
        return suitArrayList;
    }

    public void setSuitArrayList(ArrayList<int[]> suitArrayList) {
        this.suitArrayList = suitArrayList;
    }

    public void setSuitSlotNums(int suitNum, int... slotNums){
        suitArrayList.set(suitNum,slotNums);
    }
    public SuitStackImpl defaultSet(){
        for(int i=0; i<4 ; i++) {
            int[] Set = new int[EquipSuit.SIZE];
            for(int j=0;j < EquipSuit.SIZE;j++){
                Set[j] = i * EquipSuit.SIZE + j;
            }
            this.suitArrayList.add(i,Set);
        }
        return this;
    }
}
