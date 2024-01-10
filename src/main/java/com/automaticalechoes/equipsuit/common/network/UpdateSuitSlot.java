package com.automaticalechoes.equipsuit.common.network;

import com.automaticalechoes.equipsuit.api.modInterfcae.player.IPlayerInterface;
import io.netty.buffer.ByteBuf;
import net.minecraft.network.NetHandlerPlayServer;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;

public class UpdateSuitSlot implements IMessage {
    private int targetNum;
    private String key ;
    private int slotNum;

    public UpdateSuitSlot() {
    }

    public UpdateSuitSlot(int targetNum, String key, int slotNum) {
        this.targetNum = targetNum;
        this.key = key;
        this.slotNum = slotNum;
    }

    @Override
    public void fromBytes(ByteBuf byteBuf) {
        this.targetNum = byteBuf.readInt();
        this.key = ByteBufUtils.readUTF8String(byteBuf);
        this.slotNum = byteBuf.readInt();
    }

    @Override
    public void toBytes(ByteBuf byteBuf) {
        byteBuf.writeInt(this.targetNum);
        ByteBufUtils.writeUTF8String(byteBuf, key);
        byteBuf.writeInt(this.slotNum);
    }

    public static class Handler implements IMessageHandler<UpdateSuitSlot,IMessage> {
        @Override
        public IMessage onMessage(UpdateSuitSlot msg, MessageContext messageContext) {
            if(messageContext.netHandler instanceof NetHandlerPlayServer){
                NetHandlerPlayServer netHandlerPlayServer = (NetHandlerPlayServer) messageContext.netHandler;
                ((IPlayerInterface)netHandlerPlayServer.player).setSuitSlotNum(msg.targetNum, msg.key, msg.slotNum);
            }

            return null;
        }
    }
}
