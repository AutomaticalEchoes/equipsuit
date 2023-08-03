package com.AutomaticalEchoes.EquipSuit.api.modInterfcae.player;

import com.AutomaticalEchoes.EquipSuit.common.container.SuitContainer;
import net.minecraft.world.entity.player.Player;

import java.util.ArrayList;


public interface IPlayerInterface {
    ArrayList<int[]> getSuitList();
    Integer getFocus();
    void setFocus(Integer integer);
    void updateFocus();
    boolean setSuitArray(int num, int... ints);
    SuitContainer getSuitContainer();
    void restore(Player player);
}
