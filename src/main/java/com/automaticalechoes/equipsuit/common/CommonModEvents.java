package com.automaticalechoes.equipsuit.common;

import com.automaticalechoes.equipsuit.EquipSuitChange;
import com.automaticalechoes.equipsuit.api.utils.EquipSuitTemplate;
import com.automaticalechoes.equipsuit.common.network.PacketHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;

@Mod.EventBusSubscriber(modid = EquipSuitChange.MODID)
public class CommonModEvents {
    public static SimpleNetworkWrapper NetWork;
    public static  void commonSetup(FMLPreInitializationEvent event)
    {
        NetWork = PacketHandler.RegisterPacket();
        EquipSuitTemplate.Init();
    }





}
