package com.equipsuit.equip_suit_v1.common;

import com.equipsuit.equip_suit_v1.EquipSuitChange;
import com.equipsuit.equip_suit_v1.common.network.PacketHandler;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.network.simple.SimpleChannel;

@Mod.EventBusSubscriber(modid = EquipSuitChange.MODID,bus=Mod.EventBusSubscriber.Bus.MOD)
public class CommonModEvents {
    public static SimpleChannel NetWork;
    @SubscribeEvent
    public static  void commonSetup(final FMLCommonSetupEvent event)
    {
        event.enqueueWork(()->{
            NetWork= PacketHandler.RegisterPacket();
        });
    }





}
