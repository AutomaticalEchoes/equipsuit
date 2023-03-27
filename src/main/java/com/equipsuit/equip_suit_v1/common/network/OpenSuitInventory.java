package com.equipsuit.equip_suit_v1.common.network;

import com.equipsuit.equip_suit_v1.api.ModInterfcae.player.IPlayerInterface;
import com.equipsuit.equip_suit_v1.common.container.SuitInventoryMenu;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.MenuProvider;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraftforge.network.NetworkEvent;
import net.minecraftforge.network.NetworkHooks;
import org.jetbrains.annotations.Nullable;

import java.util.function.Supplier;

public class OpenSuitInventory {
    public OpenSuitInventory() {
    }

    public static void encode(OpenSuitInventory msg, FriendlyByteBuf packetBuffer) {
    }

    public static OpenSuitInventory decode(FriendlyByteBuf packetBuffer) {
        return new OpenSuitInventory();
    }

    static void onMessage(OpenSuitInventory msg, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> handleMessage(msg,context.getSender()));
        context.setPacketHandled(true);
    }

    private static void handleMessage(OpenSuitInventory msg, ServerPlayer sender) {
        NetworkHooks.openScreen(sender, new MenuProvider() {
            @Override
            public Component getDisplayName() {
                return Component.translatable("");
            }

            @Nullable
            @Override
            public AbstractContainerMenu createMenu(int p_39954_, Inventory p_39955_, Player p_39956_) {
                return new SuitInventoryMenu(((Player)(Object)this).getInventory(),p_39954_);
            }
        });
    }



}
