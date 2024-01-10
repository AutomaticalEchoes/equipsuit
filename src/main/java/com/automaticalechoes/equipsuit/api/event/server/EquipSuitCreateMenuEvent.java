package com.automaticalechoes.equipsuit.api.event.server;


import net.minecraftforge.fml.common.eventhandler.Event;

public class EquipSuitCreateMenuEvent extends Event {
    private String modId;
    private int code;

    public EquipSuitCreateMenuEvent(String modId, int code) {
        this.modId = modId;
        this.code = code;
    }

    public String getModId() {
        return modId;
    }

    public void setModId(String modId) {
        this.modId = modId;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
