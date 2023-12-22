package com.AutomaticalEchoes.EquipSuit.common.registry;

import com.AutomaticalEchoes.EquipSuit.common.Serializer.SuitStackSerializer;
import net.minecraft.network.syncher.EntityDataSerializers;

public class SerializerRegistry {
    public static final SuitStackSerializer SUIT_STACK = new SuitStackSerializer();
    static {
        EntityDataSerializers.registerSerializer(SUIT_STACK);
    }


}
