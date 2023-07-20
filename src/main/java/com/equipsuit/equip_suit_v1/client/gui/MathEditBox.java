package com.equipsuit.equip_suit_v1.client.gui;

import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;

public class MathEditBox extends EditBox {
    public MathEditBox(Font p_94106_, int p_94107_, int p_94108_, int p_94109_, int p_94110_, @Nullable EditBox p_94111_, Component p_94112_) {
        super(p_94106_, p_94107_, p_94108_, p_94109_, p_94110_, p_94111_, p_94112_);
        this.setMaxLength(3);
        this.setFilter(MathEditBox::matchString);
    }

    public static boolean matchString(String s){
        return s.matches("\\d+") || s.equals("");
    }
}
