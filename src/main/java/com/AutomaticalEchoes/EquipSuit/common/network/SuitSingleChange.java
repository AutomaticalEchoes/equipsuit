package com.AutomaticalEchoes.EquipSuit.common.network;

import com.AutomaticalEchoes.EquipSuit.api.utils.EquipSuitHelper;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.fmllegacy.network.NetworkEvent;

import java.util.function.Supplier;

public class SuitSingleChange {
    public static void encode(SuitSingleChange msg, FriendlyByteBuf packetBuffer) {}
    public static SuitSingleChange decode(FriendlyByteBuf packetBuffer) {
        return new SuitSingleChange();
    }
    public static void onMessage(SuitSingleChange msg, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> msg.handleMessage(msg,context.getSender()));
        context.setPacketHandled(true);
    }

    protected void handleMessage(SuitSingleChange msg, ServerPlayer sender) {
        EquipSuitHelper.SingleChange(sender);
    }
}
