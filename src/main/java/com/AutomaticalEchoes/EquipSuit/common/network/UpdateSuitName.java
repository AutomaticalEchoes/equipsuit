package com.AutomaticalEchoes.EquipSuit.common.network;

import com.AutomaticalEchoes.EquipSuit.api.modInterfcae.player.IPlayerInterface;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.fmllegacy.network.NetworkEvent;

import java.util.function.Supplier;

public class UpdateSuitName {
    private int suitNum;
    private String suitName;

    public UpdateSuitName(int SuitNum,String SuitName) {
        this.suitName = SuitName;
        this.suitNum = SuitNum;
    }

    public static void encode(UpdateSuitName msg, FriendlyByteBuf packetBuffer) {
        packetBuffer.writeInt(msg.suitNum);
        packetBuffer.writeComponent(Component.nullToEmpty(msg.suitName));
    }
    public static UpdateSuitName decode(FriendlyByteBuf packetBuffer) {
        return new UpdateSuitName(packetBuffer.readInt(),packetBuffer.readComponent().getString());
    }
    protected static void onMessage(UpdateSuitName msg, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> msg.handleMessage(msg,context.getSender()));
        context.setPacketHandled(true);
    }

    public void handleMessage(UpdateSuitName msg, ServerPlayer sender) {
        ((IPlayerInterface)sender).setSuitName(msg.suitNum,msg.suitName);
    }
}
