package com.automaticalechoes.equipsuit.common.network;

import com.automaticalechoes.equipsuit.EquipSuitChange;
import com.automaticalechoes.equipsuit.common.CommonModEvents;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;

public class PacketHandler {
//    private static final ResourceLocation CHANNEL_NAME=new ResourceLocation(EquipSuitChange.MODID,"network");
//    private static final String PROTOCOL_VERSION = new ResourceLocation(EquipSuitChange.MODID,"1").toString();
    private static int Ids = 0;
    public static SimpleNetworkWrapper RegisterPacket(){
        final SimpleNetworkWrapper INSTANCE = NetworkRegistry.INSTANCE.newSimpleChannel(EquipSuitChange.MODID);
//                .clientAcceptedVersions(version -> true)
//                .serverAcceptedVersions(version -> true)
//                .networkProtocolVersion(()->PROTOCOL_VERSION)
//                .simpleChannel();
        CommonModEvents.NetWork = INSTANCE;
        INSTANCE.registerMessage(OpenOrCloseSuitInventory.Handler.class, OpenOrCloseSuitInventory.class, Ids++, Side.SERVER);
//        INSTANCE.messageBuilder(OpenOrCloseSuitInventory.class,0)
//                .encoder(OpenOrCloseSuitInventory::encode)
//                .decoder(OpenOrCloseSuitInventory::decode)
//                .consumerMainThread(OpenOrCloseSuitInventory::onMessage)
//                .add();
        INSTANCE.registerMessage(SuitChangeNext.Handler.class, SuitChangeNext.class, Ids++, Side.SERVER);
//        INSTANCE.messageBuilder(SuitChangeNext.class,1)
//                .encoder(SuitChangeNext::encode)
//                .decoder(SuitChangeNext::decode)
//                .consumerMainThread(SuitChangeNext::onMessage)
//                .add();
        INSTANCE.registerMessage(SuitChange.Handler.class, SuitChange.class, Ids++ , Side.SERVER);
//        INSTANCE.messageBuilder(SuitChange.class,2)
//                .encoder(SuitChange::encode)
//                .decoder(SuitChange::decode)
//                .consumerMainThread(SuitChange::onMessage)
//                .add();
        INSTANCE.registerMessage(UpdateSuitSlot.Handler.class, UpdateSuitSlot.class, Ids++, Side.SERVER);
//        INSTANCE.messageBuilder(UpdateSuitSlot.class,3)
//                .encoder(UpdateSuitSlot::encode)
//                .decoder(UpdateSuitSlot::decode)
//                .consumerMainThread(UpdateSuitSlot::onMessage)
//                .add();
        INSTANCE.registerMessage(SuitSingleChange.Handler.class, SuitSingleChange.class, Ids++, Side.SERVER);
//        INSTANCE.messageBuilder(SuitSingleChange.class,4)
//                .encoder(SuitSingleChange::encode)
//                .decoder(SuitSingleChange::decode)
//                .consumerMainThread(SuitSingleChange::onMessage)
//                .add();
        INSTANCE.registerMessage(UpdateSuitName.Handler.class, UpdateSuitName.class, Ids++, Side.SERVER);
//        INSTANCE.messageBuilder(UpdateSuitName.class,5)
//                .encoder(UpdateSuitName::encode)
//                .decoder(UpdateSuitName::decode)
//                .consumerMainThread(UpdateSuitName::onMessage)
//                .add();
        return INSTANCE;
    }
}
