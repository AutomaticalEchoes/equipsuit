package com.AutomaticalEchoes.EquipSuit.common.network;

import com.AutomaticalEchoes.EquipSuit.api.modInterfcae.player.IPlayerInterface;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.fmllegacy.network.NetworkEvent;

import java.util.function.Supplier;

public class SuitChange {
    private final int targetNum;

    public SuitChange(int targetNum) {
        this.targetNum = targetNum;
    }

    public static void encode(SuitChange msg, FriendlyByteBuf packetBuffer) {
        packetBuffer.writeInt(msg.targetNum);
    }
    public static SuitChange decode(FriendlyByteBuf packetBuffer) {
        return new SuitChange(packetBuffer.readInt());
    }
    protected static void onMessage(SuitChange msg, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> msg.handleMessage(msg,context.getSender()));
        context.setPacketHandled(true);
    }

    public void handleMessage(SuitChange msg, ServerPlayer sender) {
        ((IPlayerInterface)sender).setFocus(targetNum);
    }
}
