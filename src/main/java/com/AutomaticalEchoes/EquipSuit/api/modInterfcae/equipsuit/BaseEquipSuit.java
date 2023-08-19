package com.AutomaticalEchoes.EquipSuit.api.modInterfcae.equipsuit;

import com.AutomaticalEchoes.EquipSuit.api.event.server.EquipSuitMapRegister;
import com.AutomaticalEchoes.EquipSuit.api.modInterfcae.baseSlot.BaseSlot;
import com.AutomaticalEchoes.EquipSuit.api.modInterfcae.baseSlot.EquipSlots;
import net.minecraftforge.common.MinecraftForge;

import java.util.HashMap;

public class BaseEquipSuit {
    public static HashMap<String, BaseSlot> BASE_INVENTORY_MAP;
    public static HashMap<String, BaseSlot> BASE_SUIT_MAP;
    public static int Size;
    public static void Init(){
        BASE_INVENTORY_MAP = new HashMap<>();
        BASE_INVENTORY_MAP.put("head",EquipSlots.INVENTORY_HEAD);
        BASE_INVENTORY_MAP.put("chest",EquipSlots.INVENTORY_CHEST);
        BASE_INVENTORY_MAP.put("leg",EquipSlots.INVENTORY_LEG);
        BASE_INVENTORY_MAP.put("feet",EquipSlots.INVENTORY_FEET);
        EquipSuitMapRegister.Right event = new EquipSuitMapRegister.Right(BASE_INVENTORY_MAP);
        MinecraftForge.EVENT_BUS.post(event);
        BASE_INVENTORY_MAP = event.getMap();

        Size = BASE_INVENTORY_MAP.size();

        BASE_SUIT_MAP = new HashMap<>();
        BASE_SUIT_MAP.put("head",EquipSlots.SUIT_HEAD);
        BASE_SUIT_MAP.put("chest",EquipSlots.SUIT_CHEST);
        BASE_SUIT_MAP.put("leg",EquipSlots.SUIT_LEG);
        BASE_SUIT_MAP.put("feet",EquipSlots.SUIT_FEET);
        EquipSuitMapRegister.Left event1 = new EquipSuitMapRegister.Left(BASE_SUIT_MAP);
        MinecraftForge.EVENT_BUS.post(event);
        BASE_SUIT_MAP = event1.getMap();

    }
}
