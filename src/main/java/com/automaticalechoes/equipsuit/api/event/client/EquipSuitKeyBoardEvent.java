package com.automaticalechoes.equipsuit.api.event.client;


import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.fml.common.eventhandler.Event;

public class EquipSuitKeyBoardEvent extends Event {
    KeyBinding keyMapping ;
    //    client level
    public EquipSuitKeyBoardEvent(KeyBinding keyMapping) {
        this.keyMapping = keyMapping;
    }

    @Override
    public boolean isCancelable() {
        return true;
    }

    public KeyBinding getKeyMapping() {
        return keyMapping;
    }
}
