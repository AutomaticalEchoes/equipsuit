package com.equipsuit.equip_suit_v1.common.network;

import com.equipsuit.equip_suit_v1.common.container.SuitInventoryMenu;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraftforge.network.NetworkEvent;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class OpenOrCloseSuitInventory {
    public OpenOrCloseSuitInventory() {
    }

    public static void encode(OpenOrCloseSuitInventory msg, FriendlyByteBuf packetBuffer) {
    }

    public static OpenOrCloseSuitInventory decode(FriendlyByteBuf packetBuffer) {
        return new OpenOrCloseSuitInventory();
    }

    static void onMessage(OpenOrCloseSuitInventory msg, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> handleMessage(msg,context.getSender()));
        context.setPacketHandled(true);
    }

    private static void handleMessage(OpenOrCloseSuitInventory msg, ServerPlayer sender) {
            sender.openMenu(new MenuProvider() {
                @Override
                public Component getDisplayName() {
                    return Component.nullToEmpty("");
                }

                @Nullable
                @Override
                public AbstractContainerMenu createMenu(int p_39954_, Inventory p_39955_, Player p_39956_) {
                    return new SuitInventoryMenu(p_39955_,p_39954_);
                }
            });
    }



}
