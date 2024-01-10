package com.automaticalechoes.equipsuit.common.network;

import com.automaticalechoes.equipsuit.EquipSuitChange;
import com.automaticalechoes.equipsuit.api.event.server.EquipSuitCreateMenuEvent;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class OpenOrCloseSuitInventory implements IMessage {

    @Override
    public void fromBytes(ByteBuf byteBuf) {

    }

    @Override
    public void toBytes(ByteBuf byteBuf) {

    }

    public static class Handler implements IMessageHandler<OpenOrCloseSuitInventory,IMessage>{
        @Override
        public IMessage onMessage(OpenOrCloseSuitInventory openOrCloseSuitInventory, MessageContext messageContext) {
            if(messageContext.netHandler instanceof NetHandlerPlayServer){
                NetHandlerPlayServer netHandlerPlayServer = (NetHandlerPlayServer) messageContext.netHandler;
                EquipSuitCreateMenuEvent equipSuitCreateMenuEvent = new EquipSuitCreateMenuEvent(EquipSuitChange.MODID,0);
                MinecraftForge.EVENT_BUS.post(equipSuitCreateMenuEvent);
                netHandlerPlayServer.player.openGui(equipSuitCreateMenuEvent.getModId(), equipSuitCreateMenuEvent.getCode(), netHandlerPlayServer.player.getServerWorld(), 0, 0, 0);
            }
            return null;
        }
    }


}
