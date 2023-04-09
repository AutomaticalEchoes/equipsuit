package com.equipsuit.equip_suit_v1.api.utils;

import com.equipsuit.equip_suit_v1.common.container.SuitContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;
import net.minecraftforge.items.wrapper.PlayerArmorInvWrapper;
import net.minecraftforge.items.wrapper.PlayerMainInvWrapper;
import net.minecraftforge.items.wrapper.PlayerOffhandInvWrapper;

public class IPlayerInvWrapper extends CombinedInvWrapper {

    public IPlayerInvWrapper(Inventory inv , SuitContainer suitContainer)
    {
        super(new PlayerMainInvWrapper(inv), new PlayerArmorInvWrapper(inv), new PlayerOffhandInvWrapper(inv),new PlayerSuitContainerWrapper(suitContainer));
    }
}
