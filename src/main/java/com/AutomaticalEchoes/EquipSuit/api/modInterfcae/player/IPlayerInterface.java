package com.AutomaticalEchoes.EquipSuit.api.modInterfcae.player;

import com.AutomaticalEchoes.EquipSuit.common.container.SuitContainer;
import net.minecraft.world.entity.player.Player;


public interface IPlayerInterface {
    SuitStack getSuitStack();
    Integer getFocus();
    void setFocus(Integer integer);
    void updateFocus();
    boolean setSuitSlotNum(int num,String key,int slotNum);
    SuitContainer getSuitContainer();
    void restore(Player player);
}
