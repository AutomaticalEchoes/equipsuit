package com.equipsuit.equip_suit_v1.api.ModInterfcae.equipsuit;


import net.minecraft.core.NonNullList;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;


public class ContainerEquipSuitImpl implements ContainerEquipSuit<ContainerEquipSuitImpl> {
    private Container container;
    private NonNullList<Integer> ContainerSlotNums ;
    NonNullList<ItemStack> SLOT_ITEMS = NonNullList.withSize(getSize(),ItemStack.EMPTY);

  

    protected ContainerEquipSuitImpl(Container container , NonNullList<Integer> nonNullList) {
        this.container = container;
        this.ContainerSlotNums=nonNullList;
    }

    public void setContainerSlotNums(NonNullList<Integer> containerSlotNums) {
        ContainerSlotNums = containerSlotNums;
    }

    public NonNullList<Integer> getContainerSlotNums() {
        return ContainerSlotNums;
    }

    @Override
    public void save() {
        for(int i=0;i<ContainerSlotNums.size();i++){
            int num = this.ContainerSlotNums.get(i);
            this.container.setItem(num,this.getSlotItems().get(i));
        }
    }

    @Override
    public ContainerEquipSuitImpl build() {
        for(int i=0;i<ContainerSlotNums.size();i++){
            int num = this.ContainerSlotNums.get(i);
            this.getSlotItems().set(i,container.getItem(num));
        }
        return this;
    }

    @Override
    public NonNullList<ItemStack> getSlotItems() {
        return SLOT_ITEMS;
    }


}
