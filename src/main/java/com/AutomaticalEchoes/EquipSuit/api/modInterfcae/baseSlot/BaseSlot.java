package com.AutomaticalEchoes.EquipSuit.api.modInterfcae.baseSlot;

import com.AutomaticalEchoes.EquipSuit.api.modInterfcae.containerType.ContainerType;
import com.AutomaticalEchoes.EquipSuit.api.modInterfcae.containerType.ContainerTypes;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

public interface BaseSlot {
    String typeName();
    int getSlotNum();
    void setSlotNum(int slot);
    void setContainerType(ContainerType<?> type);

    default ContainerType<?> ContainerType(){
        return ContainerTypes.TYPES.get(typeName());
    }

    default void onChange(ServerPlayer serverPlayer, ItemStack itemStack){
        ContainerType().getContainer(serverPlayer).setItem(getSlotNum(),itemStack);
    }























}
