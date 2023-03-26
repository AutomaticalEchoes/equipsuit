package com.equipsuit.equip_suit_v1.api.ModInterfcae.player;

import com.equipsuit.equip_suit_v1.api.ModInterfcae.equipsuit.ContainerEquipSuit;
import net.minecraft.core.NonNullList;

public interface SuitStack {
    NonNullList<ContainerEquipSuit> getSuitArrayList();
}
