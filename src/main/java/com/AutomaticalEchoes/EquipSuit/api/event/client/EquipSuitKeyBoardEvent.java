package com.AutomaticalEchoes.EquipSuit.api.event.client;


import net.minecraft.client.KeyMapping;
import net.minecraftforge.eventbus.api.Event;

public class EquipSuitKeyBoardEvent extends Event {
    KeyMapping keyMapping ;

    //    client level
    public EquipSuitKeyBoardEvent(KeyMapping keyMapping) {
        this.keyMapping = keyMapping;
    }

    @Override
    public boolean isCancelable() {
        return true;
    }

    public KeyMapping getKeyMapping() {
        return keyMapping;
    }
}
