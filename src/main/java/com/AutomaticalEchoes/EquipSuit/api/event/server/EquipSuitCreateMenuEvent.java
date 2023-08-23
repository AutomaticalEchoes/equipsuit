package com.AutomaticalEchoes.EquipSuit.api.event.server;


import net.minecraft.world.MenuProvider;
import net.minecraftforge.eventbus.api.Event;

public class EquipSuitCreateMenuEvent extends Event {
    private MenuProvider menuProvider;

    public EquipSuitCreateMenuEvent(MenuProvider menuProvider) {
        this.menuProvider = menuProvider;
    }

    public void setMenuProvider(MenuProvider menuProvider) {
        this.menuProvider = menuProvider;
    }

    public MenuProvider getMenuProvider() {
        return menuProvider;
    }
}
