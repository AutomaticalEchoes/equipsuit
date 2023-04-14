package com.equipsuit.equip_suit_v1.common.network;

import com.equipsuit.equip_suit_v1.EquipSuitChange;
import com.equipsuit.equip_suit_v1.common.CommonModEvents;
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
                .consumer(OpenOrCloseSuitInventory::onMessage)
                .add();
        INSTANCE.messageBuilder(SuitChangeNext.class,1)
                .encoder(SuitChangeNext::encode)
                .decoder(SuitChangeNext::decode)
                .consumer(SuitChangeNext::onMessage)
                .add();
        INSTANCE.messageBuilder(SuitChange.class,2)
                .encoder(SuitChange::encode)
                .decoder(SuitChange::decode)
                .consumer(SuitChange::onMessage)
                .add();
        INSTANCE.messageBuilder(SuitStackUpdate.class,3)
                .encoder(SuitStackUpdate::encode)
                .decoder(SuitStackUpdate::decode)
                .consumer(SuitStackUpdate::onMessage)
                .add();
        INSTANCE.messageBuilder(SuitSingleChange.class,4)
                .encoder(SuitSingleChange::encode)
                .decoder(SuitSingleChange::decode)
                .consumer(SuitSingleChange::onMessage)
                .add();
        return INSTANCE;
    }
}
