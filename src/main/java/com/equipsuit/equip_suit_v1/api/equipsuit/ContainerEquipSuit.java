package com.equipsuit.equip_suit_v1.api.equipsuit;

import net.minecraft.core.NonNullList;
import net.minecraft.world.Container;

import java.util.Arrays;

public interface ContainerEquipSuit<T extends EquipSuit<T>> extends EquipSuit<T> {
    void setContainerSlotNums(NonNullList<Integer> containerSlotNums);
    NonNullList<Integer> getContainerSlotNums();
    static ContainerEquipSuitImpl build(Container container){
        return new ContainerEquipSuitImpl(container,NonNullList.withSize(SIZE,64));
    }
    static ContainerEquipSuitImpl build(Container container , NonNullList<Integer> integers){
        return new ContainerEquipSuitImpl(container,sizeCheck(integers));
    }

    static ContainerEquipSuitImpl build(Container container , Integer...integers) {
        return new ContainerEquipSuitImpl(container,sizeCheck(integers));
    }
    static ContainerEquipSuitImpl build(Container container , int...integers) {
        return new ContainerEquipSuitImpl(container,sizeCheck(integers));
    }

    static NonNullList<Integer> sizeCheck(int...integers){
        return sizeCheck(NonNullList.of(64, Arrays.stream(integers).boxed().toArray(Integer[]::new))) ;
    }
    static NonNullList<Integer> sizeCheck(Integer...integers){
        return sizeCheck( NonNullList.of(64,integers))  ;
    }

    static NonNullList<Integer> sizeCheck(NonNullList<Integer> integers){
        if(integers.size() < EquipSuit.SIZE){
            return NonNullList.withSize(EquipSuit.SIZE,64);
        }else {
            return integers;
        }
    }

}
