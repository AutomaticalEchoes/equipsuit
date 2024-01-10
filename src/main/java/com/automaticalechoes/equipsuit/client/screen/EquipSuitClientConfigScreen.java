package com.automaticalechoes.equipsuit.client.screen;

import net.minecraft.client.gui.GuiScreen;

public class EquipSuitClientConfigScreen extends GuiScreen {
//    private BinarySwitchButton ChangeModeSwitchButton;
//    private BinarySwitchButton HudModeSwitchButton;
//    private MathEditBox xStart;
//    private MathEditBox yStart;
//    private MathEditBox alpha;
//    private ITextComponent title;
//
//    public EquipSuitClientConfigScreen(ITextComponent p_96550_) {
//        this.title = p_96550_;
//    }
//
//    @Override
//    public void initGui() {
//        int i = this.width / 2 - 155;
//        int j = i + 160;
//        int k = this.height / 6 - 12;
//        ChangeModeSwitchButton = new BinarySwitchButton(j, k, 30, 16) {
//            @Override
//            public void onSwitchCase(boolean SwitchBinary) {
//                int i = EquipSuitClientConfig.CHANGE_MODE.get() == 0 ? 1 : 0;
//                EquipSuitClientConfig.CHANGE_MODE.set(i);
//            }
//        };
//        ChangeModeSwitchButton.binary = !(EquipSuitClientConfig.CHANGE_MODE.get() == 0);
////        this.addRenderableWidget(ChangeModeSwitchButton);
//        this.addButton(ChangeModeSwitchButton);
//        k += 24;
//
//        HudModeSwitchButton = new BinarySwitchButton(j, k, 30, 16) {
//            @Override
//            public void onSwitchCase(boolean SwitchBinary) {
//                int i = EquipSuitClientConfig.HUD_MODE.get() == 0 ? 1 : 0;
//                EquipSuitClientConfig.HUD_MODE.set(i);
//            }
//        };
//        HudModeSwitchButton.binary = EquipSuitClientConfig.HUD_MODE.get() == 0;
////        this.addRenderableWidget(HudModeSwitchButton);
//        this.addButton(HudModeSwitchButton);
//        k += 24;
//
//        xStart = new MathEditBox(this.minecraft.font, j  , k, 40, 16, null, Component.translatable("x"));
//        xStart.setValue(EquipSuitClientConfig.LAYER_START_X.get().toString());
//        xStart.setResponder(s -> EquipSuitClientConfig.LAYER_START_X.set(s.matches("\\d+") ? Integer.parseInt(xStart.getValue()) : 0));
//        xStart.setOnFocusChange(isFocused -> { if(!isFocused) xStart.setValue(EquipSuitClientConfig.LAYER_START_X.get().toString());});
//        xStart.setCanLoseFocus(true);
////        this.addRenderableWidget(xStart);
//        this.(xStart);
//
//        yStart = new MathEditBox(this.minecraft.font, j + 60, k, 40, 16, null, Component.translatable("y"));
//        yStart.setValue(EquipSuitClientConfig.LAYER_START_Y.get().toString());
//        yStart.setResponder(s -> EquipSuitClientConfig.LAYER_START_Y.set(s.matches("\\d+") ? Integer.parseInt(yStart.getValue()) : 28));
//        yStart.setOnFocusChange(isFocused -> { if(!isFocused) yStart.setValue(EquipSuitClientConfig.LAYER_START_Y.get().toString());});
//        yStart.setCanLoseFocus(true);
//        this.addRenderableWidget(yStart);
//        k += 24;
//
//        alpha = new MathEditBox(this.minecraft.font, j , k ,40 ,16 ,null,Component.translatable("alpha"));
//        alpha.setValue(EquipSuitClientConfig.ALPHA.get().toString());
//        alpha.setResponder(s -> EquipSuitClientConfig.ALPHA.set(s.matches("\\d+") ? Integer.parseInt(alpha.getValue()) : 100));
//        alpha.setOnFocusChange(isFocused ->{
//            if(!isFocused) alpha.setValue(EquipSuitClientConfig.ALPHA.get().toString());
//        });
//        alpha.setCanLoseFocus(true);
//        this.addRenderableWidget(alpha);
//        k += 24;
//
//        this.addRenderableWidget(new Button(this.width / 2 - 100, k, 200, 20, CommonComponents.GUI_DONE, (p_97535_) -> {
//            onClose();
//        }));
//    }
//
//    @Override
//    public void tick() {
//        super.tick();
//        boolean flag = EquipSuitClientConfig.HUD_MODE.get() == 1;
//        xStart.setVisible(flag);
//        yStart.setVisible(flag);
//        alpha.setVisible(flag);
////        xStart.setEditable(flag && xStart.isFocused());
////        yStart.setEditable(flag && yStart.isFocused());
////        alpha.setEditable(flag && alpha.isFocused());
//        xStart.setEnabled(flag && xStart.isFocused());
//        yStart.setEnabled(flag && yStart.isFocused());
//        alpha.setEnabled(flag && yStart.isFocused());
////        if(InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), 257)){
////            this.renderables.forEach(widget -> {
////                if(widget instanceof EditBox editBox) editBox.setFocus(false);
////            });
////        }
//    }
//
//    @Override
//    protected void keyTyped(char p_keyTyped_1_, int p_keyTyped_2_) throws IOException {
//        if(p_keyTyped_2_ == 257){
//            this.buttonList.forEach(widget -> {
//                if(widget instanceof EditBox editBox) editBox.setFocus(false);
//            });
//        }
//        super.keyTyped(p_keyTyped_1_, p_keyTyped_2_);
//    }
//
//    public void drawScreen(int p_97531_, int p_97532_, float p_97533_) {
//        int i = this.width / 2 - 155;
//        int j = i + 160;
//        int k = this.height / 6 - 12;
//        this.drawBackground(p_97532_);
////        this.renderBackground(p_97530_);
//
//        drawCenteredString(this.fontRenderer, this.title.getFormattedText(), this.width / 2, 15, 16777215);
//        k += 4;
//        drawString(fontRenderer, Messages.TAG_ENABLE_QUICK_SELECT_MODE, i, k,16777215);
//        k += 24;
//        drawString(fontRenderer, Messages.TAG_ENABLE_SIMPLE_HUD, i, k,16777215);
//        k += 24;
//        if(EquipSuitClientConfig.HUD_MODE.get() != 0){
//            drawString(fontRenderer,Messages.TAG_GUI_COORDINATE, i,k,16777215);
//            drawString(fontRenderer,"X :",j - 15,k,16777215);
//            drawString(fontRenderer,"Y :",j + 45,k,16777215);
//            drawString(fontRenderer,Messages.TAG_GUI_ALPHA, i,k + 24 ,16777215);
//            drawString(fontRenderer,"/ 100",j + 50 ,k + 24 ,16777215);
//        }
//
//
//        super.drawScreen(p_97531_, p_97532_, p_97533_);
//    }
}
