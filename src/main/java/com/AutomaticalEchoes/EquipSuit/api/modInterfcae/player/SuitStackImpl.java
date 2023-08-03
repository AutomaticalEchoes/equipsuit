package com.AutomaticalEchoes.EquipSuit.api.modInterfcae.player;

import com.AutomaticalEchoes.EquipSuit.api.modInterfcae.equipsuit.EquipSuit;
import net.minecraft.nbt.CompoundTag;

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

    @Override
    public CompoundTag toTag() {
        CompoundTag compoundtag = new CompoundTag();
        for(int i=0;i<4;i++){
            int[] ints = getSuitArrayList().get(i);
            compoundtag.putIntArray(String.valueOf(i),ints);
        }
        return compoundtag;
    }

    @Override
    public SuitStack readTag(CompoundTag compoundTag) {
        for(int i=0;i<4;i++){
            int[] intArray = compoundTag.getIntArray(String.valueOf(i));
            setSuitSlotNums(i,intArray);
        }
        return this;
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
