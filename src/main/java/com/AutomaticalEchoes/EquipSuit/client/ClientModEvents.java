package com.AutomaticalEchoes.EquipSuit.client;


import com.AutomaticalEchoes.EquipSuit.EquipSuitChange;
import com.AutomaticalEchoes.EquipSuit.api.utils.EquipSuitKeyMapping;
import com.AutomaticalEchoes.EquipSuit.client.screen.SuitInventoryScreen;
import com.AutomaticalEchoes.EquipSuit.common.registry.ContainerRegister;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;


// You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
@Mod.EventBusSubscriber(modid = EquipSuitChange.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public  class ClientModEvents {

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event)
    {
        event.enqueueWork(() -> {
            MenuScreens.register(ContainerRegister.SUIT_INVENTORY_MENU.get(),SuitInventoryScreen::new);
        });
    }

    @SubscribeEvent
    public static void registerKeyBinding(RegisterKeyMappingsEvent event){
        EquipSuitKeyMapping.KEY_MAPPING.forEach(event::register);
    }



}
