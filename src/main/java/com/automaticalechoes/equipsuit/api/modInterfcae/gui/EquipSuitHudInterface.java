package com.automaticalechoes.equipsuit.api.modInterfcae.gui;

import com.automaticalechoes.equipsuit.api.config.EquipSuitClientConfig;
import net.minecraft.client.gui.ScaledResolution;

public interface EquipSuitHudInterface {
     void renderALl(int focus);
     int guiWidth();
     int guiHeight();
     void renderSimple(int focus);
     void initResolution(ScaledResolution scaledResolution);

     static boolean ChangeMod(){
          return EquipSuitClientConfig.CHANGE_MODE;
     }

     default int StartX() {
          return Math.min(MaxStartX(),Math.max(MinStartX(),EquipSuitClientConfig.LAYER_START_X));
     }

     default int StartY() {
          return GameWindowHeight() - Math.min(MaxStartY(),Math.max(MinStartY(),EquipSuitClientConfig.LAYER_START_Y));
     }

     default float Alpha() {
          return EquipSuitClientConfig.HUD_ALPHA / 100.0F;
     }

     int GameWindowWidth();
     int GameWindowHeight();

     default int MaxStartX(){
          return GameWindowWidth() - guiWidth();
     }

     default int MaxStartY(){
          return GameWindowHeight();
     }

     default int MinStartX(){
          return 0;
     }

     default int MinStartY(){
          return guiHeight();
     }
}
