package com.AutomaticalEchoes.EquipSuit.common.network;

import com.AutomaticalEchoes.EquipSuit.EquipSuitChange;
import com.AutomaticalEchoes.EquipSuit.common.CommonModEvents;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;

public class PacketHandler {
    private static final ResourceLocation CHANNEL_NAME=new ResourceLocation(EquipSuitChange.MODID,"network");
    private static final String PROTOCOL_VERSION = new ResourceLocation(EquipSuitChange.MODID,"1").toString();
    public static SimpleChannel RegisterPacket(){
        final SimpleChannel INSTANCE = NetworkRegistry.ChannelBuilder.named(CHANNEL_NAME)
                .clientAcceptedVersions(version -> true)
                .serverAcceptedVersions(version -> true)
                .networkProtocolVersion(()->PROTOCOL_VERSION)
                .simpleChannel();
        CommonModEvents.NetWork=INSTANCE;
        INSTANCE.messageBuilder(OpenOrCloseSuitInventory.class,0)
                .encoder(OpenOrCloseSuitInventory::encode)
                .decoder(OpenOrCloseSuitInventory::decode)
                .consumerMainThread(OpenOrCloseSuitInventory::onMessage)
                .add();
        INSTANCE.messageBuilder(SuitChangeNext.class,1)
                .encoder(SuitChangeNext::encode)
                .decoder(SuitChangeNext::decode)
                .consumerMainThread(SuitChangeNext::onMessage)
                .add();
        INSTANCE.messageBuilder(SuitChange.class,2)
                .encoder(SuitChange::encode)
                .decoder(SuitChange::decode)
                .consumerMainThread(SuitChange::onMessage)
                .add();
        INSTANCE.messageBuilder(UpdateSuitSlot.class,3)
                .encoder(UpdateSuitSlot::encode)
                .decoder(UpdateSuitSlot::decode)
                .consumerMainThread(UpdateSuitSlot::onMessage)
                .add();
        INSTANCE.messageBuilder(SuitSingleChange.class,4)
                .encoder(SuitSingleChange::encode)
                .decoder(SuitSingleChange::decode)
                .consumerMainThread(SuitSingleChange::onMessage)
                .add();
        INSTANCE.messageBuilder(UpdateSuitName.class,5)
                .encoder(UpdateSuitName::encode)
                .decoder(UpdateSuitName::decode)
                .consumerMainThread(UpdateSuitName::onMessage)
                .add();
        INSTANCE.messageBuilder(ClientSetSlot.class,6)
                .encoder(ClientSetSlot::encode)
                .decoder(ClientSetSlot::decode)
                .consumerMainThread(ClientSetSlot::onMessage)
                .add();
        return INSTANCE;
    }
}
