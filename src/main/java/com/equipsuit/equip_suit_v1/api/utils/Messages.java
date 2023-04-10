package com.equipsuit.equip_suit_v1.api.utils;

import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;

public class Messages {
    public static final String[] SUIT_TAG = {"I","II","III","IV"};
    public static final int[] SUIT_TAG_COLORS={1898002,16136466,1223670,16184082};
    public static final String MODE_TAG = I18n.get("message.equipsuit.mod_tag") + " : ";
    public static final String[] MODE_NAME = {I18n.get("message.equipsuit.mod_sequence "),I18n.get("message.equipsuit.mod_quickselect")};
}
