package com.AutomaticalEchoes.EquipSuit.api.utils;

import com.AutomaticalEchoes.EquipSuit.api.event.server.EquipSuitMapRegister;
import com.AutomaticalEchoes.EquipSuit.api.modInterfcae.baseSlot.BaseSlot;
import com.AutomaticalEchoes.EquipSuit.api.modInterfcae.baseSlot.EquipSlots;
import net.minecraftforge.common.MinecraftForge;

import java.util.HashMap;
import java.util.Map;

public class EquipSuitTemplate {
    public static HashMap<String, BaseSlot> BASE_INVENTORY_MAP;
    public static HashMap<String, BaseSlot> BASE_SUIT_MAP;
    public static final String[] NAME = {"SUIT_I","SUIT_II","SUIT_III","SUIT_IV"};
    public static final String[] INDEX_KEY = {"head","chest","leg","feet"};
    public static final Map<String,Integer> KEY_INDEX = Map.of("head",0,"chest",1,"leg",2,"feet",3);
    public static final String[] PART = {"H","C","L","F"};
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
