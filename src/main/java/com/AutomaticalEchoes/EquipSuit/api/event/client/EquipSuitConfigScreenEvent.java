package com.AutomaticalEchoes.EquipSuit.api.event.client;

import com.AutomaticalEchoes.EquipSuit.api.utils.Messages;
import com.AutomaticalEchoes.EquipSuit.client.screen.EquipSuitClientConfigScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.TextComponent;
import net.minecraftforge.eventbus.api.Event;

public class EquipSuitConfigScreenEvent extends Event {
    Screen screen;

    public EquipSuitConfigScreenEvent() {
        MutableComponent translatable = new TextComponent(Messages.EDIT_TITLE);
        this.screen = new EquipSuitClientConfigScreen(translatable);
    }

    public Screen getScreen() {
        return screen;
    }

    public void setScreen(Screen screen) {
        this.screen = screen;
    }
}
