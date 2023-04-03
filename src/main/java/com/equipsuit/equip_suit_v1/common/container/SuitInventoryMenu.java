package com.equipsuit.equip_suit_v1.common.container;

import com.equipsuit.equip_suit_v1.api.modInterfcae.equipsuit.EquipSuit;
import com.equipsuit.equip_suit_v1.api.modInterfcae.player.IPlayerInterface;
import com.equipsuit.equip_suit_v1.common.registry.ContainerRegister;
import com.mojang.datafixers.util.Pair;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ArmorItem;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.EnchantmentHelper;

public class SuitInventoryMenu extends AbstractContainerMenu {
    public static final ResourceLocation BLOCK_ATLAS = new ResourceLocation("textures/atlas/blocks.png");
    public static final ResourceLocation EMPTY_ARMOR_SLOT_HELMET = new ResourceLocation("item/empty_armor_slot_helmet");
    public static final ResourceLocation EMPTY_ARMOR_SLOT_CHESTPLATE = new ResourceLocation("item/empty_armor_slot_chestplate");
    public static final ResourceLocation EMPTY_ARMOR_SLOT_LEGGINGS = new ResourceLocation("item/empty_armor_slot_leggings");
    public static final ResourceLocation EMPTY_ARMOR_SLOT_BOOTS = new ResourceLocation("item/empty_armor_slot_boots");
    public static final ResourceLocation EMPTY_ARMOR_SLOT_SHIELD = new ResourceLocation("item/empty_armor_slot_shield");
    private static final EquipmentSlot[] SLOT_IDS = new EquipmentSlot[]{EquipmentSlot.HEAD, EquipmentSlot.CHEST, EquipmentSlot.LEGS, EquipmentSlot.FEET };
    private static final ResourceLocation[] TEXTURE_EMPTY_SLOTS = new ResourceLocation[]{EMPTY_ARMOR_SLOT_BOOTS, EMPTY_ARMOR_SLOT_LEGGINGS, EMPTY_ARMOR_SLOT_CHESTPLATE, EMPTY_ARMOR_SLOT_HELMET};
    private final Inventory inventory;
    private final SuitContainer suitContainer;
    private final Player owner;
    public static SuitInventoryMenu Create(int p_38852_, Inventory inventory) {
        return new SuitInventoryMenu(inventory,p_38852_);
    }

    public SuitInventoryMenu(Inventory inventory, int p_38852_) {
        super(ContainerRegister.SUIT_INVENTORY_MENU.get(), p_38852_);
        this.inventory = inventory;
        this.owner = inventory.player;
        this.suitContainer = ((IPlayerInterface)owner).getSuitContainer();
        initInventory();
        initContainer();
    }


    private void initContainer(){
        for(int k = 0; k < 36; k++) {
            final int i= (int) Math.ceil(k / 4);
            this.addSlot(new Slot(suitContainer,  k, -((4 -( k % 4)) * 18 + 4), (int) (8 + Math.ceil(k / 4) * 19)){
                public int getMaxStackSize() {
                    return 1 ;
                }

                public boolean mayPlace(ItemStack p_39746_) {
                    return p_39746_.getItem() instanceof ArmorItem || slotRule();
                }
            });
        }
    }

    private void initInventory(){
        for(int k = 0; k < 4; ++k) {
            final EquipmentSlot equipmentslot = SLOT_IDS[k];
            this.addSlot(new Slot(inventory, 39 - k, 8, 8 + k * 18) {
                public void set(ItemStack p_219985_) {
                    ItemStack itemstack = this.getItem();
                    super.set(p_219985_);
                    inventory.player.onEquipItem(equipmentslot, itemstack, p_219985_);
                }

                public int getMaxStackSize() {
                    return 1;
                }

                public boolean mayPlace(ItemStack p_39746_) {
                    return p_39746_.canEquip(equipmentslot, inventory.player);
                }

                public boolean mayPickup(Player p_39744_) {
                    ItemStack itemstack = this.getItem();
                    return !itemstack.isEmpty() && !p_39744_.isCreative() && EnchantmentHelper.hasBindingCurse(itemstack) ? false : super.mayPickup(p_39744_);
                }

                public Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
                    return Pair.of(BLOCK_ATLAS,TEXTURE_EMPTY_SLOTS[equipmentslot.getIndex()]);
                }
            });
        }

        for(int l = 0; l < 3; ++l) {
            for(int j1 = 0; j1 < 9; ++j1) {
                this.addSlot(new Slot(inventory, j1 + (l + 1) * 9, 8 + j1 * 18, 84 + l * 18));
            }
        }

        for(int i1 = 0; i1 < 9; ++i1) {
            this.addSlot(new Slot(inventory, i1, 8 + i1 * 18, 142));
        }

        this.addSlot(new Slot(inventory, 40 , 77, 62) {
            public Pair<ResourceLocation, ResourceLocation> getNoItemIcon() {
                return Pair.of(BLOCK_ATLAS,EMPTY_ARMOR_SLOT_SHIELD);
            }
        });
    }


    @Override
    public ItemStack quickMoveStack(Player p_39723_, int p_39724_) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = this.slots.get(p_39724_);
        if (slot != null && slot.hasItem()) {
            ItemStack itemstack1 = slot.getItem();
            itemstack = itemstack1.copy();
            EquipmentSlot equipmentslot = Mob.getEquipmentSlotForItem(itemstack);
            if (p_39724_ == 0) {
                if (!this.moveItemStackTo(itemstack1, 9, 45, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onQuickCraft(itemstack1, itemstack);
            } else if (p_39724_ >= 1 && p_39724_ < 5) {
                if (!this.moveItemStackTo(itemstack1, 9, 45, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (p_39724_ >= 5 && p_39724_ < 9) {
                if (!this.moveItemStackTo(itemstack1, 9, 45, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (equipmentslot.getType() == EquipmentSlot.Type.ARMOR && !this.slots.get(8 - equipmentslot.getIndex()).hasItem()) {
                int i = 8 - equipmentslot.getIndex();
                if (!this.moveItemStackTo(itemstack1, i, i + 1, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (equipmentslot == EquipmentSlot.OFFHAND && !this.slots.get(45).hasItem()) {
                if (!this.moveItemStackTo(itemstack1, 45, 46, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (p_39724_ >= 9 && p_39724_ < 36) {
                if (!this.moveItemStackTo(itemstack1, 36, 45, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (p_39724_ >= 36 && p_39724_ < 45) {
                if (!this.moveItemStackTo(itemstack1, 9, 36, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(itemstack1, 9, 45, false)) {//except armor or crafting
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(p_39723_, itemstack1);
            if (p_39724_ == 0) {
                p_39723_.drop(itemstack1, false);
            }
        }
        return itemstack;
    }

    private boolean slotRule(){
        return false;
    }

    public boolean canTakeItemForPickAll(ItemStack p_39716_, Slot p_39717_) {
        return  super.canTakeItemForPickAll(p_39716_, p_39717_);
    }


    @Override
    public boolean stillValid(Player p_38874_) {
        return true;
    }



}
