package com.AutomaticalEchoes.EquipSuit.client.gui;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.Component;

import javax.annotation.Nullable;
import java.util.function.Consumer;

public class MathEditBox extends EditBox {
    private Consumer<Boolean> onFocusChange = o -> {};
    public MathEditBox(Font p_94106_, int p_94107_, int p_94108_, int p_94109_, int p_94110_, @Nullable EditBox p_94111_, Component p_94112_) {
        super(p_94106_, p_94107_, p_94108_, p_94109_, p_94110_, p_94111_, p_94112_);
        this.setMaxLength(3);
        this.setFilter(MathEditBox::matchString);
    }

    public void setOnFocusChange(Consumer<Boolean> runnable){
        this.onFocusChange = runnable;
    }

    @Override
    public void setFocus(boolean p_94179_) {
        super.setFocus(p_94179_);
        onFocusChange.accept(p_94179_);
    }

    public static boolean matchString(String s){
        return s.matches("\\d+") || s.equals("");
    }


}
