package com.automaticalechoes.equipsuit.common.network;

import com.automaticalechoes.equipsuit.api.modInterfcae.player.IPlayerInterface;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class SuitChange implements IMessage {
    private int targetNum;

    public SuitChange() {
    }

    public SuitChange(int targetNum) {
        this.targetNum = targetNum;
    }

    @Override
    public void fromBytes(ByteBuf byteBuf) {
        this.targetNum = byteBuf.readInt();
    }

    @Override
    public void toBytes(ByteBuf byteBuf) {
        byteBuf.writeInt(targetNum);

    }

    public static class Handler implements IMessageHandler<SuitChange,IMessage> {

        @Override
        public IMessage onMessage(SuitChange suitChange, MessageContext messageContext) {
            if(messageContext.netHandler instanceof NetHandlerPlayServer){
                 NetHandlerPlayServer netHandlerPlayServer = (NetHandlerPlayServer) messageContext.netHandler;
                ((IPlayerInterface)netHandlerPlayServer.player).setFocus(suitChange.targetNum);
            }

            return null;
        }
    }
}
