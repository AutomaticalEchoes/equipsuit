package com.automaticalechoes.equipsuit.api.modInterfcae.player;

import com.automaticalechoes.equipsuit.api.modInterfcae.equipsuit.EquipSuit;
import com.automaticalechoes.equipsuit.api.modInterfcae.equipsuit.EquipSuitImpl;
import net.minecraft.nbt.NBTTagCompound;

import java.util.ArrayList;

public interface SuitStack {
    ArrayList<EquipSuit> getEquipSuitList();
    void setSuitList(ArrayList<EquipSuit> suitArrayList);
    boolean setSuitSlotNum(int num,String key,int slotNum);
    NBTTagCompound toTag();
    SuitStack readTag(NBTTagCompound NBTTagCompound);

    static SuitStack fromTag(NBTTagCompound Compound){
        SuitStackImpl suitStack = new SuitStackImpl();
        for(int i=0;i<4;i++){
            EquipSuitImpl equipSuit = new EquipSuitImpl(i);
            NBTTagCompound compound = Compound.getCompoundTag(String.valueOf(i));
            equipSuit.Read(compound);
            suitStack.getEquipSuitList().add(equipSuit);
        }
        return suitStack;
    }
}
