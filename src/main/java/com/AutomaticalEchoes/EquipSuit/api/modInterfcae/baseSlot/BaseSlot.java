package com.AutomaticalEchoes.EquipSuit.api.modInterfcae.baseSlot;

import com.AutomaticalEchoes.EquipSuit.api.modInterfcae.containerType.ContainerType;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;

public interface BaseSlot {
    ContainerType<?> Type();
    int getSlotNum();
    void setSlotNum(int slot);
    default void onChange(ServerPlayer serverPlayer, ItemStack itemStack){
        Type().getContainer(serverPlayer).setItem(getSlotNum(),itemStack);
    }






















}
