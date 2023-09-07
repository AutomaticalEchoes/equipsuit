package com.AutomaticalEchoes.EquipSuit.api.modInterfcae.player;

import com.AutomaticalEchoes.EquipSuit.api.modInterfcae.equipsuit.EquipSuit;
import com.AutomaticalEchoes.EquipSuit.api.modInterfcae.equipsuit.EquipSuitImpl;
import net.minecraft.nbt.CompoundTag;

import java.util.ArrayList;

public interface SuitStack {
    ArrayList<EquipSuit> getEquipSuitList();
    void setSuitStack(ArrayList<EquipSuit> suitArrayList);
    boolean setSuitSlotNum(int num,String key,int slotNum);
    CompoundTag toTag();
    SuitStack readTag(CompoundTag compoundTag);

    static SuitStack fromTag(CompoundTag compoundTag){
        SuitStackImpl suitStack = new SuitStackImpl();
        for(int i=0;i<4;i++){
            EquipSuitImpl equipSuit = new EquipSuitImpl(i);
            CompoundTag compound = compoundTag.getCompound(String.valueOf(i));
            equipSuit.Read(compound);
            suitStack.getEquipSuitList().add(equipSuit);
        }
        return suitStack;
    }
}

