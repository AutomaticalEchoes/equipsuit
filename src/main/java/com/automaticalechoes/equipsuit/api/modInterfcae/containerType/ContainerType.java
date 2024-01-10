package com.automaticalechoes.equipsuit.api.modInterfcae.containerType;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;

public interface ContainerType<T extends IInventory> {
    T getContainer(EntityPlayer player);
}
