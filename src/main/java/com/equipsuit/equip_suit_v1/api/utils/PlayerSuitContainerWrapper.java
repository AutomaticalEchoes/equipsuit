package com.equipsuit.equip_suit_v1.api.utils;

import com.equipsuit.equip_suit_v1.common.container.SuitContainer;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.wrapper.InvWrapper;
import net.minecraftforge.items.wrapper.RangedWrapper;

public class PlayerSuitContainerWrapper extends RangedWrapper {
    private SuitContainer suitContainer;
    public PlayerSuitContainerWrapper(SuitContainer suitContainer) {
        super(new InvWrapper(suitContainer), suitContainer.getContainerSize(), suitContainer.getContainerSize());
        this.suitContainer =suitContainer;
    }
}
