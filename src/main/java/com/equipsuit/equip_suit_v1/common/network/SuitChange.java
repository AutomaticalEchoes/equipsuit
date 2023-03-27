package com.equipsuit.equip_suit_v1.common.network;

import com.equipsuit.equip_suit_v1.api.ModInterfcae.equipsuit.ContainerEquipSuit;
import com.equipsuit.equip_suit_v1.api.ModInterfcae.player.IPlayerInterface;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
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
        for(int i=0;i<4;i++){
            ContainerEquipSuit containerEquipSuit = ((IPlayerInterface) sender).getSuitList().get(i);
            containerEquipSuit.setContainerSlotNums(NonNullList.of(i*4,i*4+1,i*4+2,i*4+3));
            ((IPlayerInterface)sender).getSuitList().set(i,containerEquipSuit);
        }
        ((IPlayerInterface)sender).suiChange();
    }
}
