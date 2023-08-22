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
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class EquipSuitHud extends Screen implements Widget, EquipSuitHudInterface {
    public static final ResourceLocation I_GUI_LOCATION = new ResourceLocation(EquipSuitChange.MODID,"textures/gui/suit_gui.png");
    public static final ResourceLocation ICON_QUICK_SELECT = new ResourceLocation(EquipSuitChange.MODID,"textures/gui/qs.png");
    public static final ResourceLocation ICON_SEQUENCE = new ResourceLocation(EquipSuitChange.MODID,"textures/gui/squ.png");
    private static final Component FOCUS_SUIT_HUD=Component.translatable("focus suit hud");
    private final PoseStack matrixStack;
    private static int ModeTipTime = 0;
    private static int LastMode = 0;

    public static EquipSuitHud Create(PoseStack matrixStack){
        return new EquipSuitHud(FOCUS_SUIT_HUD,matrixStack);
    }

    protected EquipSuitHud(Component p_96550_, PoseStack matrixStack) {
        super(p_96550_);
        this.minecraft = Minecraft.getInstance();
        this.width = minecraft.getWindow().getGuiScaledWidth();
        this.height = minecraft.getWindow().getGuiScaledHeight();
        this.matrixStack=matrixStack;
        this.font = minecraft.font;
        this.itemRenderer=minecraft.getItemRenderer();
    }

    @Override
    public void renderSimple(int focus){
        int leftPos_0 = GameWindowWidth() / 2 + 18 * 4 + 10;
        int topPos_0 = GameWindowHeight() - 6;
        MutableComponent translatable = Component.translatable(Messages.SUIT_NUM[focus]);
        translatable.setStyle(Style.EMPTY.withColor(Messages.SUIT_NUM_COLORS[focus]));
        this.renderTooltip(matrixStack,translatable,leftPos_0,topPos_0);
        if(LastMode != ChangeMod()){
            ModeTipTime = 1500;
        }
        if(ModeTipTime > 0){
            drawString(matrixStack, font,Component.translatable(Messages.TAG_MODE +Messages.MODE_NAME[ChangeMod()])  ,leftPos_0+10,topPos_0-28, 0xFFFFFF);
            ModeTipTime--;
        }
        LastMode = ChangeMod();

    }

    @Override
    public void renderALl(int focus) {
        int v = (int) (Alpha() * 2550);
        int stringColor = 0x00FFFFFF | v << 24;
        matrixStack.pushPose();
        renderBg();
        for (int i = 0; i < 4; i++) {
            renderNum(i, i == focus);
        }
        EquipSuit equipSuit = ((IPlayerInterface) Minecraft.getInstance().player).getSuitStack().getEquipSuitList().get(focus);
        renderMode();
        matrixStack.popPose();
        drawString(matrixStack, font,Component.translatable(equipSuit.getName()).withStyle(Style.EMPTY)  ,StartX() + 18 , StartY() + 4, stringColor);
    }

    public void renderBg(){
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0,I_GUI_LOCATION);
        RenderSystem.setShaderColor(1.0F,1.0F,1.0F,Alpha());
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();
        blit(matrixStack, StartX(), StartY(), 0 , 16 , 16, 16,64,64);
        blit(matrixStack, StartX() + 15, StartY(), 0 , 0 , 64, 16,64,64);
        RenderSystem.setShaderColor(1.0F,1.0F,1.0F,1.0F);
    }

    public void renderNum(int i, boolean isFocus){
        float red = (Messages.SUIT_NUM_COLORS[i] >> 16 & 255) / 255.0F;
        float green =  (Messages.SUIT_NUM_COLORS[i] >> 8 & 255) / 255.0F;
        float blue =  (Messages.SUIT_NUM_COLORS[i] & 255) / 255.0F;
        float alpha = 0.5F * StartX();
        int startY = StartY() + 16;
        int textureStartY = 26;
        if(isFocus){
            alpha =  Alpha();
            startY = StartY() + 15;
            textureStartY = 20;
            RenderSystem.disableDepthTest();
            RenderSystem.enableDepthTest();
        }
        matrixStack.pushPose();
        RenderSystem.setShaderColor(red,green,blue,alpha);
        blit(matrixStack,(i + 1) * 15 + StartX() , startY ,16,textureStartY,16,32 - textureStartY,64,64);
        RenderSystem.setShaderColor(1.0F,1.0F,1.0F,1.0F);
        matrixStack.popPose();
        if(!isFocus) return;
        matrixStack.pushPose();
        matrixStack.scale(0.625F,0.625F,1.0F);
        RenderSystem.setShaderColor(1.0F,1.0F,1.0F,alpha);
        blit(matrixStack, ((i + 1) * 15 + 3 + StartX()) * 8 / 5, (startY + 2) * 8 / 5 , 16 * i , textureStartY + 16 , 16  , 32 - textureStartY ,64,64);
        RenderSystem.setShaderColor(1.0F,1.0F,1.0F,1.0F);
        matrixStack.popPose();
    }

    public void renderMode(){
        matrixStack.pushPose();
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0 ,ChangeMod() == 0 ? ICON_SEQUENCE : ICON_QUICK_SELECT);
        RenderSystem.setShaderColor(1.0F,1.0F,1.0F,Alpha() * 10);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();
        blit(matrixStack,StartX(),StartY() , 0, 0, 16, 16,16,16);
        RenderSystem.setShaderColor(1.0F,1.0F,1.0F,1.0F);
        matrixStack.popPose();
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
