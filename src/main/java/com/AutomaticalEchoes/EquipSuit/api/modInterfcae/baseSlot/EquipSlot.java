package com.AutomaticalEchoes.EquipSuit.api.modInterfcae.baseSlot;

import com.AutomaticalEchoes.EquipSuit.api.modInterfcae.containerType.ContainerType;

public class EquipSlot implements BaseSlot{
    private ContainerType<?> containerType;
    private int SlotNum;

    public EquipSlot(ContainerType<?> containerType, int slotNum) {
        this.containerType = containerType;
        SlotNum = slotNum;
    }

    @Override
    public ContainerType<?> Type() {
        return containerType;
    }

    @Override
    public int getSlotNum() {
        return SlotNum;
    }

    @Override
    public void setSlotNum(int slot) {
        this.SlotNum = slot;
    }
}
