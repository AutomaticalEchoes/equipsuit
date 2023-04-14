package com.equipsuit.equip_suit_v1.api.config;

import net.minecraftforge.common.ForgeConfigSpec;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class EquipSuitClientConfig {
    public static final ForgeConfigSpec.Builder BUILDER=new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;
    public static final ForgeConfigSpec.ConfigValue<Integer> CHANGE_MODE;
    static {
        BUILDER.push("equipsuit client config");
        CHANGE_MODE = BUILDER.comment("base suit change mode: \n 0 : Sequence \n 1 : Quick select").defineInRange("change mode",0,0,1);
        BUILDER.pop();
        SPEC=BUILDER.build();
    }
}
