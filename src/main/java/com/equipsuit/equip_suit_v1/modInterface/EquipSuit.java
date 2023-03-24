package com.equipsuit.equip_suit_v1.modInterface;

import com.equipsuit.equip_suit_v1.config.EquipSlotConfig;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.ItemStack;

public interface EquipSuit<T extends EquipSuit<T>> {
    NonNullList<Integer> SLOTS_NUMS =(NonNullList<Integer>) EquipSlotConfig.EQUIP_SLOT_LIST.get();
    NonNullList<ItemStack> SLOT_ITEMS =NonNullList.withSize(SLOTS_NUMS.size(),ItemStack.EMPTY);
    int SIZE =SLOTS_NUMS.size();
    void save();
    void build();
    static EquipSuitImpl defaultEquipSuit(CompoundTag tag){
        return new EquipSuitImpl(tag);
    }
    static ContainerEquipSuit containerEquipSuit(Container container ,NonNullList<Integer> integers){
        return new ContainerEquipSuit(container,sizeCheck(integers));
    }
    static ContainerEquipSuit containerEquipSuit(Container container , Integer...integers) {
        return new ContainerEquipSuit(container,sizeCheck(integers));
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

    default NonNullList<Integer> getSlotsNums(){
        return SLOTS_NUMS;
    }
    default NonNullList<ItemStack> getSlotItems() {
        return SLOT_ITEMS;
    }

    default CompoundTag defaultSave(CompoundTag tag){
        CompoundTag armorsTag= tag.contains("equips") ? tag.getCompound("equips") : new CompoundTag();
        ContainerHelper.saveAllItems(armorsTag,SLOT_ITEMS);
        tag.put("equips",armorsTag);
        return tag;
    }

    default T defaultRead(CompoundTag tag){
        if(tag.contains("equips")){
            ContainerHelper.loadAllItems(tag.getCompound("equips"),SLOT_ITEMS);
        }
        return (T)this;
    }
    default int getSize(){
        return SIZE;
    }
}
