package com.AutomaticalEchoes.EquipSuit.api.modInterfcae.player;

import net.minecraft.nbt.CompoundTag;

import java.util.ArrayList;

public interface SuitStack {
    ArrayList<int[]> getSuitArrayList();
    void setSuitSlotNums(int suitNum,int... slotNums);
    void setSuitArrayList(ArrayList<int[]> suitArrayList);
    CompoundTag toTag();
    SuitStack readTag(CompoundTag compoundTag);
}
