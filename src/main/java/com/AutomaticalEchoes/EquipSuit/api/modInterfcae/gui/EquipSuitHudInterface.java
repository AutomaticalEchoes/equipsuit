package com.AutomaticalEchoes.EquipSuit.api.modInterfcae.gui;

import com.AutomaticalEchoes.EquipSuit.api.config.EquipSuitClientConfig;
import net.minecraft.client.Minecraft;

public interface EquipSuitHudInterface {
     void renderALl(int focus);
     int guiWidth();
     int guiHeight();
     void renderSimple(int focus);

     default int ChangeMod(){
          return EquipSuitClientConfig.CHANGE_MODE.get();
     }

     default int StartX() {
          return Math.min(MaxStartX(),Math.max(MinStartX(),EquipSuitClientConfig.LAYER_START_X.get()));
     }

     default int StartY() {
          return GameWindowHeight() - Math.min(MaxStartY(),Math.max(MinStartY(),EquipSuitClientConfig.LAYER_START_Y.get()));
     }

     default float Alpha() {
          return EquipSuitClientConfig.ALPHA.get() / 1000.0F;
     }

     default int GameWindowWidth(){
          return Minecraft.getInstance().getWindow().getGuiScaledWidth();
     }

     default int GameWindowHeight(){
          return Minecraft.getInstance().getWindow().getGuiScaledHeight();
     }

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
