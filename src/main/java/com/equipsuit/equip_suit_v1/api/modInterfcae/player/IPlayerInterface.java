package com.equipsuit.equip_suit_v1.api.modInterfcae.player;

import com.equipsuit.equip_suit_v1.common.container.SuitContainer;

import java.util.HashMap;


public interface IPlayerInterface {
    HashMap<Integer, int[]> getSuitList();
    Integer getFocus();
    void setFocus(Integer integer);
    SuitContainer getSuitContainer();
    void suiChange();
}
