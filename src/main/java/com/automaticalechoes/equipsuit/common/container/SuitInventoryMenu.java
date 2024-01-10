package com.automaticalechoes.equipsuit.common.container;

import com.automaticalechoes.equipsuit.api.modInterfcae.player.IPlayerInterface;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.*;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.function.Supplier;

public class SuitInventoryMenu extends Container {
    public static final ResourceLocation BLOCK_ATLAS = new ResourceLocation("textures/atlas/blocks.png");
//    public static final ResourceLocation EMPTY_ARMOR_SLOT_HELMET = new ResourceLocation("item/empty_armor_slot_helmet");
//    public static final ResourceLocation EMPTY_ARMOR_SLOT_CHESTPLATE = new ResourceLocation("item/empty_armor_slot_chestplate");
//    public static final ResourceLocation EMPTY_ARMOR_SLOT_LEGGINGS = new ResourceLocation("item/empty_armor_slot_leggings");
//    public static final ResourceLocation EMPTY_ARMOR_SLOT_BOOTS = new ResourceLocation("item/empty_armor_slot_boots");
//    public static final ResourceLocation EMPTY_ARMOR_SLOT_SHIELD = new ResourceLocation("item/empty_armor_slot_shield");
    private static final EntityEquipmentSlot[] SLOT_IDS = new EntityEquipmentSlot[]{EntityEquipmentSlot.HEAD, EntityEquipmentSlot.CHEST, EntityEquipmentSlot.LEGS, EntityEquipmentSlot.FEET };
//    private static final ResourceLocation[] TEXTURE_EMPTY_SLOTS = new ResourceLocation[]{EMPTY_ARMOR_SLOT_BOOTS, EMPTY_ARMOR_SLOT_LEGGINGS, EMPTY_ARMOR_SLOT_CHESTPLATE, EMPTY_ARMOR_SLOT_HELMET};
//    private final CraftingContainer craftSlots = new CraftingContainer(this, 2, 2);
//    private final ResultContainer resultSlots = new ResultContainer();
    public InventoryCrafting craftMatrix = new InventoryCrafting(this, 2, 2);
    public InventoryCraftResult craftResult = new InventoryCraftResult();
    private final InventoryPlayer inventory;
    private final SuitContainer suitContainer;
    private final EntityPlayer owner;
    private Supplier<Boolean> slotRuleOr = () -> false;
    private Supplier<Boolean> slotRuleAnd = () -> true;

//    public static SuitInventoryMenu Create(int p_38852_, Inventory inventory) {
//        return new SuitInventoryMenu(inventory,p_38852_);
//    }

    public SuitInventoryMenu(InventoryPlayer inventory) {
//        super(ContainerRegister.SUIT_INVENTORY_MENU.get(), p_38852_);
        this.inventory = inventory;
        this.owner = inventory.player;
        this.suitContainer = ((IPlayerInterface)owner).getSuitContainer();
        initCraftSlot(inventory);
        initInventory();
        initContainer();
    }

    public SuitInventoryMenu SlotRuleOr(Supplier<Boolean> SlotRule){
        this.slotRuleOr = SlotRule;
        return this;
    }

    public SuitInventoryMenu SlotRuleAnd(Supplier<Boolean> SlotRule){
        this.slotRuleAnd = SlotRule;
        return this;
    }

    private void initCraftSlot(InventoryPlayer p_39706_){
        this.addSlotToContainer(new SlotCrafting(p_39706_.player, this.craftMatrix, this.craftResult, 0, 154, 28));

        int i1;
        int j1;
        for(i1 = 0; i1 < 2; ++i1) {
            for(j1 = 0; j1 < 2; ++j1) {
                this.addSlotToContainer(new Slot(this.craftMatrix, j1 + i1 * 2, 98 + j1 * 18, 18 + i1 * 18));
            }
        }
    }

    private void initContainer(){
        for(int k = 0; k < 36; k++) {
            final int i= (int) Math.ceil(k / 4);
            this.addSlotToContainer(new Slot(suitContainer,  k, -((4 -( k % 4)) * 18 + 4), (int) (8 + Math.ceil(k / 4) * 19) -5){
                public int getSlotStackLimit() {
                    return 1;
                }

                public boolean isItemValid(ItemStack itemStack) {
                    return slotRuleAnd.get() && (slotRuleOr.get() || itemStack.getItem() instanceof ItemArmor);
                }

                public boolean canTakeStack(EntityPlayer p_canTakeStack_1_) {
                    ItemStack itemstack = this.getStack();
                    return (itemstack.isEmpty() || p_canTakeStack_1_.isCreative() || !EnchantmentHelper.hasBindingCurse(itemstack)) && super.canTakeStack(p_canTakeStack_1_);
                }

            });
        }
    }

