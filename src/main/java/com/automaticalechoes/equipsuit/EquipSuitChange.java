package com.automaticalechoes.equipsuit;

import com.automaticalechoes.equipsuit.api.utils.EquipSuitTemplate;
import com.automaticalechoes.equipsuit.client.ClientModEvents;
import com.automaticalechoes.equipsuit.common.CommonModEvents;
import com.automaticalechoes.equipsuit.common.Serializer.SuitStackSerializer;
import com.automaticalechoes.equipsuit.common.container.SuitInventoryHandler;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import org.apache.logging.log4j.Logger;
//import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(modid = EquipSuitChange.MODID, name = EquipSuitChange.NAME, version = EquipSuitChange.VERSION)
public class EquipSuitChange
{
    // Define mod id in a common place for everything to reference
    public static final String MODID = "equipsuit";
    public static final String NAME = "EquipSuit";
    public static final String VERSION = "1.8.3";

    // Directly reference a slf4j logger
    public static Logger LOGGER;
    public static final SuitStackSerializer SUIT_STACK_SERIALIZER = new SuitStackSerializer();
    public static final SuitInventoryHandler SUIT_INVENTORY_HANDLER = new SuitInventoryHandler();
    // Create a Deferred Register to hold Blocks which will all be registered under the "examplemod" namespace
//    public EquipSuitChange()
//    {
////        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
//        // Register the commonSetup method for modloading
////        ContainerRegister.REGISTRY.register(modEventBus);
////        SerializerRegistry.REGISTRY.register(modEventBus);
//
//        // Register ourselves for server and other game events we are interested in
//        MinecraftForge.EVENT_BUS.register(this);
//
//        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, EquipSuitClientConfig.SPEC,"equipsuit-client-config.toml");
//    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event)
    {
        ClientModEvents.onClientSetup(event);
        CommonModEvents.commonSetup(event);
        DataSerializers.registerSerializer(SUIT_STACK_SERIALIZER);
        NetworkRegistry.INSTANCE.registerGuiHandler(MODID, SUIT_INVENTORY_HANDLER);
        LOGGER = event.getModLog();
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event)
    {
        // some example code
//        logger.info("DIRT BLOCK >> {}", Blocks.DIRT.getRegistryName());
    }

}
