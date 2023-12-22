package com.AutomaticalEchoes.EquipSuit.common;

import com.AutomaticalEchoes.EquipSuit.EquipSuitChange;
import com.AutomaticalEchoes.EquipSuit.api.modInterfcae.equipsuit.EquipSuitTemplate;
import com.AutomaticalEchoes.EquipSuit.common.network.PacketHandler;
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
        EquipSuitTemplate.Init();
        event.enqueueWork(()->{
            NetWork= PacketHandler.RegisterPacket();
        });
    }





}
