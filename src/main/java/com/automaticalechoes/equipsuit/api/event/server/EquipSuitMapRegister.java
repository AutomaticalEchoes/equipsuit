package com.automaticalechoes.equipsuit.api.event.server;

import com.automaticalechoes.equipsuit.api.modInterfcae.baseSlot.BaseSlot;
import net.minecraftforge.fml.common.eventhandler.Event;

import java.util.HashMap;

public class EquipSuitMapRegister extends Event {
    private final HashMap<String, BaseSlot> equip;
    private final HashMap<String, BaseSlot> container;

    public EquipSuitMapRegister(HashMap<String, BaseSlot> equip , HashMap<String, BaseSlot> container) {
       this.equip = equip;
       this.container = container;
    }

    public HashMap<String, BaseSlot> getContainer() {
        return container;
    }

    public HashMap<String, BaseSlot> getEquip() {
        return equip;
    }

    //example("chest",EquipSlots.INVENTORY_CHEST,EquipSlots.SUIT_CHEST);
    public void Register(String name , BaseSlot equip, BaseSlot container){
        this.equip.put(name,equip);
        this.container.put(name,container);
    }
}
