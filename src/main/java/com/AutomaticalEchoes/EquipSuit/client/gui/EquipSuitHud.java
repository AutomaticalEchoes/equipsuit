package com.AutomaticalEchoes.EquipSuit.client.gui;

import com.AutomaticalEchoes.EquipSuit.EquipSuitChange;
import com.AutomaticalEchoes.EquipSuit.api.modInterfcae.equipsuit.EquipSuit;
import com.AutomaticalEchoes.EquipSuit.api.modInterfcae.gui.EquipSuitHudInterface;
import com.AutomaticalEchoes.EquipSuit.api.modInterfcae.player.IPlayerInterface;
import com.AutomaticalEchoes.EquipSuit.api.utils.Messages;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.protocol.game.ClientboundMoveEntityPacket;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class EquipSuitHud extends Screen implements Widget, EquipSuitHudInterface {
    public static final ResourceLocation I_GUI_LOCATION = new ResourceLocation(EquipSuitChange.MODID,"textures/gui/suit_gui.png");
    public static final ResourceLocation ICON_QUICK_SELECT = new ResourceLocation(EquipSuitChange.MODID,"textures/gui/qs.png");
    public static final ResourceLocation ICON_SEQUENCE = new ResourceLocation(EquipSuitChange.MODID,"textures/gui/squ.png");
    private static final Component FOCUS_SUIT_HUD=Component.translatable("focus suit hud");
    private static int ModeTipTime = 0;
    private static int LastMode = 0;

    public static EquipSuitHud Create(){
        return new EquipSuitHud(FOCUS_SUIT_HUD);
    }

    protected EquipSuitHud(Component p_96550_) {
        super(p_96550_);
        this.minecraft = Minecraft.getInstance();
        this.width = minecraft.getWindow().getGuiScaledWidth();
        this.height = minecraft.getWindow().getGuiScaledHeight();
        this.font = minecraft.font;
        this.itemRenderer=minecraft.getItemRenderer();
    }

    @Override
    public void renderSimple(PoseStack poseStack,int focus){
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.disableDepthTest();
        int leftPos_0 = GameWindowWidth() / 2 + 90;
        int topPos_0 = Mth.floor(GameWindowHeight() - 22.0F);
        renderSimpleBg(poseStack, leftPos_0, topPos_0);
        renderSimpleNum(poseStack,focus,leftPos_0,topPos_0);
        if(LastMode != ChangeMod()){
            ModeTipTime = 100;
        }

        if(ModeTipTime > 0){
            MutableComponent mutableComponent = Messages.MODE_CHANGE_MESSAGE[ChangeMod()];
            int width = this.font.width(mutableComponent.getString());
            int height = GameWindowHeight() - 73;
            if (!minecraft.gameMode.canHurtPlayer()) {
                height += 14;
            }
            poseStack.pushPose();
            int l = (int)((float)ModeTipTime * 256.0F / 10.0F);
            if (l > 255) {
                l = 255;
            }
            int color =  16777215 + (l << 24);
            drawString(poseStack, font, mutableComponent, (GameWindowWidth() - width) / 2, height, color);
            poseStack.popPose();
            ModeTipTime--;
        }
        LastMode = ChangeMod();
        RenderSystem.disableBlend();
    }

    public void renderSimpleBg(PoseStack poseStack, int x, int y ){
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0,I_GUI_LOCATION);
        RenderSystem.setShaderColor(0.5F,0.5F,0.5F,1.0F);
        poseStack.pushPose();
        int j = this.getBlitOffset();
        this.setBlitOffset(-90);

        blit(poseStack, x, y,48,16,16,16,64,64);
        this.setBlitOffset(j);
        RenderSystem.setShaderColor(1.0F,1.0F,1.0F,1.0F);
        poseStack.popPose();
    }

    public void renderSimpleNum(PoseStack poseStack, int i,int x, int y){
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0,I_GUI_LOCATION);
        float red = (Messages.SUIT_NUM_COLORS[i] >> 16 & 255) / 255.0F;
        float green = (Messages.SUIT_NUM_COLORS[i] >> 8 & 255) / 255.0F;
        float blue = (Messages.SUIT_NUM_COLORS[i] & 255) / 255.0F;
        RenderSystem.setShaderColor(red,green,blue,1.0F);
        poseStack.pushPose();
        poseStack.scale(0.5F,0.5F,1.0F);
        blit(poseStack, (x + 3) * 2, (y + 3) * 2, 16 * i , 36 , 16  , 12 ,64,64);
        RenderSystem.setShaderColor(1.0F,1.0F,1.0F,1.0F);
        poseStack.popPose();
    }

    @Override
    public void renderALl(PoseStack poseStack, int focus) {
        int v = (int) (Alpha() * 255);
        int stringColor = 0x00FFFFFF | v << 24;
        poseStack.pushPose();
        renderAllBg(poseStack);
        for (int i = 0; i < 4; i++) {
            renderAllNum(poseStack, i, i == focus);
        }
        EquipSuit equipSuit = ((IPlayerInterface) Minecraft.getInstance().player).getSuitStack().getEquipSuitList().get(focus);
        renderMode(poseStack);
        poseStack.popPose();
        drawString(poseStack, font,Component.translatable(equipSuit.getName()).withStyle(Style.EMPTY)  ,StartX() + 18 , StartY() + 4, stringColor);
    }

    public void renderAllBg(PoseStack poseStack){
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0,I_GUI_LOCATION);
        RenderSystem.setShaderColor(1.0F,1.0F,1.0F,Alpha());
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();
        blit(poseStack, StartX(), StartY(), 0 , 16 , 16, 16,64,64);
        blit(poseStack, StartX() + 15, StartY(), 0 , 0 , 64, 16,64,64);
        RenderSystem.setShaderColor(1.0F,1.0F,1.0F,1.0F);
    }

    public void renderAllNum(PoseStack poseStack, int i, boolean isFocus){
        float red = (Messages.SUIT_NUM_COLORS[i] >> 16 & 255) / 255.0F;
        float green = (Messages.SUIT_NUM_COLORS[i] >> 8 & 255) / 255.0F;
        float blue = (Messages.SUIT_NUM_COLORS[i] & 255) / 255.0F;
        float alpha = 0.5F * Alpha();
        int startY = StartY() + 16;
        int textureStartY = 26;
        if(isFocus){
            alpha = Alpha();
            startY = StartY() + 15;
            textureStartY = 20;
            RenderSystem.disableDepthTest();
            RenderSystem.enableDepthTest();
        }
        poseStack.pushPose();
        RenderSystem.setShaderColor(red,green,blue,alpha);
        blit(poseStack,(i + 1) * 15 + StartX() , startY ,16,textureStartY,16,32 - textureStartY,64,64);
        RenderSystem.setShaderColor(1.0F,1.0F,1.0F,1.0F);
        poseStack.popPose();
        if(!isFocus) return;
        poseStack.pushPose();
        poseStack.scale(0.625F,0.625F,1.0F);
        RenderSystem.setShaderColor(1.0F,1.0F,1.0F,alpha);
        blit(poseStack, ((i + 1) * 15 + 3 + StartX()) * 8 / 5, (startY + 2) * 8 / 5 , 16 * i , textureStartY + 16 , 16  , 32 - textureStartY ,64,64);
        RenderSystem.setShaderColor(1.0F,1.0F,1.0F,1.0F);
        poseStack.popPose();
    }

    public void renderMode(PoseStack poseStack){
        poseStack.pushPose();
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0 ,ChangeMod() == 0 ? ICON_SEQUENCE : ICON_QUICK_SELECT);
        RenderSystem.setShaderColor(1.0F,1.0F,1.0F,Alpha());
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();
        blit(poseStack,StartX(),StartY() , 0, 0, 16, 16,16,16);
        RenderSystem.setShaderColor(1.0F,1.0F,1.0F,1.0F);
        poseStack.popPose();
    }

    @Override
    public int guiWidth() {
        return 80;
    }

    @Override
    public int guiHeight() {
        return 28;
    }


}
