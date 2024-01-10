package com.automaticalechoes.equipsuit.api.modInterfcae.baseSlot;

import com.automaticalechoes.equipsuit.api.modInterfcae.containerType.ContainerType;
import com.automaticalechoes.equipsuit.api.modInterfcae.containerType.ContainerTypes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public interface BaseSlot {
    String typeName();
    int getSlotNum();
    void setSlotNum(int slot);
    void setContainerType(ContainerType<?> type);

    default ContainerType<?> ContainerType(){
        return ContainerTypes.TYPES.get(typeName());
    }

    default void onChange(EntityPlayer serverPlayer, ItemStack itemStack){
        ContainerType().getContainer(serverPlayer).setInventorySlotContents(getSlotNum(),itemStack);
    }























}
