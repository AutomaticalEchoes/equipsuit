package com.AutomaticalEchoes.EquipSuit.api.modInterfcae.containerType;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;

public interface ContainerType<T extends Container> {
    T getContainer(ServerPlayer player);
}
