package com.automaticalechoes.equipsuit.api.config;

import com.automaticalechoes.equipsuit.EquipSuitChange;
import com.automaticalechoes.equipsuit.api.utils.Messages;
import net.minecraftforge.common.config.Config;

import java.util.function.Predicate;

@Config(modid = EquipSuitChange.MODID,name = "equipsuit-client-setting")
//@Config.LangKey("text.tag.equipsuit.edit_title")
public class EquipSuitClientConfig {
//    public static final Predicate<Object> StringPredicate = strings -> strings != null && strings.toString().length() < 10;
    @Config.LangKey(Messages.TAG_ENABLE_SIMPLE_HUD)
    @Config.Comment("true :simple Mod , false : All")
    public static boolean HUD_MODE = true;

    @Config.LangKey(Messages.TAG_ENABLE_QUICK_SELECT_MODE)
    @Config.Comment("base suit change mode: \n false : Sequence ,\n ture : Quick select")
    public static boolean CHANGE_MODE = false;


    @Config.SlidingOption
    @Config.LangKey(Messages.TAG_GUI_ALPHA)
    @Config.RangeInt(min = 0 , max = 100)
    public static int HUD_ALPHA = 20;
    @Config.LangKey(Messages.TAG_GUI_COORDINATE_X)
    @Config.RangeInt(min = 0 , max = 400)
    public static int LAYER_START_X = 0;
    @Config.LangKey(Messages.TAG_GUI_COORDINATE_Y)
    @Config.RangeInt(min = 28 , max = 400)
    public static int LAYER_START_Y = 40;
//    public static final ForgeConfigSpec.Builder BUILDER=new ForgeConfigSpec.Builder();
//    public static final ForgeConfigSpec SPEC;
//    public static final ForgeConfigSpec.ConfigValue<Integer> CHANGE_MODE;
//    public static final ForgeConfigSpec.ConfigValue<Integer> ALPHA;
//    public static final ForgeConfigSpec.ConfigValue<Integer> HUD_MODE;
//    public static final ForgeConfigSpec.ConfigValue<Integer> LAYER_START_X;
//    public static final ForgeConfigSpec.ConfigValue<Integer> LAYER_START_Y;
//    static {

//        BUILDER.push("equipsuit client config");
//        ALPHA = BUILDER.defineInRange("hud alpha",20,0,100);
//        CHANGE_MODE = BUILDER.comment("base suit change mode: \n 0 : Sequence \n 1 : Quick select").defineInRange("change mode",0,0,1);
//        HUD_MODE =  BUILDER.comment("base hud show mode: \n 0 : Simple \n 1 : ALL").defineInRange("hud mode",1,0,1);
//        LAYER_START_X = BUILDER.defineInRange("hud layer start x",0,0,400);
//        LAYER_START_Y = BUILDER.defineInRange("hud layer start y",40,28,400);
//        BUILDER.pop();
//        SPEC=BUILDER.build();
//    }
}
