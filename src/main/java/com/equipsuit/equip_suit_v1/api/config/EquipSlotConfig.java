package com.equipsuit.equip_suit_v1.api.config;

import net.minecraftforge.common.ForgeConfigSpec;

import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

public class EquipSlotConfig {
    public static final ForgeConfigSpec.Builder BUILDER=new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;
    public static final ForgeConfigSpec.ConfigValue<List<? extends Integer>> EQUIP_SLOT_LIST;
    static {
        BUILDER.push("equip slot config");
        Predicate<Object> Integers= integers -> (Integer)integers >= 0 && (Integer)integers < 64;
        List<Integer> EquipSlotNum = Arrays.asList(39, 38, 37, 36);
        EQUIP_SLOT_LIST=BUILDER.comment("add your custom slot nums,make sure the number exists in your inventory slots list,\nrange:[0,64)").defineList("equip list", EquipSlotNum ,Integers);
        BUILDER.pop();
        SPEC=BUILDER.build();
    }
}
