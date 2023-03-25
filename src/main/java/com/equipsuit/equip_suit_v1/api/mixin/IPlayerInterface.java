package com.equipsuit.equip_suit_v1.api.mixin;

import com.equipsuit.equip_suit_v1.api.equipsuit.ContainerEquipSuit;
import com.equipsuit.equip_suit_v1.api.equipsuit.EquipSuit;
import com.equipsuit.equip_suit_v1.common.container.SuitContainer;
import com.equipsuit.equip_suit_v1.common.container.SuitInventoryMenu;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(Player.class)
public interface IPlayerInterface {
    @Invoker
    Inventory invokerGetInventory();
    NonNullList<ContainerEquipSuit> getSuitList();
    Integer getFocus();
    void setFocus(Integer integer);
    SuitContainer getSuitContainer();
    void suiChange();
    SuitInventoryMenu getSuitInventoryMenu();
}
