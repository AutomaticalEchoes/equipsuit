package com.equipsuit.equip_suit_v1.modInterface.impl;


import com.equipsuit.equip_suit_v1.modInterface.EquipSuit;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Container;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;


public class ContainerEquipSuit implements EquipSuit<ContainerEquipSuit> {
    private Container container;
    private NonNullList<Integer> ContainerSlotNums ;

    public ContainerEquipSuit(Container container ,NonNullList<Integer> nonNullList) {
        this.container = container;
        this.ContainerSlotNums=nonNullList;
        this.build();
    }


    public ContainerEquipSuit(Container container ,Integer...integers) {
        this.container = container;
        this.ContainerSlotNums = NonNullList.of(64,integers);
        this.build();
    }

    @Override
    public void save() {
        for(int i=0;i<ContainerSlotNums.size();i++){
            int num = this.ContainerSlotNums.get(i);
            this.container.setItem(num,this.getSlotItems().get(i));
        }
    }

    @Override
    public void build() {
        for(int i=0;i<ContainerSlotNums.size();i++){
            int num = this.ContainerSlotNums.get(i);
            this.getSlotItems().set(i,container.getItem(num));
        }
    }
}
