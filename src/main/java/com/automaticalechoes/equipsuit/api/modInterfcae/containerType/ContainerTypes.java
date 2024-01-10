package com.automaticalechoes.equipsuit.api.modInterfcae.containerType;

import com.automaticalechoes.equipsuit.api.modInterfcae.player.IPlayerInterface;
import com.automaticalechoes.equipsuit.common.container.SuitContainer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryEnderChest;

import java.util.HashMap;

public class ContainerTypes {
    public static final HashMap<String,ContainerType<?>> TYPES = new HashMap<>();
    public static final ContainerType<InventoryPlayer> TYPE_INVENTORY = Register("type_inventory", player -> player.inventory) ;
    public static final ContainerType<InventoryEnderChest> TYPE_ENDER_CHEST = Register("type_ender_chest", player -> player.getInventoryEnderChest());
    public static final ContainerType<SuitContainer> TYPE_SUIT = Register("type_suit",player -> ((IPlayerInterface) player).getSuitContainer());

    public static <T extends IInventory> ContainerType<T> Register(String s , ContainerType<T> containerType){
        TYPES.put(s,containerType);
        return containerType;
    }
}
