package com.automaticalechoes.equipsuit.api.modInterfcae.player;

import com.automaticalechoes.equipsuit.common.container.SuitContainer;
import net.minecraft.entity.player.EntityPlayer;


public interface IPlayerInterface {
    SuitStack getSuitStack();
    Integer getFocus();
    void setFocus(Integer integer);
    void updateFocus();
    boolean setSuitSlotNum(int num,String key,int slotNum);
    SuitContainer getSuitContainer();
    void restore(EntityPlayer player);
    void setSuitName(int num,String s);
    void dropEquipment();
}
