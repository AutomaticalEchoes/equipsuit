package com.equipsuit.equip_suit_v1.common.network;

import com.equipsuit.equip_suit_v1.EquipSuitChange;
import com.equipsuit.equip_suit_v1.api.mixin.PlayerMixin;
import com.equipsuit.equip_suit_v1.api.modInterfcae.equipsuit.ContainerEquipSuit;
import com.equipsuit.equip_suit_v1.api.modInterfcae.player.IPlayerInterface;
import com.equipsuit.equip_suit_v1.api.utils.EquipSuitHelper;
import net.minecraft.core.NonNullList;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
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
        EquipSuitChange.LOGGER.info(((IPlayerInterface)sender).getSuitList().get(0).toString());
        EquipSuitHelper.suiChange(sender);
    }
}
