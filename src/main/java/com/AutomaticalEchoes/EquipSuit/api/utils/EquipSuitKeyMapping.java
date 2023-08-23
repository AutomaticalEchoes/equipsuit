package com.AutomaticalEchoes.EquipSuit.api.utils;

import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.client.settings.KeyModifier;

import java.util.ArrayList;

public class EquipSuitKeyMapping {
    public static final ArrayList<KeyMapping> KEY_MAPPING = new ArrayList<>();

    public static final KeyMapping CALL_SUIT_INVENTORY_KEY = RegisterKeyMapping(new KeyMapping("key.category.equipsuit.suitinvetory",
            KeyConflictContext.IN_GAME,
            KeyModifier.NONE,
            InputConstants.getKey(InputConstants.KEY_B,0 ),
            "key.equipsuit"));
    public static final KeyMapping CALL_SUIT_SETTING = RegisterKeyMapping(new KeyMapping("key.category.equipsuit.setting",
            KeyConflictContext.IN_GAME,
            KeyModifier.CONTROL,
            InputConstants.getKey(InputConstants.KEY_F,0 ),
            "key.equipsuit"));
    public static final KeyMapping SUIT_CHANGE = RegisterKeyMapping(new KeyMapping("key.category.equipsuit.suitchange",
            KeyConflictContext.IN_GAME,
            KeyModifier.NONE,
            InputConstants.getKey(InputConstants.KEY_R,0 ),
            "key.equipsuit"));
    public static final KeyMapping MODE_CHANGE = RegisterKeyMapping(new KeyMapping("key.category.equipsuit.modechange",
            KeyConflictContext.IN_GAME,
            KeyModifier.CONTROL,
            InputConstants.getKey(InputConstants.KEY_R,0 ),
            "key.equipsuit"));
    public static final KeyMapping SELECT_SUIT_CHANGE_I = RegisterKeyMapping(new KeyMapping("key.category.equipsuit.suitchange.select_1",
            KeyConflictContext.IN_GAME,
            KeyModifier.CONTROL,
            InputConstants.getKey(InputConstants.KEY_1,0 ),
            "key.equipsuit"));
    public static final KeyMapping SELECT_SUIT_CHANGE_II = RegisterKeyMapping(new KeyMapping("key.category.equipsuit.suitchange.select_2",
            KeyConflictContext.IN_GAME,
            KeyModifier.CONTROL,
            InputConstants.getKey(InputConstants.KEY_2,0 ),
            "key.equipsuit"));
    public static final KeyMapping SELECT_SUIT_CHANGE_III = RegisterKeyMapping(new KeyMapping("key.category.equipsuit.suitchange.select_3",
            KeyConflictContext.IN_GAME,
            KeyModifier.CONTROL,
            InputConstants.getKey(InputConstants.KEY_3,0 ),
            "key.equipsuit"));
    public static final KeyMapping SELECT_SUIT_CHANGE_IV = RegisterKeyMapping( new KeyMapping("key.category.equipsuit.suitchange.select_4",
            KeyConflictContext.IN_GAME,
            KeyModifier.CONTROL,
            InputConstants.getKey(InputConstants.KEY_4,0 ),
            "key.equipsuit"));

    public static KeyMapping RegisterKeyMapping(KeyMapping keyMapping){
        KEY_MAPPING.add(keyMapping);
        return keyMapping;
    }
}
