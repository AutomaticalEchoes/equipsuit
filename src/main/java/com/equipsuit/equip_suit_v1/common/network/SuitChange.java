package com.equipsuit.equip_suit_v1.common.network;

import com.equipsuit.equip_suit_v1.api.modInterfcae.player.IPlayerInterface;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SuitChange {
    private int targetNum;

    public SuitChange(int targetNum) {
        this.targetNum = targetNum;
    }

    public static void encode(SuitChange msg, FriendlyByteBuf packetBuffer) {
        packetBuffer.writeInt(msg.targetNum);
    }
    public static SuitChange decode(FriendlyByteBuf packetBuffer) {
        return new SuitChange(packetBuffer.readInt());
    }
    static void onMessage(SuitChange msg, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> msg.handleMessage(msg,context.getSender()));
        context.setPacketHandled(true);
    }

    public void handleMessage(SuitChange msg, ServerPlayer sender) {
        ((IPlayerInterface)sender).setFocus(targetNum);
    }
}
