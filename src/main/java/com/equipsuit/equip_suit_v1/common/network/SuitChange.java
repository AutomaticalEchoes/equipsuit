package com.equipsuit.equip_suit_v1.common.network;

import com.equipsuit.equip_suit_v1.api.modInterfcae.equipsuit.ContainerEquipSuit;
import com.equipsuit.equip_suit_v1.api.modInterfcae.player.IPlayerInterface;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SuitChange {
    public static void encode(SuitChange msg, FriendlyByteBuf packetBuffer) {}
    public static SuitChange decode(FriendlyByteBuf packetBuffer) {
        return new SuitChange();
    }
    static void onMessage(SuitChange msg, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> msg.handleMessage(msg,context.getSender()));
        context.setPacketHandled(true);
    }

    public void handleMessage(SuitChange msg, ServerPlayer sender) {
        ((IPlayerInterface)sender).suiChange();
    }
}
