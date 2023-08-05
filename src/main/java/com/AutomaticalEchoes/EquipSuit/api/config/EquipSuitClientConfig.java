package com.AutomaticalEchoes.EquipSuit.api.config;

import net.minecraftforge.common.ForgeConfigSpec;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class EquipSuitClientConfig {
    public static final ForgeConfigSpec.Builder BUILDER=new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;
    public static final ForgeConfigSpec.ConfigValue<Integer> CHANGE_MODE;
    public static final ForgeConfigSpec.ConfigValue<Integer> ALPHA;
    public static final ForgeConfigSpec.ConfigValue<Integer> HUD_MODE;
    public static final ForgeConfigSpec.ConfigValue<Integer> LAYER_START_X;
    public static final ForgeConfigSpec.ConfigValue<Integer> LAYER_START_Y;
    public static final ForgeConfigSpec.ConfigValue<List<? extends String>> SUIT_NAME;
    static {
        Predicate<Object> StringPredicate = strings -> strings != null && strings.toString().length() < 10;
        List<String> SuitName = Arrays.asList("Suit_I","Suit_II","Suit_III","Suit_IV");
        BUILDER.push("equipsuit client config");
        ALPHA = BUILDER.defineInRange("hud alpha",20,0,100);
        CHANGE_MODE = BUILDER.comment("base suit change mode: \n 0 : Sequence \n 1 : Quick select").defineInRange("change mode",0,0,1);
        HUD_MODE =  BUILDER.comment("base hud show mode: \n 0 : Simple \n 1 : ALL").defineInRange("hud mode",1,0,1);
        LAYER_START_X = BUILDER.defineInRange("hud layer start x",0,0,400);
        LAYER_START_Y = BUILDER.defineInRange("hud layer start y",40,28,400);
        SUIT_NAME = BUILDER.comment("length < 10").defineList("suits‘ name",SuitName,StringPredicate);
        BUILDER.pop();
        SPEC=BUILDER.build();
    }
}
