package com.equipsuit.equip_suit_v1.modInterface;

import com.equipsuit.equip_suit_v1.config.EquipSlotConfig;
import com.equipsuit.equip_suit_v1.modInterface.impl.EquipSuitImpl;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.ItemStack;

public interface EquipSuit<T extends EquipSuit<T>> {
    NonNullList<Integer> SLOTS_NUMS =(NonNullList<Integer>) EquipSlotConfig.EQUIP_SLOT_LIST.get();
    NonNullList<ItemStack> SLOT_ITEMS =NonNullList.withSize(SLOTS_NUMS.size(),ItemStack.EMPTY);
    void save();
    void build();
    static EquipSuitImpl defaultEquipSuit(CompoundTag tag){
        return new EquipSuitImpl(tag);
    }

    default NonNullList<Integer> getSlotsNums(){return SLOTS_NUMS;}
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
}
