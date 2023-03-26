package com.equipsuit.equip_suit_v1.api.ModInterfcae.player;

import com.equipsuit.equip_suit_v1.api.ModInterfcae.equipsuit.ContainerEquipSuit;
import com.equipsuit.equip_suit_v1.common.container.SuitContainer;
import net.minecraft.core.NonNullList;

public class SuitStackImpl implements SuitStack{
    private final NonNullList<ContainerEquipSuit> suitArrayList;

    public SuitStackImpl(SuitContainer suitContainer) {
        suitArrayList = NonNullList.withSize(4,ContainerEquipSuit.build(suitContainer));
    }

    public NonNullList<ContainerEquipSuit> getSuitArrayList() {
        return suitArrayList;
    }
}
