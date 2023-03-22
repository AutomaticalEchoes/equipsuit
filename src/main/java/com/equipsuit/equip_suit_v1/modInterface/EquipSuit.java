package com.equipsuit.equip_suit_v1.modInterface;

import com.equipsuit.equip_suit_v1.config.EquipSlotConfig;
import com.equipsuit.equip_suit_v1.modInterface.impl.EquipSuitImpl;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.ItemStack;

public interface EquipSuit<T extends EquipSuit<T>> {
    NonNullList<? extends Integer> SLOTS_NUMS =(NonNullList<? extends Integer>) EquipSlotConfig.EQUIP_SLOT_LIST.get();
    NonNullList<ItemStack> slotItems =NonNullList.withSize(SLOTS_NUMS.size(),ItemStack.EMPTY);
    CompoundTag save();
    T read();
    static EquipSuitImpl defaultEquipSuit(CompoundTag tag){
        return new EquipSuitImpl(tag);
    }

    default NonNullList<ItemStack> getSlotItems() {
        return slotItems;
    }

    default CompoundTag defaultSave(CompoundTag tag){
        CompoundTag armorsTag= tag.contains("equips") ? tag.getCompound("equips") : new CompoundTag();
        ContainerHelper.saveAllItems(armorsTag,slotItems);
        tag.put("equips",armorsTag);
        return tag;
    }

    default T defaultRead(CompoundTag tag){
        if(tag.contains("equips")){
            ContainerHelper.loadAllItems(tag.getCompound("equips"),slotItems);
        }
        return (T)this;
    }
}
