package com.AutomaticalEchoes.EquipSuit.common.network;

import com.AutomaticalEchoes.EquipSuit.api.modInterfcae.player.IPlayerInterface;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class SuitStackUpdate {
        private int targetNum;
        private int[] ints;

        public SuitStackUpdate(int targetNum,int... ints) {
            this.targetNum = targetNum;
            this.ints = ints;
        }

        public static void encode(SuitStackUpdate msg, FriendlyByteBuf packetBuffer) {
            packetBuffer.writeInt(msg.targetNum);
            packetBuffer.writeVarIntArray(msg.ints);
        }
        public static SuitStackUpdate decode(FriendlyByteBuf packetBuffer) {
            return new SuitStackUpdate(packetBuffer.readInt(),packetBuffer.readVarIntArray());
        }
        static void onMessage(SuitStackUpdate msg, Supplier<NetworkEvent.Context> contextSupplier) {
            NetworkEvent.Context context = contextSupplier.get();
            context.enqueueWork(() -> msg.handleMessage(msg,context.getSender()));
            context.setPacketHandled(true);
        }

        public void handleMessage(SuitStackUpdate msg, ServerPlayer sender) {
            ((IPlayerInterface)sender).setSuitArray(targetNum,ints);
        }
}
