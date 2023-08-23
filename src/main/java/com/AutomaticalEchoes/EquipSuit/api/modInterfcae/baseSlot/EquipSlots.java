package com.AutomaticalEchoes.EquipSuit.api.modInterfcae.baseSlot;

import com.AutomaticalEchoes.EquipSuit.api.modInterfcae.containerType.ContainerTypes;

public class EquipSlots {
    public static final BaseSlot INVENTORY_HEAD = new EquipSlot(ContainerTypes.TYPE_INVENTORY,39);
    public static final BaseSlot INVENTORY_CHEST = new EquipSlot(ContainerTypes.TYPE_INVENTORY,38);
    public static final BaseSlot INVENTORY_LEG =  new EquipSlot(ContainerTypes.TYPE_INVENTORY,37);
    public static final BaseSlot INVENTORY_FEET =  new EquipSlot(ContainerTypes.TYPE_INVENTORY,36);
    public static final BaseSlot SUIT_HEAD = new EquipSlot(ContainerTypes.TYPE_SUIT ,0);
    public static final BaseSlot SUIT_CHEST = new EquipSlot(ContainerTypes.TYPE_SUIT,1);
    public static final BaseSlot SUIT_LEG = new EquipSlot(ContainerTypes.TYPE_SUIT,2);
    public static final BaseSlot SUIT_FEET = new EquipSlot(ContainerTypes.TYPE_SUIT,3);

}
