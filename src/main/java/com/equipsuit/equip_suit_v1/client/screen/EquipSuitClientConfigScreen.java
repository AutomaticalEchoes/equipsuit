package com.equipsuit.equip_suit_v1.client.screen;

import com.equipsuit.equip_suit_v1.api.config.EquipSuitClientConfig;
import com.equipsuit.equip_suit_v1.api.utils.Messages;
import com.equipsuit.equip_suit_v1.client.ClientEvents;
import com.equipsuit.equip_suit_v1.client.gui.BinarySwitchButton;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Options;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.OptionsSubScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

public class EquipSuitClientConfigScreen extends OptionsSubScreen {
    private BinarySwitchButton ChangeModeSwitchButton;
    private BinarySwitchButton HudModeSwitchButton;
    public EquipSuitClientConfigScreen(Screen p_96284_, Options p_96285_, Component p_96286_) {
        super(p_96284_, p_96285_, p_96286_);
    }

    @Override
    protected void init() {
        int i = this.width / 2 - 155;
        int j = i + 160;
        int k = this.height / 6 - 12;
        ChangeModeSwitchButton = new BinarySwitchButton(j, k, 30, 20) {
            @Override
            public void onSwitchCase(boolean SwitchBinary) {
                int i = EquipSuitClientConfig.CHANGE_MODE.get() == 0 ? 1 : 0;
                EquipSuitClientConfig.CHANGE_MODE.set(i);
                ClientEvents.FocusSuitHud.setMode(i);
            }
        };
        ChangeModeSwitchButton.binary = !(EquipSuitClientConfig.CHANGE_MODE.get() == 0);
        this.addRenderableWidget(ChangeModeSwitchButton);
        k += 24;

        HudModeSwitchButton = new BinarySwitchButton(j, k, 30, 20) {
            @Override
            public void onSwitchCase(boolean SwitchBinary) {
                int i = EquipSuitClientConfig.HUD_MODE.get() == 0 ? 1 : 0;
                EquipSuitClientConfig.HUD_MODE.set(i);
            }
        };
        HudModeSwitchButton.binary = EquipSuitClientConfig.HUD_MODE.get() == 0;
        this.addRenderableWidget(HudModeSwitchButton);
        k += 24;
        this.addRenderableWidget(new Button(this.width / 2 - 100, k, 200, 20, CommonComponents.GUI_DONE, (p_97535_) -> {
            this.minecraft.setScreen(this.lastScreen);
        }));
    }

    public void render(PoseStack p_97530_, int p_97531_, int p_97532_, float p_97533_) {
        int i = this.width / 2 - 155;
        int j = i + 160;
        int k = this.height / 6 - 12;
        this.renderBackground(p_97530_);
        drawCenteredString(p_97530_, this.font, this.title, this.width / 2, 15, 16777215);
        drawString(p_97530_,font,Component.translatable(Messages.TAG_ENABLE_QUICK_SELECT_MODE),i,k,16777215);
        k += 24;
        drawString(p_97530_,font,Component.translatable(Messages.TAG_ENABLE_SIMPLE_HUD),i,k,16777215);
        k += 24;
        drawString(p_97530_,font,Component.translatable(Messages.TAG_ENABLE_SIMPLE_HUD),i,k,16777215);
        k += 24;
        drawString(p_97530_,font,Component.translatable(Messages.TAG_ENABLE_SIMPLE_HUD),i,k,16777215);
        super.render(p_97530_, p_97531_, p_97532_, p_97533_);
    }
}
