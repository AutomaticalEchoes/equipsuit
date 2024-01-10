package com.automaticalechoes.equipsuit.client;


import com.automaticalechoes.equipsuit.EquipSuitChange;
import com.automaticalechoes.equipsuit.api.utils.EquipSuitKeyMapping;
import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;


// You can use EventBusSubscriber to automatically register all static methods in the class annotated with @SubscribeEvent
@Mod.EventBusSubscriber(modid = EquipSuitChange.MODID , value = Side.CLIENT)
@SideOnly(Side.CLIENT)
public  class ClientModEvents {

    public static void onClientSetup(FMLPreInitializationEvent event)
    {
//        event.enqueueWork(() -> {
//            MenuScreens.register(ContainerRegister.SUIT_INVENTORY_MENU.get(),SuitInventoryScreen::new);
//        });
        EquipSuitKeyMapping.KEY_MAPPING.forEach(ClientRegistry::registerKeyBinding);
    }

    @SubscribeEvent
    public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
        if (event.getModID().equals(EquipSuitChange.MODID)) {
            ConfigManager.sync(EquipSuitChange.MODID, Config.Type.INSTANCE);
        }
    }

//    @SubscribeEvent
//    public static void registerKeyBinding(RegisterKeyMappingsEvent event){
//        EquipSuitKeyMapping.KEY_MAPPING.forEach(event::register);
//    }



}
