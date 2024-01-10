package com.automaticalechoes.equipsuit.api.utils;


import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.text.TextFormatting;

public class Messages {
    public static final String[] SUIT_NUM = {I18n.format("text.message.equipsuit.suit_1"),
            I18n.format("text.message.equipsuit.suit_2"),
            I18n.format("text.message.equipsuit.suit_3"),
            I18n.format("text.message.equipsuit.suit_4")};
    public static final String[] MODE_NAME = {I18n.format("text.message.equipsuit.mode_sequence"),
            I18n.format("text.message.equipsuit.mode_quickselect")};
    public static final String[] PART_NAME ={I18n.format("text.message.equipsuit.part_name_1"),
            I18n.format("text.message.equipsuit.part_name_2"),
            I18n.format("text.message.equipsuit.part_name_3"),
            I18n.format("text.message.equipsuit.part_name_4")};;
    public static final String NO_CLICK_RESULT =I18n.format("text.message.equipsuit.no_click_result");
    public static final String NO_CLICK_RESULT_1 =I18n.format("text.message.equipsuit.no_click_result_1");
    public static final String EDIT_TITLE = I18n.format("text.tag.equipsuit.edit_title");
    public static final TextFormatting[] SUIT_NUM_COLORS_FORMAT = {TextFormatting.GREEN, TextFormatting.RED, TextFormatting.AQUA, TextFormatting.YELLOW};
    public static final int[] SUIT_NUM_COLORS = {1898002,16136466,1223670,16184082};
    public static final String TAG_MODE = MessageTag("text.tag.equipsuit.mode");
    public static final String TAG_SUIT = MessageTag("text.tag.equipsuit.suit");
    public static final String TAG_EDITING = MessageTag("text.tag.equipsuit.editing");
    public static final String TAG_PART = MessageTag("text.tag.equipsuit.part");
    public static final String TAG_WARNING = MessageTag("text.tag.equipsuit.warning");
    public static final String TAG_ENABLE_SIMPLE_HUD = "text.tag.equipsuit.enable_simple_hud";
    public static final String TAG_ENABLE_QUICK_SELECT_MODE = "text.tag.equipsuit.enable_quick_select_mode";
    public static final String TAG_GUI_COORDINATE_X = "text.tag.equipsuit.coordinate_x";
    public static final String TAG_GUI_COORDINATE_Y = "text.tag.equipsuit.coordinate_y";
    public static final String TAG_GUI_ALPHA = "text.tag.equipsuit.alpha";
    public static final TextComponentTranslation MODE_CHANGE_MESSAGE_0 = new TextComponentTranslation(Messages.TAG_MODE + Messages.MODE_NAME[0]);
    public static final TextComponentTranslation MODE_CHANGE_MESSAGE_1 = new TextComponentTranslation(Messages.TAG_MODE + Messages.MODE_NAME[1]);
    public static final TextComponentTranslation[] MODE_CHANGE_MESSAGE = {MODE_CHANGE_MESSAGE_0,MODE_CHANGE_MESSAGE_1};
    public static String MessageTag(String s){
        return I18n.format(s,0) + ": ";
    }
}
