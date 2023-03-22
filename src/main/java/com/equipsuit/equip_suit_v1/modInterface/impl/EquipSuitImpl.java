package com.equipsuit.equip_suit_v1.modInterface.impl;

import com.equipsuit.equip_suit_v1.modInterface.EquipSuit;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.item.ItemStack;

public class EquipSuitImpl implements EquipSuit<EquipSuitImpl> {
    public CompoundTag tag;

    public EquipSuitImpl(CompoundTag tag) {
        this.tag = tag;
        this.read();
    }

    public CompoundTag getTag() {
        return tag;
    }

    @Override
    public CompoundTag save() {
        return this.defaultSave(tag);
    }

    @Override
    public EquipSuitImpl read() {
        return this.defaultRead(tag);
    }


}
