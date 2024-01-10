package com.automaticalechoes.equipsuit.api.utils;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.client.settings.KeyModifier;
import org.lwjgl.input.Keyboard;

import java.util.ArrayList;

public class EquipSuitKeyMapping {
    public static final ArrayList<KeyBinding> KEY_MAPPING = new ArrayList<>();

    public static final KeyBinding CALL_SUIT_INVENTORY_KEY = RegisterKeyBinding(new KeyBinding("key.category.equipsuit.suitinvetory",
            KeyConflictContext.IN_GAME,
            KeyModifier.NONE,
            Keyboard.KEY_B,
            "key.equipsuit"));
//    public static final KeyBinding CALL_SUIT_SETTING = RegisterKeyBinding(new KeyBinding("key.category.equipsuit.setting",
//            KeyConflictContext.IN_GAME,
//            KeyModifier.CONTROL,
//            Keyboard.KEY_F,
//            "key.equipsuit"));
    public static final KeyBinding SUIT_CHANGE = RegisterKeyBinding(new KeyBinding("key.category.equipsuit.suitchange",
            KeyConflictContext.IN_GAME,
            KeyModifier.NONE,
            Keyboard.KEY_R,
            "key.equipsuit"));
    public static final KeyBinding MODE_CHANGE = RegisterKeyBinding(new KeyBinding("key.category.equipsuit.modechange",
            KeyConflictContext.IN_GAME,
            KeyModifier.CONTROL,
            Keyboard.KEY_C,
            "key.equipsuit"));
    public static final KeyBinding SELECT_SUIT_CHANGE_I = RegisterKeyBinding(new KeyBinding("key.category.equipsuit.suitchange.select_1",
            KeyConflictContext.IN_GAME,
            KeyModifier.CONTROL,
            Keyboard.KEY_1,
            "key.equipsuit"));
    public static final KeyBinding SELECT_SUIT_CHANGE_II = RegisterKeyBinding(new KeyBinding("key.category.equipsuit.suitchange.select_2",
            KeyConflictContext.IN_GAME,
            KeyModifier.CONTROL,
            Keyboard.KEY_2,
            "key.equipsuit"));
    public static final KeyBinding SELECT_SUIT_CHANGE_III = RegisterKeyBinding(new KeyBinding("key.category.equipsuit.suitchange.select_3",
            KeyConflictContext.IN_GAME,
            KeyModifier.CONTROL,
            Keyboard.KEY_3,
            "key.equipsuit"));
    public static final KeyBinding SELECT_SUIT_CHANGE_IV = RegisterKeyBinding( new KeyBinding("key.category.equipsuit.suitchange.select_4",
            KeyConflictContext.IN_GAME,
            KeyModifier.CONTROL,
            Keyboard.KEY_4,
            "key.equipsuit"));

    public static KeyBinding RegisterKeyBinding(KeyBinding KeyBinding){
        KEY_MAPPING.add(KeyBinding);
        return KeyBinding;
    }
}
