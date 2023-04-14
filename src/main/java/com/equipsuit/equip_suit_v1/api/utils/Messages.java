package com.equipsuit.equip_suit_v1.api.utils;

import net.minecraft.client.resources.language.I18n;
import net.minecraft.network.chat.Component;

public class Messages {
    public static final String[] SUIT_NUM = {I18n.get("text.message.equipsuit.suit_1"),
            I18n.get("text.message.equipsuit.suit_2"),
            I18n.get("text.message.equipsuit.suit_3"),
            I18n.get("text.message.equipsuit.suit_4")};
    public static final String[] MODE_NAME = {I18n.get("text.message.equipsuit.mode_sequence"),
            I18n.get("text.message.equipsuit.mode_quickselect")};
    public static final String[] PART ={"H","B","L","F"};
    public static final String[] PART_NAME ={I18n.get("text.message.equipsuit.part_name_1"),
            I18n.get("text.message.equipsuit.part_name_2"),
            I18n.get("text.message.equipsuit.part_name_3"),
            I18n.get("text.message.equipsuit.part_name_4")};;
    public static final String NO_CLICK_RESULT =I18n.get("text.message.equipsuit.no_click_result");
    public static final String NO_CLICK_RESULT_1 =I18n.get("text.message.equipsuit.no_click_result_1");
    public static final int[] SUIT_NUM_COLORS={1898002,16136466,1223670,16184082};
    public static final String TAG_MODE = MessageTag("text.tag.equipsuit.mode");
    public static final String TAG_SUIT = MessageTag("text.tag.equipsuit.suit");
    public static final String TAG_EDITING = MessageTag("text.tag.equipsuit.editing");
    public static final String TAG_PART = MessageTag("text.tag.equipsuit.part");
    public static final String TAG_WARNING = MessageTag("text.tag.equipsuit.warning");
    public static String MessageTag(String s){
        return I18n.get(s) + ": ";
    }
}
