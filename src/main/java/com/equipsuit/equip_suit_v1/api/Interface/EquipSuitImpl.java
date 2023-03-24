package com.equipsuit.equip_suit_v1.api.Interface;

import net.minecraft.nbt.CompoundTag;

public class EquipSuitImpl implements EquipSuit<EquipSuitImpl> {
    public CompoundTag tag;

   protected EquipSuitImpl(CompoundTag tag) {
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
