package com.equipsuit.equip_suit_v1.modInterface.impl;

import com.equipsuit.equip_suit_v1.modInterface.EquipSuit;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public class EquipSuitImpl implements EquipSuit<EquipSuitImpl> {
    public CompoundTag tag;

    public EquipSuitImpl(CompoundTag tag) {
        this.tag = tag;
        this.build();
    }

    public CompoundTag getTag() {
        return tag;
    }

    @Override
    public void save() {
        this.defaultSave(tag);
    }

    @Override
    public void build() {
         this.defaultRead(tag);
    }


}
