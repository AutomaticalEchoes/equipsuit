package com.AutomaticalEchoes.EquipSuit.common.network;

import com.AutomaticalEchoes.EquipSuit.api.modInterfcae.player.IPlayerInterface;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.fmllegacy.network.NetworkEvent;

import java.util.function.Supplier;

public class UpdateSuitSlot {
    private int targetNum;
    private String key ;
    private int slotNum;

    public UpdateSuitSlot(int targetNum, String key, int slotNum) {
        this.targetNum = targetNum;
        this.key = key;
        this.slotNum = slotNum;
    }

    public static void encode(UpdateSuitSlot msg, FriendlyByteBuf packetBuffer) {
        packetBuffer.writeInt(msg.targetNum);
        packetBuffer.writeComponent(Component.nullToEmpty(msg.key));
        packetBuffer.writeInt(msg.slotNum);
    }
    public static UpdateSuitSlot decode(FriendlyByteBuf packetBuffer) {
        return new UpdateSuitSlot(packetBuffer.readInt(),packetBuffer.readComponent().getString(),packetBuffer.readInt());
    }
    protected static void onMessage(UpdateSuitSlot msg, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> msg.handleMessage(msg,context.getSender()));
        context.setPacketHandled(true);
    }

    public void handleMessage(UpdateSuitSlot msg, ServerPlayer sender) {
        ((IPlayerInterface)sender).setSuitSlotNum(targetNum,key,slotNum);
    }
}
