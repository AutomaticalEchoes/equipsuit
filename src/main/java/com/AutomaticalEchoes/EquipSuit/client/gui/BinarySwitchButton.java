package com.AutomaticalEchoes.EquipSuit.client.gui;

import com.AutomaticalEchoes.EquipSuit.EquipSuitChange;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.client.gui.widget.ExtendedButton;

public abstract class BinarySwitchButton extends ExtendedButton {
    public static final ResourceLocation I_WIDGETS_LOCATION = new ResourceLocation(EquipSuitChange.MODID,"textures/gui/widgets.png");
    private static final OnPress switchOnPress = p_93751_ -> {
        if(p_93751_ instanceof BinarySwitchButton binarySwitchButton){
            binarySwitchButton.binary = !binarySwitchButton.binary;
            binarySwitchButton.time = 100;
            binarySwitchButton.onSwitchCase(binarySwitchButton.binary);
        }
    };
    public int time = 0 ;
    private final Component[] components = new Component[2];
    public boolean binary = false;

    public BinarySwitchButton(int p_93728_, int p_93729_, int p_93730_, int p_93731_) {
        super(p_93728_, p_93729_, p_93730_, p_93731_, Component.empty(), switchOnPress,DEFAULT_NARRATION);
        this.components[0] = Component.translatable("OFF");
        this.components[1] = Component.translatable("ON");
    }

    public BinarySwitchButton(int p_93728_, int p_93729_, int p_93730_, int p_93731_,CreateNarration onTooltip) {
        super(p_93728_, p_93729_, p_93730_, p_93731_, Component.empty(), switchOnPress , onTooltip);
        this.components[0] = Component.translatable("OFF");
        this.components[1] = Component.translatable("ON");
    }

    public BinarySwitchButton DefaultRight(){
        this.binary = true;
        return this;
    }

    public BinarySwitchButton ComponentsOff(Component component){
        this.components[0] = component;
        return this;
    }
    public BinarySwitchButton ComponentsOn(Component component){
        this.components[1] = component;
        return this;
    }

    public abstract void onSwitchCase(boolean SwitchBinary);

    @Override
    public void renderWidget(GuiGraphics p_93676_, int p_93677_, int p_93678_, float p_93679_) {
        Minecraft minecraft = Minecraft.getInstance();
        Font font = minecraft.font;
        renderBar(p_93676_, p_93677_, p_93678_, p_93679_);
        renderBlock(p_93676_, p_93677_, p_93678_, p_93679_);
        int j = getFGColor();
        int left = this.getX() + (binary ? 0 : this.width / 2 ) - 1;
        float scale = this.width < 20 ? this.width / 20.0F : 1.0F;
        if(time > 0) {
            p_93676_.pose().pushPose();
//            p_93676_.pose().scale(scale,scale,1.0F);
            p_93676_.drawString(font, binary ? components[1] : components[0], (int) (left / scale), (int) ((this.getY() + this.height / 2) / scale - 4 ), j | Mth.ceil(this.alpha * 255.0F) << 24);
            p_93676_.pose().popPose();
            time -- ;
        }
    }

    public void renderBar(GuiGraphics p_93676_, int p_93677_, int p_93678_, float p_93679_){
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();
        int left = 0;
        int top  = binary ? 60 : 0;
        int right = 128 - this.width / 2;
        int bottom = top + 20 - this.height / 2;
        p_93676_.blit(I_WIDGETS_LOCATION, this.getX(), this.getY(), left, top , this.width / 2, this.height / 2,128,128);
        p_93676_.blit(I_WIDGETS_LOCATION, this.getX() + this.width / 2, this.getY(), right, top , this.width / 2, this.height / 2,128,128);
        p_93676_.blit(I_WIDGETS_LOCATION, this.getX(), this.getY() + this.height / 2, left, bottom, this.width / 2, this.height / 2,128,128);
        p_93676_.blit(I_WIDGETS_LOCATION, this.getX() + this.width / 2, this.getY() + this.height / 2, right, bottom, this.width / 2, this.height / 2,128,128);
    }

    public void renderBlock(GuiGraphics p_93676_, int p_93677_, int p_93678_, float p_93679_){
        int i = this.isHovered? 2:1;
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();
        int left = this.getX() + (binary ? this.width / 2 : 0);
        p_93676_.blit(I_WIDGETS_LOCATION,left, this.getY() + this.height / 2, 0, (i+1) * 20 - this.height / 2, this.width / 4, this.height / 2,128,128);
        p_93676_.blit(I_WIDGETS_LOCATION,left + this.width / 4, this.getY() + this.height / 2, 128 - this.width / 4,  (i+1) * 20 - this.height / 2, this.width / 4, this.height / 2,128,128);
        p_93676_.blit(I_WIDGETS_LOCATION,left, this.getY(), 0, i * 20, this.width / 4, this.height / 2,128,128);
        p_93676_.blit(I_WIDGETS_LOCATION,left + this.width / 4, this.getY(), 128 - this.width / 4, i * 20, this.width / 4, this.height / 2,128,128);
    }

}
