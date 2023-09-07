package com.AutomaticalEchoes.EquipSuit.common.registry;

import com.AutomaticalEchoes.EquipSuit.EquipSuitChange;
import com.AutomaticalEchoes.EquipSuit.common.Serializer.SuitStackSerializer;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SerializerRegistry {
    public static final SuitStackSerializer SUIT_STACK = new SuitStackSerializer();
    static {
        EntityDataSerializers.registerSerializer(SUIT_STACK);
    }


}
