package com.equipsuit.equip_suit_v1.api.modInterfcae.player;

import com.equipsuit.equip_suit_v1.api.modInterfcae.equipsuit.ContainerEquipSuit;
import net.minecraft.core.NonNullList;

import java.util.HashMap;

public interface SuitStack {
    HashMap<Integer,int[]> getSuitArrayList();
    void setSuitSlotNums(int suitNum,int... slotNums);
}
