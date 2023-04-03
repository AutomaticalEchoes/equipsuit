package com.equipsuit.equip_suit_v1.api.modInterfcae.player;

import java.util.ArrayList;

public interface SuitStack {
    ArrayList<int[]> getSuitArrayList();
    void setSuitSlotNums(int suitNum,int... slotNums);
    void setSuitArrayList(ArrayList<int[]> suitArrayList);
}
