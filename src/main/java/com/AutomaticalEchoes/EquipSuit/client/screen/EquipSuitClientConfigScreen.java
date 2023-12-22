package com.AutomaticalEchoes.EquipSuit.client.screen;

import com.AutomaticalEchoes.EquipSuit.api.config.EquipSuitClientConfig;
import com.AutomaticalEchoes.EquipSuit.api.utils.Messages;
import com.AutomaticalEchoes.EquipSuit.client.gui.BinarySwitchButton;
import com.AutomaticalEchoes.EquipSuit.client.gui.MathEditBox;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;

public class EquipSuitClientConfigScreen extends Screen {
    private BinarySwitchButton ChangeModeSwitchButton;
    private BinarySwitchButton HudModeSwitchButton;
    private MathEditBox xStart;
    private MathEditBox yStart;
    private MathEditBox alpha;

    public EquipSuitClientConfigScreen(Component p_96550_) {
        super(p_96550_);
    }

    @Override
    protected void init() {
        int i = this.width / 2 - 155;
        int j = i + 160;
        int k = this.height / 6 - 12;
        ChangeModeSwitchButton = new BinarySwitchButton(j, k, 30, 16) {
            @Override
            public void onSwitchCase(boolean SwitchBinary) {
                int i = EquipSuitClientConfig.CHANGE_MODE.get() == 0 ? 1 : 0;
                EquipSuitClientConfig.CHANGE_MODE.set(i);
            }
        };
        ChangeModeSwitchButton.binary = !(EquipSuitClientConfig.CHANGE_MODE.get() == 0);
        this.addRenderableWidget(ChangeModeSwitchButton);
        k += 24;

        HudModeSwitchButton = new BinarySwitchButton(j, k, 30, 16) {
            @Override
            public void onSwitchCase(boolean SwitchBinary) {
                int i = EquipSuitClientConfig.HUD_MODE.get() == 0 ? 1 : 0;
                EquipSuitClientConfig.HUD_MODE.set(i);
            }
        };
        HudModeSwitchButton.binary = EquipSuitClientConfig.HUD_MODE.get() == 0;
        this.addRenderableWidget(HudModeSwitchButton);
        k += 24;

        xStart = new MathEditBox(this.minecraft.font, j  , k, 40, 16, null, Component.nullToEmpty("x"));
        xStart.setValue(EquipSuitClientConfig.LAYER_START_X.get().toString());
        xStart.setResponder(s -> EquipSuitClientConfig.LAYER_START_X.set(s.matches("\\d+") ? Integer.parseInt(xStart.getValue()) : 0));
        xStart.setOnFocusChange(isFocused -> { if(!isFocused) xStart.setValue(EquipSuitClientConfig.LAYER_START_X.get().toString());});
        xStart.setCanLoseFocus(true);
        this.addRenderableWidget(xStart);

        yStart = new MathEditBox(this.minecraft.font, j + 60, k, 40, 16, null, Component.nullToEmpty("y"));
        yStart.setValue(EquipSuitClientConfig.LAYER_START_Y.get().toString());
        yStart.setResponder(s -> EquipSuitClientConfig.LAYER_START_Y.set(s.matches("\\d+") ? Integer.parseInt(yStart.getValue()) : 28));
        yStart.setOnFocusChange(isFocused -> { if(!isFocused) yStart.setValue(EquipSuitClientConfig.LAYER_START_Y.get().toString());});
        yStart.setCanLoseFocus(true);
        this.addRenderableWidget(yStart);
        k += 24;

        alpha = new MathEditBox(this.minecraft.font, j , k ,40 ,16 ,null,Component.nullToEmpty("alpha"));
        alpha.setValue(EquipSuitClientConfig.ALPHA.get().toString());
        alpha.setResponder(s -> EquipSuitClientConfig.ALPHA.set(s.matches("\\d+") ? Integer.parseInt(alpha.getValue()) : 100));
        alpha.setOnFocusChange(isFocused ->{
            if(!isFocused) alpha.setValue(EquipSuitClientConfig.ALPHA.get().toString());
        });
        alpha.setCanLoseFocus(true);
        this.addRenderableWidget(alpha);
        k += 24;

        this.addRenderableWidget(new Button(this.width / 2 - 100, k, 200, 20, CommonComponents.GUI_DONE, (p_97535_) -> {
            onClose();
        }));
    }

    @Override
    public void tick() {
        super.tick();
        boolean flag = EquipSuitClientConfig.HUD_MODE.get() == 1;
        xStart.setVisible(flag);
        yStart.setVisible(flag);
        alpha.setVisible(flag);
        xStart.setEditable(flag && xStart.isFocused());
        yStart.setEditable(flag && yStart.isFocused());
        alpha.setEditable(flag && alpha.isFocused());
        if(InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), 257)){
            this.renderables.forEach(widget -> {
                if(widget instanceof EditBox editBox) editBox.setFocus(false);
            });
        }
    }

    public void render(PoseStack p_97530_, int p_97531_, int p_97532_, float p_97533_) {
        int i = this.width / 2 - 155;
        int j = i + 160;
        int k = this.height / 6 - 12;
        this.renderBackground(p_97530_);

        drawCenteredString(p_97530_, this.font, this.title, this.width / 2, 15, 16777215);
        k += 4;
        drawString(p_97530_,font,Component.nullToEmpty(Messages.TAG_ENABLE_QUICK_SELECT_MODE),i,k,16777215);
        k += 24;
        drawString(p_97530_,font,Component.nullToEmpty(Messages.TAG_ENABLE_SIMPLE_HUD),i,k,16777215);
        k += 24;
        if(EquipSuitClientConfig.HUD_MODE.get() != 0){
            drawString(p_97530_,font,Component.nullToEmpty(Messages.TAG_GUI_COORDINATE), i,k,16777215);
            drawString(p_97530_,font,Component.nullToEmpty("X :"),j - 15,k,16777215);
            drawString(p_97530_,font,Component.nullToEmpty("Y :"),j + 45,k,16777215);
            drawString(p_97530_,font,Component.nullToEmpty(Messages.TAG_GUI_ALPHA), i,k + 24 ,16777215);
            drawString(p_97530_,font,Component.nullToEmpty("/ 100"),j + 50 ,k + 24 ,16777215);
        }


        super.render(p_97530_, p_97531_, p_97532_, p_97533_);
    }
}
