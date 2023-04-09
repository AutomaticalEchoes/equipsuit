package com.equipsuit.equip_suit_v1.api.utils;

import com.equipsuit.equip_suit_v1.common.container.SuitContainer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.wrapper.InvWrapper;
import net.minecraftforge.items.wrapper.RangedWrapper;
import org.jetbrains.annotations.NotNull;

public class PlayerSuitContainerWrapper extends RangedWrapper {
    private SuitContainer suitContainer;
    public PlayerSuitContainerWrapper(SuitContainer suitContainer) {
        super(new InvWrapper(suitContainer), 0, suitContainer.getContainerSize());
        this.suitContainer =suitContainer;
    }
    @Override
    @NotNull
    public ItemStack insertItem(int slot, @NotNull ItemStack stack, boolean simulate)
    {
        ItemStack rest = super.insertItem(slot, stack, simulate);
        if (rest.getCount()!= stack.getCount())
        {
            // the stack in the slot changed, animate it
            ItemStack inSlot = getStackInSlot(slot);
            if(!inSlot.isEmpty())
            {
                if (getSuitContainer().player.level.isClientSide)
                {
                    inSlot.setPopTime(5);
                }
                else if(getSuitContainer().player instanceof ServerPlayer) {
                    getSuitContainer().player.containerMenu.broadcastChanges();
                }
            }
        }
        return rest;
    }
    public SuitContainer getSuitContainer(){
        return this.suitContainer;
    }
}
