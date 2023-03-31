package com.equipsuit.equip_suit_v1.common.network;

import com.equipsuit.equip_suit_v1.api.modInterfcae.player.IPlayerInterface;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SuitUpdate {
    public static void encode(SuitUpdate msg, FriendlyByteBuf packetBuffer) {}
    public static SuitUpdate decode(FriendlyByteBuf packetBuffer) {
        return new SuitUpdate();
    }
    static void onMessage(SuitUpdate msg, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> msg.handleMessage(msg,context.getSender()));
        context.setPacketHandled(true);
    }

    public void handleMessage(SuitUpdate msg, ServerPlayer sender) {
        ((IPlayerInterface)sender).updateFocus();
    }
}
