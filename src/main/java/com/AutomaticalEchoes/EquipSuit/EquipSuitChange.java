package com.AutomaticalEchoes.EquipSuit;

import com.AutomaticalEchoes.EquipSuit.api.config.EquipSuitClientConfig;
import com.AutomaticalEchoes.EquipSuit.common.registry.ContainerRegister;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(EquipSuitChange.MODID)
public class EquipSuitChange
{
    // Define mod id in a common place for everything to reference
    public static final String MODID = "equipsuit";
    // Create a Deferred Register to hold Blocks which will all be registered under the "examplemod" namespace
    public EquipSuitChange()
    {
        IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        // Register the commonSetup method for modloading
        ContainerRegister.REGISTRY.register(modEventBus);
        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);

        ModLoadingContext.get().registerConfig(ModConfig.Type.CLIENT, EquipSuitClientConfig.SPEC,"equipsuit-client-config.toml");
    }

    // You can use SubscribeEvent and let the Event Bus discover methods to call

}