    private void initInventory(){
        for(int k = 0; k < 4; ++k) {
            final EntityEquipmentSlot equipmentslot = SLOT_IDS[k];
            this.addSlotToContainer(new Slot(inventory, 39 - k, 8, 8 + k * 18) {
                public int getSlotStackLimit() {
                    return 1;
                }

                public boolean isItemValid(ItemStack p_isItemValid_1_) {
                    return p_isItemValid_1_.getItem().isValidArmor(p_isItemValid_1_, equipmentslot, owner);
                }

                public boolean canTakeStack(EntityPlayer p_canTakeStack_1_) {
                    ItemStack itemstack = this.getStack();
                    return (itemstack.isEmpty() || p_canTakeStack_1_.isCreative() || !EnchantmentHelper.hasBindingCurse(itemstack)) && super.canTakeStack(p_canTakeStack_1_);
                }

                @Nullable
                @SideOnly(Side.CLIENT)
                public String getSlotTexture() {
                    return ItemArmor.EMPTY_SLOT_NAMES[equipmentslot.getIndex()];
                }
            });

        }

        for(int l = 0; l < 3; ++l) {
            for(int j1 = 0; j1 < 9; ++j1) {
                this.addSlotToContainer(new Slot(inventory, j1 + (l + 1) * 9, 8 + j1 * 18, 84 + l * 18));
            }
        }

        for(int i1 = 0; i1 < 9; ++i1) {
            this.addSlotToContainer(new Slot(inventory, i1, 8 + i1 * 18, 142));
        }

        this.addSlotToContainer(new Slot(inventory, 40, 77, 62) {
            @Nullable
            @SideOnly(Side.CLIENT)
            public String getSlotTexture() {
                return "minecraft:items/empty_armor_slot_shield";
            }
        });
    }


    public void onCraftMatrixChanged(IInventory p_onCraftMatrixChanged_1_) {
        this.slotChangedCraftingGrid(this.owner.world, this.owner, this.craftMatrix, this.craftResult);
    }

    public void onContainerClosed(EntityPlayer p_onContainerClosed_1_) {
        super.onContainerClosed(p_onContainerClosed_1_);
        this.craftResult.clear();
        if (!p_onContainerClosed_1_.world.isRemote) {
            this.clearContainer(p_onContainerClosed_1_, p_onContainerClosed_1_.world, this.craftMatrix);
        }

    }

    public boolean canInteractWith(EntityPlayer p_canInteractWith_1_) {
        return true;
    }

    public ItemStack transferStackInSlot(EntityPlayer p_transferStackInSlot_1_, int p_transferStackInSlot_2_) {
        ItemStack itemstack = ItemStack.EMPTY;
        Slot slot = (Slot)this.inventorySlots.get(p_transferStackInSlot_2_);
        if (slot != null && slot.getHasStack()) {
            ItemStack itemstack1 = slot.getStack();
            itemstack = itemstack1.copy();
            EntityEquipmentSlot entityequipmentslot = EntityLiving.getSlotForItemStack(itemstack);
            if (p_transferStackInSlot_2_ == 0) {
                if (!this.mergeItemStack(itemstack1, 9, 45, true)) {
                    return ItemStack.EMPTY;
                }

                slot.onSlotChange(itemstack1, itemstack);
            } else if (p_transferStackInSlot_2_ >= 1 && p_transferStackInSlot_2_ < 5) {
                if (!this.mergeItemStack(itemstack1, 9, 45, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (p_transferStackInSlot_2_ >= 5 && p_transferStackInSlot_2_ < 9) {
                if (!this.mergeItemStack(itemstack1, 9, 45, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (entityequipmentslot.getSlotType() == EntityEquipmentSlot.Type.ARMOR && !((Slot)this.inventorySlots.get(8 - entityequipmentslot.getIndex())).getHasStack()) {
                int i = 8 - entityequipmentslot.getIndex();
                if (!this.mergeItemStack(itemstack1, i, i + 1, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (entityequipmentslot == EntityEquipmentSlot.OFFHAND && !((Slot)this.inventorySlots.get(45)).getHasStack()) {
                if (!this.mergeItemStack(itemstack1, 45, 46, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (p_transferStackInSlot_2_ >= 9 && p_transferStackInSlot_2_ < 36) {
                if (!this.mergeItemStack(itemstack1, 36, 45, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (p_transferStackInSlot_2_ >= 36 && p_transferStackInSlot_2_ < 45) {
                if (!this.mergeItemStack(itemstack1, 9, 36, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.mergeItemStack(itemstack1, 9, 45, false)) {
                return ItemStack.EMPTY;
            }

            if (itemstack1.isEmpty()) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }

            if (itemstack1.getCount() == itemstack.getCount()) {
                return ItemStack.EMPTY;
            }

            ItemStack itemstack2 = slot.onTake(p_transferStackInSlot_1_, itemstack1);
            if (p_transferStackInSlot_2_ == 0) {
                p_transferStackInSlot_1_.dropItem(itemstack2, false);
            }
        }

        return itemstack;
    }

    public boolean canMergeSlot(ItemStack p_canMergeSlot_1_, Slot p_canMergeSlot_2_) {
        return p_canMergeSlot_2_.inventory != this.craftResult && super.canMergeSlot(p_canMergeSlot_1_, p_canMergeSlot_2_);
    }
}
