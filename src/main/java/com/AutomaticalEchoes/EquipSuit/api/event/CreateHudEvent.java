package com.AutomaticalEchoes.EquipSuit.api.event;

import com.AutomaticalEchoes.EquipSuit.api.modInterfcae.gui.EquipSuitHudInterface;
import com.AutomaticalEchoes.EquipSuit.client.gui.EquipSuitHud;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraftforge.eventbus.api.Event;

import java.util.Optional;

public class CreateHudEvent extends Event {
    Optional<EquipSuitHudInterface> equipSuitHUD;

    public CreateHudEvent(PoseStack stack){
        equipSuitHUD = Optional.of( EquipSuitHud.Create(stack));
    }

    public Optional<EquipSuitHudInterface> getEquipSuitHUD() {
        return equipSuitHUD;
    }

    public void setEquipSuitHUD(Optional<EquipSuitHudInterface> equipSuitHUD) {
        this.equipSuitHUD = equipSuitHUD;
    }
}
