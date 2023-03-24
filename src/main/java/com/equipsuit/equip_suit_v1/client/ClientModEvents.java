package com.equipsuit.equip_suit_v1.client;


import com.equipsuit.equip_suit_v1.EquipSuitChange;
import com.equipsuit.equip_suit_v1.client.screen.SuitInventoryScreen;
import com.equipsuit.equip_suit_v1.common.registry.ContainerRegister;
import com.mojang.blaze3d.platform.InputConstants;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.client.settings.KeyConflictContext;
import net.minecraftforge.client.settings.KeyModifier;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;


// You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
@Mod.EventBusSubscriber(modid = EquipSuitChange.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public  class ClientModEvents {
    public static final KeyMapping CALL_SUIT_INVENTORY_KEY = new KeyMapping("open suit menu",
                KeyConflictContext.IN_GAME,
                KeyModifier.CONTROL,
                InputConstants.getKey(InputConstants.KEY_B,0 ),
                "key");
    public static final KeyMapping SUIT_CHANGE = new KeyMapping("change focus suit",
            KeyConflictContext.IN_GAME,
            KeyModifier.CONTROL,
            InputConstants.getKey(InputConstants.KEY_C,0 ),
            "key");
    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event)
    {
        event.enqueueWork(() -> {
            MenuScreens.register(ContainerRegister.SUIT_INVENTORY_MENU.get(),SuitInventoryScreen::new);
        });
    }

    @SubscribeEvent
    public static void registerKeyBinding(RegisterKeyMappingsEvent event){
        event.register(CALL_SUIT_INVENTORY_KEY);
        event.register(SUIT_CHANGE);
    }

}
