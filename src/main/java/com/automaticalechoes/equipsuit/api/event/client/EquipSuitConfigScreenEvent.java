package com.automaticalechoes.equipsuit.api.event.client;

import com.automaticalechoes.equipsuit.api.utils.Messages;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.common.eventhandler.Event;

public class EquipSuitConfigScreenEvent extends Event {
    GuiScreen screen;

    public EquipSuitConfigScreenEvent() {
        ITextComponent translatable = new TextComponentTranslation(Messages.EDIT_TITLE);
//        this.screen = new EquipSuitClientConfigScreen(translatable);
    }

    public GuiScreen getScreen() {
        return screen;
    }

    public void setScreen(GuiScreen screen) {
        this.screen = screen;
    }
}
