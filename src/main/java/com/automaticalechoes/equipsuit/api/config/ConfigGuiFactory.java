package com.automaticalechoes.equipsuit.api.config;

import com.automaticalechoes.equipsuit.EquipSuitChange;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.fml.client.IModGuiFactory;
import net.minecraftforge.fml.client.config.GuiConfig;

import java.util.Set;

public class ConfigGuiFactory implements IModGuiFactory {
    @Override
    public void initialize(Minecraft minecraft) {

    }

    @Override
    public boolean hasConfigGui() {
        return true;
    }

    @Override
    public GuiScreen createConfigGui(GuiScreen guiScreen) {
        return new GuiConfig(guiScreen, ConfigElement.from(EquipSuitClientConfig.class).getChildElements(), EquipSuitChange.MODID, false, false, null);
    }

    @Override
    public Set<RuntimeOptionCategoryElement> runtimeGuiCategories() {
        return null;
    }
}
