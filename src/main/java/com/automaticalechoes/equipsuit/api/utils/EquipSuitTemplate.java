package com.automaticalechoes.equipsuit.api.utils;

import com.automaticalechoes.equipsuit.api.event.server.EquipSuitMapRegister;
import com.automaticalechoes.equipsuit.api.modInterfcae.baseSlot.BaseSlot;
import com.automaticalechoes.equipsuit.api.modInterfcae.baseSlot.EquipSlots;
import net.minecraftforge.common.MinecraftForge;

import java.util.HashMap;
import java.util.Map;

public class EquipSuitTemplate {
    public static HashMap<String, BaseSlot> BASE_EQUIP_MAP;
    public static HashMap<String, BaseSlot> BASE_SUIT_CONTAINER_MAP;
    public static final String[] NAME = {"SUIT_I","SUIT_II","SUIT_III","SUIT_IV"};
    public static final String[] INDEX_KEY = {"head","chest","leg","feet"};
    public static final Map<String,Integer> KEY_INDEX = new HashMap<>();
    public static final String[] PART = {"H","C","L","F"};
    public static int Size;
    public static void Init(){
        KEY_INDEX.put("head",0);
        KEY_INDEX.put("chest",1);
        KEY_INDEX.put("leg",2);
        KEY_INDEX.put("feet",3);
        BASE_EQUIP_MAP = new HashMap<>();
        BASE_EQUIP_MAP.put("head",EquipSlots.INVENTORY_HEAD);
        BASE_EQUIP_MAP.put("chest",EquipSlots.INVENTORY_CHEST);
        BASE_EQUIP_MAP.put("leg",EquipSlots.INVENTORY_LEG);
        BASE_EQUIP_MAP.put("feet",EquipSlots.INVENTORY_FEET);

        Size = BASE_EQUIP_MAP.size();

        BASE_SUIT_CONTAINER_MAP = new HashMap<>();
        BASE_SUIT_CONTAINER_MAP.put("head",EquipSlots.SUIT_HEAD);
        BASE_SUIT_CONTAINER_MAP.put("chest",EquipSlots.SUIT_CHEST);
        BASE_SUIT_CONTAINER_MAP.put("leg",EquipSlots.SUIT_LEG);
        BASE_SUIT_CONTAINER_MAP.put("feet",EquipSlots.SUIT_FEET);
        EquipSuitMapRegister event = new EquipSuitMapRegister(BASE_EQUIP_MAP,BASE_SUIT_CONTAINER_MAP);
        MinecraftForge.EVENT_BUS.post(event);

        BASE_EQUIP_MAP = event.getEquip();
        BASE_SUIT_CONTAINER_MAP = event.getContainer();


    }
}
