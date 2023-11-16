package com.AutomaticalEchoes.EquipSuit.common.registry;

import com.AutomaticalEchoes.EquipSuit.EquipSuitChange;
import com.AutomaticalEchoes.EquipSuit.common.Serializer.SuitStackSerializer;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class SerializerRegistry {
    public static final DeferredRegister<EntityDataSerializer<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.Keys.ENTITY_DATA_SERIALIZERS, EquipSuitChange.MODID);
    public static final RegistryObject<SuitStackSerializer> SUIT_STACK = REGISTRY.register("suit_stack_serializer", SuitStackSerializer::new);

}
