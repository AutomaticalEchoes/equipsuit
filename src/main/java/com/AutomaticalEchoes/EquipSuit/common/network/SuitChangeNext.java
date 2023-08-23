package com.AutomaticalEchoes.EquipSuit.common.network;

import com.AutomaticalEchoes.EquipSuit.api.modInterfcae.player.IPlayerInterface;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SuitChangeNext {
    public static void encode(SuitChangeNext msg, FriendlyByteBuf packetBuffer) {}
    public static SuitChangeNext decode(FriendlyByteBuf packetBuffer) {
        return new SuitChangeNext();
    }
    static void onMessage(SuitChangeNext msg, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> msg.handleMessage(msg,context.getSender()));
        context.setPacketHandled(true);
    }

    public void handleMessage(SuitChangeNext msg, ServerPlayer sender) {
        ((IPlayerInterface)sender).updateFocus();
    }
}
