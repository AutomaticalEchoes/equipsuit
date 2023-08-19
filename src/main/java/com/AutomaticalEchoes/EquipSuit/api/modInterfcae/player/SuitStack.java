package com.AutomaticalEchoes.EquipSuit.api.modInterfcae.player;

import com.AutomaticalEchoes.EquipSuit.api.modInterfcae.equipsuit.EquipSuit;
import net.minecraft.nbt.CompoundTag;

import java.util.ArrayList;

public interface SuitStack {
    ArrayList<EquipSuit> getEquipSuitList();
    void setSuitStack(ArrayList<EquipSuit> suitArrayList);
    boolean setSuitSlotNum(int num,String key,int slotNum);
    CompoundTag toTag();
    SuitStack readTag(CompoundTag compoundTag);
}
