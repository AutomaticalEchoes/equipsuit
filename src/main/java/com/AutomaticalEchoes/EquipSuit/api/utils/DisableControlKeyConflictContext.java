package com.AutomaticalEchoes.EquipSuit.api.utils;

public class DisableControlKeyConflictContext implements net.minecraftforge.client.settings.IKeyConflictContext {
    @Override
    public boolean isActive() {
        return false;
    }

    @Override
    public boolean conflicts(net.minecraftforge.client.settings.IKeyConflictContext other) {
        return false;
    }
}
