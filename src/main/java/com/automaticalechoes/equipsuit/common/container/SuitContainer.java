package com.automaticalechoes.equipsuit.common.container;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.ITextComponent;

public class SuitContainer implements IInventory {
    public final NonNullList<ItemStack> items = NonNullList.withSize(36, ItemStack.EMPTY);
    public EntityPlayer player;

    public SuitContainer(EntityPlayer player) {
        this.player = player;
    }

    public void replaceWith(SuitContainer p_36007_) {
        for(int i = 0; i < this.getSizeInventory(); ++i) {
            this.setInventorySlotContents(i, p_36007_.getStackInSlot(i));
        }
    }

    public NBTTagList save(NBTTagList listTag){
        for(int i = 0; i < this.items.size(); ++i) {
            if (!this.items.get(i).isEmpty()) {
                NBTTagCompound compoundtag = new NBTTagCompound();
                compoundtag.setByte("Slot", (byte)i);
                this.items.get(i).writeToNBT(compoundtag);
                listTag.appendTag(compoundtag);
            }
        }
        return listTag;
    }

    public void load(NBTTagList p_36036_) {
        this.items.clear();
        for(int i = 0; i < p_36036_.tagCount(); ++i) {
            NBTTagCompound compoundtag = p_36036_.getCompoundTagAt(i);
            int j = compoundtag.getByte("Slot") & 255;
            ItemStack itemstack = new ItemStack(compoundtag);
            if (!itemstack.isEmpty()) {
                if (j < this.items.size()) {
                    this.items.set(j, itemstack);
                }
            }
        }

    }

    public void dropAll(EntityPlayer player) {
        items.forEach(itemStack -> {
                player.dropItem(itemStack, true, false);
        });

    }

    @Override
    public int getSizeInventory() {
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

    @Override
    public ItemStack getStackInSlot(int i) {
        return i >= this.getSizeInventory() ? ItemStack.EMPTY : this.items.get(i);
    }

    @Override
    public ItemStack decrStackSize(int i, int i1) {
        ItemStack lvt_3_1_ = ItemStackHelper.getAndSplit(this.items, i, i1);
        if (!lvt_3_1_.isEmpty()) {
            this.markDirty();
        }

        return lvt_3_1_;
    }

    @Override
    public ItemStack removeStackFromSlot(int i) {
        ItemStack lvt_2_1_ = (ItemStack)this.items.get(i);
        if (lvt_2_1_.isEmpty()) {
            return ItemStack.EMPTY;
        } else {
            this.items.set(i, ItemStack.EMPTY);
            return lvt_2_1_;
        }
    }

    @Override
    public void setInventorySlotContents(int i, ItemStack itemStack) {
        this.items.set(i, itemStack);
    }

    @Override
    public int getInventoryStackLimit() {
        return 1;
    }

    @Override
    public void markDirty() {

    }

    @Override
    public boolean isUsableByPlayer(EntityPlayer entityPlayer) {
        return false;
    }

    @Override
    public void openInventory(EntityPlayer entityPlayer) {

    }

    @Override
    public void closeInventory(EntityPlayer entityPlayer) {

    }

    @Override
    public boolean isItemValidForSlot(int i, ItemStack itemStack) {
        return true;
    }

    @Override
    public int getField(int i) {
        return 0;
    }

    @Override
    public void setField(int i, int i1) {

    }

    @Override
    public int getFieldCount() {
        return 0;
    }

    @Override
    public void clear() {
        this.items.clear();
    }

    @Override
    public String getName() {
        return null;
    }

    @Override
    public boolean hasCustomName() {
        return false;
    }

    @Override
    public ITextComponent getDisplayName() {
        return null;
    }
}
