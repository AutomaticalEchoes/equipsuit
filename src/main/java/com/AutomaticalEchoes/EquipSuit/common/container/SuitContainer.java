package com.AutomaticalEchoes.EquipSuit.common.container;

import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.Container;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;

public class SuitContainer implements Container {
    public final NonNullList<ItemStack> items = NonNullList.withSize(36, ItemStack.EMPTY);
    public Player player;

    public SuitContainer(Player player) {
        this.player = player;
    }

    public void replaceWith(SuitContainer p_36007_) {
        for(int i = 0; i < this.getContainerSize(); ++i) {
            this.setItem(i, p_36007_.getItem(i));
        }
    }

    @Override
    public int getContainerSize() {
        return items.size();
    }

    @Override
    public boolean isEmpty() {
        for(ItemStack itemstack : this.items) {
            if (!itemstack.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    public ItemStack getItem(int p_39332_) {
        return p_39332_ >= this.getContainerSize() ? ItemStack.EMPTY : this.items.get(p_39332_);
    }

    public ItemStack removeItemNoUpdate(int p_39344_) {
        return ContainerHelper.takeItem(this.items, p_39344_);
    }

    public ItemStack removeItem(int p_39334_, int p_39335_) {
        ItemStack itemstack = ContainerHelper.removeItem(this.items, p_39334_, p_39335_);
        return itemstack;
    }

    public void setItem(int p_39337_, ItemStack p_39338_) {
        this.items.set(p_39337_, p_39338_);
    }

    @Override
    public void setChanged() {

    }

    @Override
    public boolean stillValid(Player p_18946_) {
        return true;
    }

    @Override
    public void clearContent() {
        this.items.clear();
    }

    public ListTag save(ListTag listTag){
        for(int i = 0; i < this.items.size(); ++i) {
            if (!this.items.get(i).isEmpty()) {
                CompoundTag compoundtag = new CompoundTag();
                compoundtag.putByte("Slot", (byte)i);
                this.items.get(i).save(compoundtag);
                listTag.add(compoundtag);
            }
        }
        return listTag;
    }

    public void load(ListTag p_36036_) {
        this.items.clear();
        for(int i = 0; i < p_36036_.size(); ++i) {
            CompoundTag compoundtag = p_36036_.getCompound(i);
            int j = compoundtag.getByte("Slot") & 255;
            ItemStack itemstack = ItemStack.of(compoundtag);
            if (!itemstack.isEmpty()) {
                if (j < this.items.size()) {
                    this.items.set(j, itemstack);
                }
            }
        }

    }

    public void dropAll(Player player) {
        items.forEach(itemStack -> {
                player.drop(itemStack, true, false);
        });

    }
}
