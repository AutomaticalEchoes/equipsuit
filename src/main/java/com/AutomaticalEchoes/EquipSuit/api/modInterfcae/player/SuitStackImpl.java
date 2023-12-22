package com.AutomaticalEchoes.EquipSuit.api.modInterfcae.player;

import com.AutomaticalEchoes.EquipSuit.api.modInterfcae.baseSlot.BaseSlot;
import com.AutomaticalEchoes.EquipSuit.api.modInterfcae.equipsuit.EquipSuit;
import com.AutomaticalEchoes.EquipSuit.api.modInterfcae.equipsuit.EquipSuitImpl;
import net.minecraft.nbt.CompoundTag;

import java.util.ArrayList;

public class SuitStackImpl implements SuitStack{
    private ArrayList<EquipSuit> suitStack = new ArrayList<>();

    @Override
    public ArrayList<EquipSuit> getEquipSuitList() {
        return suitStack;
    }

    @Override
    public void setSuitStack(ArrayList<EquipSuit> suitArrayList) {
        this.suitStack = suitArrayList;
    }

    @Override
    public boolean setSuitSlotNum(int num, String key, int slotNum) {
        EquipSuit equipSuit = this.suitStack.get(num);
        if(!equipSuit.left().containsKey(key)) return false;
        BaseSlot baseSlot = suitStack.get(num).left().get(key);
        baseSlot.setSlotNum(slotNum);
        suitStack.get(num).left().put(key,baseSlot);
        return true;
    }

    @Override
    public CompoundTag toTag() {
        CompoundTag compoundtag = new CompoundTag();
        for(int i=0;i<4;i++){
            CompoundTag save = suitStack.get(i).Save(new CompoundTag());
            compoundtag.put(String.valueOf(i),save);
        }
        return compoundtag;
    }

    @Override
    public SuitStack readTag(CompoundTag compoundTag) {
        suitStack.clear();
        for(int i=0;i<4;i++){
            EquipSuitImpl equipSuit = new EquipSuitImpl(i);
            CompoundTag compound = compoundTag.getCompound(String.valueOf(i));
            equipSuit.Read(compound);
            suitStack.add(equipSuit);
        }
        return this;
    }

    public SuitStackImpl defaultSet(){
        suitStack.clear();
        for(int i=0; i < 4 ; i++) {
            EquipSuitImpl equipSuit = new EquipSuitImpl(i);
            equipSuit.Build();
            suitStack.add(equipSuit);
        }
        return this;
    }
}
