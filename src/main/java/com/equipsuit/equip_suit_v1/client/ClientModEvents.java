package com.equipsuit.equip_suit_v1.client;


import com.equipsuit.equip_suit_v1.EquipSuitChange;
import com.equipsuit.equip_suit_v1.client.screen.SuitInventoryScreen;
import com.equipsuit.equip_suit_v1.common.registry.ContainerRegister;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.ClientRegistry;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.client.settings.KeyModifier;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;


// You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
@Mod.EventBusSubscriber(modid = EquipSuitChange.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public  class ClientModEvents {
    public static final KeyMapping CALL_SUIT_INVENTORY_KEY = new KeyMapping("key.category.equipsuit.suitinvetory",
                KeyConflictContext.IN_GAME,
                KeyModifier.NONE,
                InputConstants.getKey(InputConstants.KEY_B,0 ),
                "key.equipsuit");
    public static final KeyMapping SUIT_CHANGE = new KeyMapping("key.category.equipsuit.suitchange",
            KeyConflictContext.IN_GAME,
            KeyModifier.NONE,
            InputConstants.getKey(InputConstants.KEY_R,0 ),
            "key.equipsuit");
    public static final KeyMapping MODE_CHANGE = new KeyMapping("key.category.equipsuit.modechange",
            KeyConflictContext.IN_GAME,
            KeyModifier.CONTROL,
            InputConstants.getKey(InputConstants.KEY_R,0 ),
            "key.equipsuit");
    public static final KeyMapping SELECT_SUIT_CHANGE_I = new KeyMapping("key.category.equipsuit.suitchange.select_1",
            KeyConflictContext.IN_GAME,
            KeyModifier.CONTROL,
            InputConstants.getKey(InputConstants.KEY_1,0 ),
            "key.equipsuit");
    public static final KeyMapping SELECT_SUIT_CHANGE_II = new KeyMapping("key.category.equipsuit.suitchange.select_2",
            KeyConflictContext.IN_GAME,
            KeyModifier.CONTROL,
            InputConstants.getKey(InputConstants.KEY_2,0 ),
            "key.equipsuit");
    public static final KeyMapping SELECT_SUIT_CHANGE_III = new KeyMapping("key.category.equipsuit.suitchange.select_3",
            KeyConflictContext.IN_GAME,
            KeyModifier.CONTROL,
            InputConstants.getKey(InputConstants.KEY_3,0 ),
            "key.equipsuit");
    public static final KeyMapping SELECT_SUIT_CHANGE_IV = new KeyMapping("key.category.equipsuit.suitchange.select_4",
            KeyConflictContext.IN_GAME,
            KeyModifier.CONTROL,
            InputConstants.getKey(InputConstants.KEY_4,0 ),
            "key.equipsuit");

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event)
    {
        event.enqueueWork(() -> {
            MenuScreens.register(ContainerRegister.SUIT_INVENTORY_MENU.get(),SuitInventoryScreen::new);
            ClientRegistry.registerKeyBinding(CALL_SUIT_INVENTORY_KEY);
            ClientRegistry.registerKeyBinding(SUIT_CHANGE);
            ClientRegistry.registerKeyBinding(MODE_CHANGE);
            ClientRegistry.registerKeyBinding(SELECT_SUIT_CHANGE_I);
            ClientRegistry.registerKeyBinding(SELECT_SUIT_CHANGE_II);
            ClientRegistry.registerKeyBinding(SELECT_SUIT_CHANGE_III);
            ClientRegistry.registerKeyBinding(SELECT_SUIT_CHANGE_IV);
        });
    }

//    @SubscribeEvent
//    public static void registerKeyBinding(RegisterKeyMappingsEvent event){
//        event.register(CALL_SUIT_INVENTORY_KEY);
//        event.register(SUIT_CHANGE);
//        event.register(SELECT_SUIT_CHANGE_I);
//        event.register(SELECT_SUIT_CHANGE_II);
//        event.register(SELECT_SUIT_CHANGE_III);
//        event.register(SELECT_SUIT_CHANGE_IV);
//    }

}
