package com.automaticalechoes.equipsuit.api.modInterfcae.baseSlot;

import com.automaticalechoes.equipsuit.api.modInterfcae.containerType.ContainerType;
import com.automaticalechoes.equipsuit.api.modInterfcae.containerType.ContainerTypes;

public class EquipSlot implements BaseSlot{
    private String TypeName;
    private int SlotNum;

    public EquipSlot(ContainerType<?> Type, int slotNum) {
        ContainerTypes.TYPES.forEach((s, containerType) -> {
           if(containerType == Type ) TypeName = s;
        });
        SlotNum = slotNum;
    }

    public EquipSlot(String typeName ,int slotNum){
        this.TypeName = typeName;
        this.SlotNum = slotNum;
    }

    @Override
    public void setContainerType(ContainerType<?> type) {
        ContainerTypes.TYPES.forEach((s, containerType) -> {
            if(containerType == type ) TypeName = s;
        });
    }

    @Override
    public String typeName() {
        return TypeName;
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
