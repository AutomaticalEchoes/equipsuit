package com.AutomaticalEchoes.EquipSuit.api.modInterfcae.equipsuit;

import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.item.ItemStack;

public class EquipSuitImpl implements EquipSuit<EquipSuitImpl> {
    private NonNullList<ItemStack> SLOT_ITEMS = NonNullList.withSize(getSize(),ItemStack.EMPTY);
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
    public EquipSuitImpl build() {
        if(tag.contains("equips")){
            ContainerHelper.loadAllItems(tag.getCompound("equips"),SLOT_ITEMS);
        }
        return this;
    }

    @Override
    public NonNullList<ItemStack> getSlotItems() {
        return SLOT_ITEMS;
    }


}
