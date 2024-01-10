package com.automaticalechoes.equipsuit.client.gui;

import com.automaticalechoes.equipsuit.EquipSuitChange;
import com.automaticalechoes.equipsuit.api.modInterfcae.equipsuit.EquipSuit;
import com.automaticalechoes.equipsuit.api.modInterfcae.gui.EquipSuitHudInterface;
import com.automaticalechoes.equipsuit.api.modInterfcae.player.IPlayerInterface;
import com.automaticalechoes.equipsuit.api.utils.Messages;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class EquipSuitHud extends Gui implements EquipSuitHudInterface {
    public static final ResourceLocation I_GUI_LOCATION = new ResourceLocation(EquipSuitChange.MODID,"textures/gui/suit_gui.png");
    public static final ResourceLocation ICON_QUICK_SELECT = new ResourceLocation(EquipSuitChange.MODID,"textures/gui/qs.png");
    public static final ResourceLocation ICON_SEQUENCE = new ResourceLocation(EquipSuitChange.MODID,"textures/gui/squ.png");
    private static final ITextComponent FOCUS_SUIT_HUD = new TextComponentString("focus suit hud");
    private static int ModeTipTime = 0;
    private static boolean LastMode = false;
    private static int guiScaleWidth = 0;
    private static int guiScaleHeight = 0;
    private final Minecraft mc;
    private final FontRenderer fontRenderer;

    public EquipSuitHud(){
        mc = Minecraft.getMinecraft();
        fontRenderer = mc.fontRenderer;
    }

//    protected EquipSuitHud(Component p_96550_) {
//        super(p_96550_);
//        this.minecraft = Minecraft.getInstance();
//        this.width = minecraft.getWindow().getGuiScaledWidth();
//        this.height = minecraft.getWindow().getGuiScaledHeight();
//        this.font = minecraft.font;
//        this.itemRenderer=minecraft.getItemRenderer();
//    }

    @Override
    public void renderSimple(int focus){
//        RenderSystem.enableBlend();
//        RenderSystem.defaultBlendFunc();
//        RenderSystem.disableDepthTest();
        GlStateManager.enableBlend();
        int leftPos_0 = this.GameWindowWidth() / 2 + 90;
        int topPos_0 = (int) Math.floor(this.GameWindowHeight() - 22);
        renderSimpleBg(leftPos_0, topPos_0);
        renderSimpleNum(focus,leftPos_0,topPos_0);
        if(LastMode != EquipSuitHudInterface.ChangeMod()){
            ModeTipTime = 100;
        }

        if(ModeTipTime > 0){
            TextComponentTranslation mutableComponent = Messages.MODE_CHANGE_MESSAGE[EquipSuitHudInterface.ChangeMod()? 0 : 1];
            int width = this.fontRenderer.getStringWidth(mutableComponent.getFormattedText());
            this.fontRenderer.getStringWidth(mutableComponent.getUnformattedComponentText());
            int height = GameWindowHeight() - 73;
            if (this.mc.playerController.shouldDrawHUD()) {
                height += 14;
            }
//            poseStack.pushPose();
            GlStateManager.pushMatrix();
            int l = (int)((float)ModeTipTime * 256.0F / 10.0F);
            if (l > 255) {
                l = 255;
            }
            int color = 16777215 + (l << 24);
            this.fontRenderer.drawString(mutableComponent.getUnformattedComponentText(),(GameWindowWidth() - width) / 2, height, color);
//            drawString(poseStack, font, mutableComponent, (GameWindowWidth() - width) / 2, height, color);
//            poseStack.popPose();
            GlStateManager.popMatrix();
            ModeTipTime--;
        }
        LastMode = EquipSuitHudInterface.ChangeMod();
//        RenderSystem.disableBlend();
    }



    @Override
    public void initResolution(ScaledResolution scaledResolution) {
        guiScaleHeight = scaledResolution.getScaledHeight();
        guiScaleWidth = scaledResolution.getScaledWidth();
    }

    @Override
    public int GameWindowWidth() {
        return guiScaleWidth;
    }

    @Override
    public int GameWindowHeight() {
        return guiScaleHeight;
    }

    public void renderSimpleBg(int x, int y ){
        GlStateManager.pushMatrix();
        this.mc.getTextureManager().bindTexture(I_GUI_LOCATION);
        GlStateManager.color(0.5F, 0.5F ,0.5F, 1.0F);

//        RenderSystem.setShader(GameRenderer::getPositionTexShader);
//        RenderSystem.setShaderTexture(0,I_GUI_LOCATION);
//        RenderSystem.setShaderColor(0.5F,0.5F,0.5F,1.0F);
//        poseStack.pushPose();
//        int j = this.getBlitOffset();
//        this.setBlitOffset(-90);
//        blit(poseStack, x, y,48,16,16,16,64,64);

        this.zLevel = -90F;
        drawModalRectWithCustomSizedTextureWithZ(x, y, 48, 16, 16, 16,64,64);
        this.zLevel = 0;
//        this.setBlitOffset(j);
//        RenderSystem.setShaderColor(1.0F,1.0F,1.0F,1.0F);
//        poseStack.popPose();
        GlStateManager.color(1.0F,1.0F,1.0F,1.0F);
        GlStateManager.popMatrix();
    }

    public void renderSimpleNum(int i,int x, int y){
//        RenderSystem.setShader(GameRenderer::getPositionTexShader);
//        RenderSystem.setShaderTexture(0,I_GUI_LOCATION);
        float red = (Messages.SUIT_NUM_COLORS[i] >> 16 & 255) / 255.0F;
        float green = (Messages.SUIT_NUM_COLORS[i] >> 8 & 255) / 255.0F;
        float blue = (Messages.SUIT_NUM_COLORS[i] & 255) / 255.0F;
//        RenderSystem.setShaderColor(red,green,blue,1.0F);
//        poseStack.pushPose();
//        poseStack.scale(0.5F,0.5F,1.0F);
        GlStateManager.pushMatrix();
        this.mc.getTextureManager().bindTexture(I_GUI_LOCATION);
        GlStateManager.scale(0.5, 0.5, 1.0);
        GlStateManager.color(red,green,blue,1.0F);
//        blit(poseStack, (x + 3) * 2, (y + 3) * 2, 16 * i , 36 , 16  , 12 ,64,64);
        drawModalRectWithCustomSizedTexture( (x + 2) * 2 + 1, (y + 1) * 2, 16 * i , 32, 16 , 18, 64, 64);
//        poseStack.popPose();
        GlStateManager.color(1.0F,1.0F,1.0F,1.0F);
        GlStateManager.popMatrix();
    }

    @Override
    public void renderALl(int focus) {
        int v = (int) (Alpha() * 255);
        int stringColor = 0x00FFFFFF | v << 24;
//        poseStack.pushPose();
        GlStateManager.pushMatrix();
        renderAllBg();
        for (int i = 0; i < 4; i++) {
            renderAllNum(i, i == focus);
        }
        EquipSuit equipSuit = ((IPlayerInterface) this.mc.player).getSuitStack().getEquipSuitList().get(focus);
        renderMode();
        GlStateManager.popMatrix();
//        poseStack.popPose();
        this.fontRenderer.drawString(equipSuit.getName(), StartX() + 18, StartY() + 4, stringColor, false);
//        drawString(poseStack, font,Component.translatable(equipSuit.getName()).withStyle(Style.EMPTY)  ,StartX() + 18 , StartY() + 4, stringColor);
    }

    public void renderAllBg(){
        this.mc.getTextureManager().bindTexture(I_GUI_LOCATION);
        GlStateManager.pushMatrix();
        GlStateManager.color(1.0F,1.0F,1.0F,Alpha());
        GlStateManager.enableBlend();
        GlStateManager.enableDepth();
        drawModalRectWithCustomSizedTexture(StartX(), StartY(), 0 ,16, 16, 16,64,64);
        drawModalRectWithCustomSizedTexture(StartX() + 15, StartY(), 0 ,0, 64, 16,64,64);
//        RenderSystem.setShader(GameRenderer::getPositionTexShader);
//        RenderSystem.setShaderTexture(0,I_GUI_LOCATION);
//        RenderSystem.setShaderColor(1.0F,1.0F,1.0F,Alpha());
//        RenderSystem.enableBlend();
//        RenderSystem.defaultBlendFunc();
//        RenderSystem.enableDepthTest();
//        blit(poseStack, StartX(), StartY(), 0 , 16 , 16, 16,64,64);
//        blit(poseStack, StartX() + 15, StartY(), 0 , 0 , 64, 16,64,64);
//        RenderSystem.setShaderColor(1.0F,1.0F,1.0F,1.0F);
        GlStateManager.popMatrix();
    }

    public void renderAllNum(int i, boolean isFocus){
        float red = (Messages.SUIT_NUM_COLORS[i] >> 16 & 255) / 255.0F;
        float green = (Messages.SUIT_NUM_COLORS[i] >> 8 & 255) / 255.0F;
        float blue = (Messages.SUIT_NUM_COLORS[i] & 255) / 255.0F;
        float alpha = 0.5F * Alpha();
        int startY = StartY() + 16;
        int textureStartY = 26;
        GlStateManager.pushMatrix();
        if(isFocus){
            alpha = Alpha();
            startY = StartY() + 15;
            textureStartY = 20;
            GlStateManager.disableDepth();
        }
//        poseStack.pushPose();
//        RenderSystem.setShaderColor(red,green,blue,alpha);
//        blit(poseStack,(i + 1) * 15 + StartX() , startY ,16,textureStartY,16,32 - textureStartY,64,64);
//        RenderSystem.setShaderColor(1.0F,1.0F,1.0F,1.0F);
//        poseStack.popPose();
        GlStateManager.color(red, green, blue ,alpha);
        drawModalRectWithCustomSizedTexture((i + 1) * 15 + StartX() , startY ,16,textureStartY,16,32 - textureStartY, 64,64);
        GlStateManager.popMatrix();
        if(!isFocus) return;
//        poseStack.pushPose();
//        poseStack.scale(0.625F,0.625F,1.0F);
//        RenderSystem.setShaderColor(1.0F,1.0F,1.0F,alpha);
//        blit(poseStack, ((i + 1) * 15 + 3 + StartX()) * 8 / 5, (startY + 2) * 8 / 5 , 16 * i , textureStartY + 16 , 16  , 32 - textureStartY ,64,64);
//        RenderSystem.setShaderColor(1.0F,1.0F,1.0F,1.0F);
//        poseStack.popPose();
        GlStateManager.pushMatrix();
        GlStateManager.scale(0.625F,0.625F,1.0F);
        GlStateManager.color(1.0F, 1.0F, 1.0F , alpha);
        drawModalRectWithCustomSizedTexture(((i + 1) * 15 + 3 + StartX()) * 8 / 5, (startY + 2) * 8 / 5 , 16 * i , textureStartY + 16 , 16  , 32 - textureStartY,64,64);
        GlStateManager.popMatrix();
    }

    public void renderMode(){
//        poseStack.pushPose();
//        RenderSystem.setShader(GameRenderer::getPositionTexShader);
//        RenderSystem.setShaderTexture(0 ,ChangeMod() == 0 ? ICON_SEQUENCE : ICON_QUICK_SELECT);
//        RenderSystem.setShaderColor(1.0F,1.0F,1.0F,Alpha());
//        RenderSystem.enableBlend();
//        RenderSystem.defaultBlendFunc();
//        RenderSystem.enableDepthTest();
//        blit(poseStack,StartX(),StartY() , 0, 0, 16, 16,16,16);
//        RenderSystem.setShaderColor(1.0F,1.0F,1.0F,1.0F);
//        poseStack.popPose();
        GlStateManager.pushMatrix();
        this.mc.getTextureManager().bindTexture(!EquipSuitHudInterface.ChangeMod() ? ICON_SEQUENCE : ICON_QUICK_SELECT);
        GlStateManager.color(1.0F,1.0F,1.0F,Alpha());
        GlStateManager.enableBlend();
        GlStateManager.enableDepth();
        drawModalRectWithCustomSizedTexture(StartX(), StartY() , 0, 0, 16, 16, 16, 16);
        GlStateManager.popMatrix();
    }

    @Override
    public int guiWidth() {
        return 80;
    }

    @Override
    public int guiHeight() {
        return 28;
    }


    public void drawModalRectWithCustomSizedTextureWithZ(int x, int y, float u, float v, int width, int height, float textureWidth, float textureHeight)
    {
        float f = 1.0F / textureWidth;
        float f1 = 1.0F / textureHeight;
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.pos((double)x, (double)(y + height), zLevel).tex((double)(u * f), (double)((v + (float)height) * f1)).endVertex();
        bufferbuilder.pos((double)(x + width), (double)(y + height), zLevel).tex((double)((u + (float)width) * f), (double)((v + (float)height) * f1)).endVertex();
        bufferbuilder.pos((double)(x + width), (double)y, zLevel).tex((double)((u + (float)width) * f), (double)(v * f1)).endVertex();
        bufferbuilder.pos((double)x, (double)y, zLevel).tex((double)(u * f), (double)(v * f1)).endVertex();
        tessellator.draw();
    }


}
