package com.AutomaticalEchoes.EquipSuit.client;


import com.AutomaticalEchoes.EquipSuit.EquipSuitChange;
import com.AutomaticalEchoes.EquipSuit.api.utils.EquipSuitKeyMapping;
import com.AutomaticalEchoes.EquipSuit.client.screen.SuitInventoryScreen;
import com.AutomaticalEchoes.EquipSuit.common.registry.ContainerRegister;
import net.minecraft.client.gui.screens.MenuScreens;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fmlclient.registry.ClientRegistry;


// You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
@Mod.EventBusSubscriber(modid = EquipSuitChange.MODID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public  class ClientModEvents {

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event)
    {
        event.enqueueWork(() -> {
            MenuScreens.register(ContainerRegister.SUIT_INVENTORY_MENU.get(),SuitInventoryScreen::new);
            EquipSuitKeyMapping.KEY_MAPPING.forEach(ClientRegistry::registerKeyBinding);
        });
    }

//    @SubscribeEvent
//    public static void registerKeyBinding(Register event){
//        EquipSuitKeyMapping.KEY_MAPPING.forEach(event::register);
//    }



}
