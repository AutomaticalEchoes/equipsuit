package com.automaticalechoes.equipsuit.common.network;

import com.automaticalechoes.equipsuit.api.modInterfcae.player.IPlayerInterface;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class UpdateSuitName implements IMessage {
    private int suitNum;
    private String suitName;

    public UpdateSuitName() {
    }

    public UpdateSuitName(int SuitNum, String SuitName) {
        this.suitName = SuitName;
        this.suitNum = SuitNum;
    }

    @Override
    public void fromBytes(ByteBuf byteBuf) {
        this.suitNum = byteBuf.readInt();
        this.suitName = ByteBufUtils.readUTF8String(byteBuf);
    }

    @Override
    public void toBytes(ByteBuf byteBuf) {
        byteBuf.writeInt(suitNum);
        ByteBufUtils.writeUTF8String(byteBuf,suitName);
    }

    public static class Handler implements IMessageHandler<UpdateSuitName,IMessage> {

        @Override
        public IMessage onMessage(UpdateSuitName updateSuitName, MessageContext messageContext) {
            if(messageContext.netHandler instanceof NetHandlerPlayServer){
                NetHandlerPlayServer netHandlerPlayServer = (NetHandlerPlayServer) messageContext.netHandler;
                ((IPlayerInterface)netHandlerPlayServer.player).setSuitName(updateSuitName.suitNum, updateSuitName.suitName);
            }

            return null;
        }
    }
}
