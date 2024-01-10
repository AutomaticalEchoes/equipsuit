package com.automaticalechoes.equipsuit.common.network;

import com.automaticalechoes.equipsuit.api.modInterfcae.player.IPlayerInterface;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class SuitChangeNext implements IMessage {

    @Override
    public void fromBytes(ByteBuf byteBuf) {

    }

    @Override
    public void toBytes(ByteBuf byteBuf) {

    }

    public static class Handler implements IMessageHandler<SuitChangeNext,IMessage> {
        @Override
        public IMessage onMessage(SuitChangeNext suitChangeNext, MessageContext messageContext) {
            if(messageContext.netHandler instanceof NetHandlerPlayServer){
                NetHandlerPlayServer netHandlerPlayServer = (NetHandlerPlayServer) messageContext.netHandler;
                ((IPlayerInterface)netHandlerPlayServer.player).updateFocus();
            }

            return null;
        }
    }
}
