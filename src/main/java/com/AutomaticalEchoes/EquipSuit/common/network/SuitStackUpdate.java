package com.AutomaticalEchoes.EquipSuit.common.network;

import com.AutomaticalEchoes.EquipSuit.api.modInterfcae.player.IPlayerInterface;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SuitStackUpdate {
    private int targetNum;
    private String key ;
    private int slotNum;

    public SuitStackUpdate(int targetNum, String key, int slotNum) {
        this.targetNum = targetNum;
        this.key = key;
        this.slotNum = slotNum;
    }

    public static void encode(SuitStackUpdate msg, FriendlyByteBuf packetBuffer) {
        packetBuffer.writeInt(msg.targetNum);
        packetBuffer.writeComponent(Component.translatable(msg.key));
        packetBuffer.writeInt(msg.slotNum);
    }
    public static SuitStackUpdate decode(FriendlyByteBuf packetBuffer) {
        return new SuitStackUpdate(packetBuffer.readInt(),packetBuffer.readComponent().getString(),packetBuffer.readInt());
    }
    static void onMessage(SuitStackUpdate msg, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> msg.handleMessage(msg,context.getSender()));
        context.setPacketHandled(true);
    }

    public void handleMessage(SuitStackUpdate msg, ServerPlayer sender) {
        ((IPlayerInterface)sender).setSuitSlotNum(targetNum,key,slotNum);
    }
}
