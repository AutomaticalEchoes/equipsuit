package com.automaticalechoes.equipsuit.client.gui;

import com.automaticalechoes.equipsuit.EquipSuitChange;
import com.automaticalechoes.equipsuit.api.modInterfcae.gui.OnPress;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public abstract class BinarySwitchButton extends GuiButton {
    public static final ResourceLocation I_WIDGETS_LOCATION = new ResourceLocation(EquipSuitChange.MODID,"textures/gui/widgets.png");
    private static final OnPress<BinarySwitchButton> switchOnPress = binarySwitchButton -> {
        binarySwitchButton.binary = !binarySwitchButton.binary;
        binarySwitchButton.time = 100;
        binarySwitchButton.onSwitchCase(binarySwitchButton.binary);
    };
    public int time = 0 ;
    private final String[] components = new String[2];
    public boolean binary = false;

    public BinarySwitchButton(int p_i1020_1_, int p_i1020_2_, int p_i1020_3_, int width, int height, String p_i1020_4_) {
        super(p_i1020_1_, p_i1020_2_, p_i1020_3_, width, height, p_i1020_4_);
        this.components[0] = "OFF";
        this.components[1] = "ON";
    }

//    public BinarySwitchButton(int p_93728_, int p_93729_, int p_93730_, int p_93731_) {
//        super(p_93728_, p_93729_, p_93730_, p_93731_, Component.empty(), switchOnPress);
//        this.components[0] = Component.translatable("OFF");
//        this.components[1] = Component.translatable("ON");
//    }
//
//    public BinarySwitchButton(int p_93728_, int p_93729_, int p_93730_, int p_93731_,OnTooltip onTooltip) {
//        super(p_93728_, p_93729_, p_93730_, p_93731_, Component.empty(), switchOnPress , onTooltip);
//        this.components[0] = Component.translatable("OFF");
//        this.components[1] = Component.translatable("ON");
//    }


    @Override
    public void mouseReleased(int mouseX, int mouseY) {
        switchOnPress.action(this);
    }

    public BinarySwitchButton DefaultRight(){
        this.binary = true;
        return this;
    }

    public BinarySwitchButton ComponentsOff(String component){
        this.components[0] = component;
        return this;
    }
    public BinarySwitchButton ComponentsOn(String component){
        this.components[1] = component;
        return this;
    }

    public abstract void onSwitchCase(boolean SwitchBinary);



    @Override
    public void drawButton(Minecraft minecraft, int p_93677_, int p_93678_, float p_93679_) {
        FontRenderer font = minecraft.fontRenderer;
        renderBar(minecraft ,p_93677_, p_93678_, p_93679_);
        renderBlock(minecraft, p_93677_, p_93678_, p_93679_);
//        this.renderBg(p_93676_, minecraft, p_93677_, p_93678_);
        int left = this.x + (binary ? 3 : this.width / 2 ) - 1;
        float scale = this.width < 20 ? this.width / 20.0F : 1.0F;
        if(time > 0) {
//            p_93676_.pushPose();
//            p_93676_.scale(scale,scale,1.0F);
            GlStateManager.pushMatrix();
            GlStateManager.scale(scale,scale, 1.0F);
            drawString(font, binary ? components[1] : components[0], (int) (left / scale), (int) ((this.y + this.height / 2) / scale - 4 ), -1);
//            p_93676_.popPose();
            GlStateManager.popMatrix();
            time -- ;
        }
    }

    public void renderBar(Minecraft minecraft, int p_93677_, int p_93678_, float p_93679_){
//        RenderSystem.setShader(GameRenderer::getPositionTexShader);
//        RenderSystem.setShaderTexture(0,I_WIDGETS_LOCATION);
//        RenderSystem.enableBlend();
//        RenderSystem.defaultBlendFunc();
//        RenderSystem.enableDepthTest();
        GlStateManager.enableBlend();
        GlStateManager.enableDepth();
        minecraft.getTextureManager().bindTexture(I_WIDGETS_LOCATION);
        int Lx = binary ? 23 : 0;
        int Ly = binary ? 61 : 0;
        int left = Lx;
        int right = 200 - this.width / 2 + Lx;
        int top = 46 + Ly;
        int bottom = 66 - this.height / 2 + Ly;
        this.drawTexturedModalRect(this.x, this.y, left, top , this.width / 2, this.height / 2);
        this.drawTexturedModalRect(this.x + this.width / 2, this.y, right, top , this.width / 2, this.height / 2);
        this.drawTexturedModalRect(this.x, this.y + this.height / 2, left, bottom, this.width / 2, this.height / 2);
        this.drawTexturedModalRect(this.x + this.width / 2, this.y + this.height / 2, right, bottom, this.width / 2, this.height / 2);
    }

    public void renderBlock(Minecraft minecraft, int p_93677_, int p_93678_, float p_93679_){
//        RenderSystem.setShader(GameRenderer::getPositionTexShader);
//        RenderSystem.setShaderTexture(1, WIDGETS_LOCATION);
//        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, this.alpha);
//        int i = this.getYImage(this.isHovered);
//        RenderSystem.enableBlend();
//        RenderSystem.defaultBlendFunc();
//        RenderSystem.enableDepthTest();
        int i = this.hovered ? 2 : 1;
        minecraft.getTextureManager().bindTexture(I_WIDGETS_LOCATION);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        int left = this.x + (binary ? this.width / 2 + 1 : 0);
        this.drawTexturedModalRect(left, this.y + this.height / 2, 0, 46 + (i+1) * 20 - this.height / 2, this.width / 4, this.height / 2);
        this.drawTexturedModalRect(left + this.width / 4, this.y + this.height / 2, 200 - this.width / 4, 46 +  (i+1) * 20 - this.height / 2, this.width / 2, this.height / 2);
        this.drawTexturedModalRect(left, this.y, 0, 46 + i * 20, this.width / 4, this.height / 2);
        this.drawTexturedModalRect(left + this.width / 4, this.y, 200 - this.width / 4, 46 + i * 20, this.width / 2, this.height / 2);
    }




}
