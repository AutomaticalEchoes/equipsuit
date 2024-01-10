package com.automaticalechoes.equipsuit.common.network;

import com.automaticalechoes.equipsuit.api.utils.EquipSuitHelper;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class SuitSingleChange implements IMessage {

    @Override
    public void fromBytes(ByteBuf byteBuf) {

    }

    @Override
    public void toBytes(ByteBuf byteBuf) {

    }

    public static class Handler implements IMessageHandler<SuitSingleChange,IMessage> {

        @Override
        public IMessage onMessage(SuitSingleChange suitSingleChange, MessageContext messageContext) {
            if(messageContext.netHandler instanceof NetHandlerPlayServer){
                NetHandlerPlayServer netHandlerPlayServer = (NetHandlerPlayServer) messageContext.netHandler;
                EquipSuitHelper.SingleChange(netHandlerPlayServer.player);
            }

            return null;
        }
    }
}
