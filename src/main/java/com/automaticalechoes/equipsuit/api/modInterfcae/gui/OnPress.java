package com.automaticalechoes.equipsuit.api.modInterfcae.gui;

import net.minecraft.client.gui.GuiButton;

public interface OnPress<T extends GuiButton> {
    void action(T t);
}
