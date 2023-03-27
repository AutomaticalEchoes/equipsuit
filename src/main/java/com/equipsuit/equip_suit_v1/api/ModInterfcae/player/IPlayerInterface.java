package com.equipsuit.equip_suit_v1.api.ModInterfcae.player;

import com.equipsuit.equip_suit_v1.api.ModInterfcae.equipsuit.ContainerEquipSuit;
import com.equipsuit.equip_suit_v1.common.container.SuitContainer;
import com.equipsuit.equip_suit_v1.common.container.SuitInventoryMenu;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.player.Inventory;


public interface IPlayerInterface {
    NonNullList<ContainerEquipSuit> getSuitList();
    Integer getFocus();
    void setFocus(Integer integer);
    SuitContainer getSuitContainer();
    void suiChange();
}
