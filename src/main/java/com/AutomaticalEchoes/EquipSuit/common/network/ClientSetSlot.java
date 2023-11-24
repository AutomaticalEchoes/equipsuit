package com.AutomaticalEchoes.EquipSuit.common.network;

import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Equipable;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class ClientSetSlot {
    public static final int CARRIED_ITEM = -1;
    public static final int PLAYER_INVENTORY = -2;
    private final int slot;
    private final ItemStack itemStack;

    public ClientSetSlot(int p_131983_, ItemStack p_131984_) {
        this.slot = p_131983_;
        this.itemStack = p_131984_.copy();
    }

    public static ClientSetSlot decode(FriendlyByteBuf p_178829_) {
        int slot = p_178829_.readShort();
        ItemStack itemStack = p_178829_.readItem();
        return new ClientSetSlot(slot,itemStack);
    }

    public static void encode(ClientSetSlot msg,FriendlyByteBuf p_131993_) {
        p_131993_.writeShort(msg.slot);
        p_131993_.writeItem(msg.itemStack);
    }

    static void onMessage(ClientSetSlot msg, Supplier<NetworkEvent.Context> contextSupplier) {
        NetworkEvent.Context context = contextSupplier.get();
        context.enqueueWork(() -> msg.handleMessage(msg,context.getSender()));
        context.setPacketHandled(true);
    }

    public void handleMessage(ClientSetSlot msg, Player sender) {
        Equipable item = (Equipable) itemStack.getItem();
        LocalPlayer player = Minecraft.getInstance().player;
        player.playSound( item.getEquipSound());
    }

}
