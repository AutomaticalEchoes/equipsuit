package com.AutomaticalEchoes.EquipSuit.api.modInterfcae.containerType;

import com.AutomaticalEchoes.EquipSuit.api.modInterfcae.player.IPlayerInterface;
import com.AutomaticalEchoes.EquipSuit.common.container.SuitContainer;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.PlayerEnderChestContainer;

import java.util.HashMap;

public class ContainerTypes {
    public static final HashMap<String,ContainerType<?>> TYPES = new HashMap<>();
    public static final ContainerType<Inventory> TYPE_INVENTORY = Register("type_inventory",Player::getInventory) ;
    public static final ContainerType<PlayerEnderChestContainer> TYPE_ENDER_CHEST = Register("type_ender_chest",Player::getEnderChestInventory);
    public static final ContainerType<SuitContainer> TYPE_SUIT = Register("type_suit",player -> ((IPlayerInterface) player).getSuitContainer());

    public static <T extends Container> ContainerType<T> Register(String s ,ContainerType<T> containerType){
        TYPES.put(s,containerType);
        return containerType;
    }
}
