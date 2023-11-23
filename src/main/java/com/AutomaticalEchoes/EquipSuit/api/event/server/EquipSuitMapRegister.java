package com.AutomaticalEchoes.EquipSuit.api.event.server;

import com.AutomaticalEchoes.EquipSuit.api.modInterfcae.baseSlot.BaseSlot;
import net.minecraftforge.eventbus.api.Event;

import java.util.HashMap;

public class EquipSuitMapRegister extends Event {
    private final HashMap<String, BaseSlot> map;

    public EquipSuitMapRegister(HashMap<String, BaseSlot> map) {
       this.map = map;
    }

    public void register(String s,BaseSlot slot){
        map.put(s,slot);
    }

    public HashMap<String, BaseSlot> getMap() {
        return map;
    }

    public static class Left extends EquipSuitMapRegister{

        public Left(HashMap<String, BaseSlot> map) {
            super(map);
        }
    }

    public static class Right extends EquipSuitMapRegister{

        public Right(HashMap<String, BaseSlot> map) {
            super(map);
        }
    }
}
