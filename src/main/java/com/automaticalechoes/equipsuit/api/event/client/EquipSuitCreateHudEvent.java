package com.automaticalechoes.equipsuit.api.event.client;

import com.automaticalechoes.equipsuit.api.modInterfcae.gui.EquipSuitHudInterface;
import com.automaticalechoes.equipsuit.client.gui.EquipSuitHud;
import net.minecraftforge.fml.common.eventhandler.Event;

import java.util.Optional;

public class EquipSuitCreateHudEvent extends Event {
    Optional<EquipSuitHudInterface> equipSuitHUD;
    //    client level
    public EquipSuitCreateHudEvent(){
        equipSuitHUD = Optional.of( new EquipSuitHud());
    }

    public Optional<EquipSuitHudInterface> getEquipSuitHUD() {
        return equipSuitHUD;
    }

    public void setEquipSuitHUD(Optional<EquipSuitHudInterface> equipSuitHUD) {
        this.equipSuitHUD = equipSuitHUD;
    }
}
