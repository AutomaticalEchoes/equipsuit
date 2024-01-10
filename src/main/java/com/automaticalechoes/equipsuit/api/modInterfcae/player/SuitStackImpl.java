package com.automaticalechoes.equipsuit.api.modInterfcae.player;

import com.automaticalechoes.equipsuit.api.modInterfcae.baseSlot.BaseSlot;
import com.automaticalechoes.equipsuit.api.modInterfcae.equipsuit.EquipSuit;
import com.automaticalechoes.equipsuit.api.modInterfcae.equipsuit.EquipSuitImpl;
import net.minecraft.nbt.NBTTagCompound;

import java.util.ArrayList;

public class SuitStackImpl implements SuitStack{
    private ArrayList<EquipSuit> suitList = new ArrayList<>();

    @Override
    public ArrayList<EquipSuit> getEquipSuitList() {
        return suitList;
    }

    @Override
    public void setSuitList(ArrayList<EquipSuit> suitArrayList) {
        this.suitList = suitArrayList;
    }

    @Override
    public boolean setSuitSlotNum(int num, String key, int slotNum) {
        EquipSuit equipSuit = this.suitList.get(num);
        if(!equipSuit.left().containsKey(key)) return false;
        BaseSlot baseSlot = suitList.get(num).left().get(key);
        baseSlot.setSlotNum(slotNum);
        suitList.get(num).left().put(key,baseSlot);
        return true;
    }

    @Override
    public NBTTagCompound toTag() {
        NBTTagCompound compoundtag = new NBTTagCompound();
        for(int i=0;i<4;i++){
            NBTTagCompound save = suitList.get(i).Save(new NBTTagCompound());
            compoundtag.setTag(String.valueOf(i),save);
        }
        return compoundtag;
    }

    @Override
    public SuitStack readTag(NBTTagCompound compoundTag) {
        suitList.clear();
        for(int i=0;i<4;i++){
            EquipSuitImpl equipSuit = new EquipSuitImpl(i);
            NBTTagCompound compound = compoundTag.getCompoundTag(String.valueOf(i));
            equipSuit.Read(compound);
            suitList.add(equipSuit);
        }
        return this;
    }

    public SuitStackImpl defaultSet(){
        suitList.clear();
        for(int i=0; i < 4 ; i++) {
            EquipSuitImpl equipSuit = new EquipSuitImpl(i);
            equipSuit.Build();
            suitList.add(equipSuit);
        }
        return this;
    }


}
