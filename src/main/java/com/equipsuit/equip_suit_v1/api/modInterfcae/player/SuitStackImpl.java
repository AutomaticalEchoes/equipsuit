package com.equipsuit.equip_suit_v1.api.modInterfcae.player;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;

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
        for(int i=0;i<4;i++) {
            this.suitArrayList.add(i,new int[]{ i * 4, i * 4 + 1, i * 4 + 2, i * 4 + 3});
        }
        return this;
    }
}
