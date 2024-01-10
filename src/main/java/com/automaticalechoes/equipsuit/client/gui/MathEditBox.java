package com.automaticalechoes.equipsuit.client.gui;

import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiTextField;

import java.util.function.Consumer;

public class MathEditBox extends GuiTextField {
    private Consumer<Boolean> onFocusChange = o -> {};
    public MathEditBox(int id, FontRenderer p_94106_, int p_94107_, int p_94108_, int p_94109_, int p_94110_) {
        super(id, p_94106_, p_94107_, p_94108_, p_94109_, p_94110_);
//        this.setMaxLength(3);
        this.setValidator(MathEditBox::matchString);
    }


    @Override
    public void writeText(String p_writeText_1_) {
        super.writeText(p_writeText_1_);
    }

    public void setOnFocusChange(Consumer<Boolean> runnable){
        this.onFocusChange = runnable;
    }

    @Override
    public void setFocused(boolean p_setFocused_1_) {
        super.setFocused(p_setFocused_1_);
        onFocusChange.accept(p_setFocused_1_);
    }

    public static boolean matchString(String s){
        return s.matches("\\d+") || s.equals("");
    }


}
