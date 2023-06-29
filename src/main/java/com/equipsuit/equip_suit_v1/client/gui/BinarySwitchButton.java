package com.equipsuit.equip_suit_v1.client.gui;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;

public abstract class BinarySwitchButton extends Button {
    private static final OnPress switchOnPress = p_93751_ -> {
        if(p_93751_ instanceof  BinarySwitchButton binarySwitchButton){
            binarySwitchButton.binary = !binarySwitchButton.binary;
            binarySwitchButton.onSwitchCase(binarySwitchButton.binary);
        }
    };
    private boolean defaultSwitch = false;
    public boolean binary = false;
    public BinarySwitchButton(int p_93728_, int p_93729_, int p_93730_, int p_93731_, Component p_93732_, OnTooltip p_93734_) {
        super(p_93728_, p_93729_, p_93730_, p_93731_, p_93732_, switchOnPress, p_93734_);
    }
    public BinarySwitchButton(int p_93728_, int p_93729_, int p_93730_, int p_93731_, Component p_93732_) {
        super(p_93728_, p_93729_, p_93730_, p_93731_, p_93732_, switchOnPress);
    }

    public BinarySwitchButton DefaultRight(){
        this.defaultSwitch = true;
        return this;
    }

    public abstract void onSwitchCase(boolean SwitchBinary);

    @Override
    public void renderButton(PoseStack p_93676_, int p_93677_, int p_93678_, float p_93679_) {
//        51, 204, 102
        Minecraft minecraft = Minecraft.getInstance();
        Font font = minecraft.font;
        renderBar(p_93676_, p_93677_, p_93678_, p_93679_);
        renderBlock(p_93676_, p_93677_, p_93678_, p_93679_);
        this.renderBg(p_93676_, minecraft, p_93677_, p_93678_);
        int j = getFGColor();
        drawCenteredString(p_93676_, font, this.getMessage(), this.x + this.width / 2, this.y + (this.height - 8) / 2, j | Mth.ceil(this.alpha * 255.0F) << 24);

        if (this.isHoveredOrFocused()) {
            this.renderToolTip(p_93676_,p_93677_,p_93678_);
        }
    }

    public void renderBar(PoseStack p_93676_, int p_93677_, int p_93678_, float p_93679_){
        float rgbX = binary ? ( 19 / 255) :  1.0F  ;
        float rgbY = binary ? ( 206 / 255) :  1.0F ;
        float rgbZ = binary ? ( 102 / 255) :  1.0F ;
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, WIDGETS_LOCATION);
        RenderSystem.setShaderColor(rgbX , rgbY , rgbZ ,  this.alpha);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();
        this.blit(p_93676_, this.x, this.y, 0, 46 , this.width / 2, this.height / 2);
        this.blit(p_93676_, this.x + this.width / 2, this.y, 200 - this.width / 2, 46 , this.width / 2, this.height / 2);
        this.blit(p_93676_, this.x, this.y + this.height / 2, 0, 66  - this.height / 2, this.width / 2, this.height / 2);
        this.blit(p_93676_, this.x + this.width / 2, this.y + this.height / 2, 200 - this.width / 2, 66 - this.height / 2, this.width / 2, this.height / 2);
    }

    public void renderBlock(PoseStack p_93676_, int p_93677_, int p_93678_, float p_93679_){
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(1, WIDGETS_LOCATION);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, this.alpha);
        int i = this.getYImage(this.isHoveredOrFocused());
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();
        int left = this.x + (binary ? this.width / 2 + 1 : 0);
        this.blit(p_93676_,left, this.y + this.height / 2, 0, 46 + (i+1) * 20 - this.height / 2, this.width / 4, this.height / 2);
        this.blit(p_93676_,left + this.width / 4, this.y + this.height / 2, 200 - this.width / 4, 46 +  (i+1) * 20 - this.height / 2, this.width / 2, this.height / 2);
        this.blit(p_93676_,left, this.y, 0, 46 + i * 20, this.width / 4, this.height / 2);
        this.blit(p_93676_,left + this.width / 4, this.y, 200 - this.width / 4, 46 + i * 20, this.width / 2, this.height / 2);
    }


}
