package com.AutomaticalEchoes.EquipSuit.client.gui;

import com.AutomaticalEchoes.EquipSuit.EquipSuitChange;
import com.AutomaticalEchoes.EquipSuit.api.config.EquipSuitClientConfig;
import com.AutomaticalEchoes.EquipSuit.api.modInterfcae.gui.FocusSuitHud;
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
public class FocusSuitHUD extends Screen implements Widget, FocusSuitHud {
    public static final ResourceLocation I_GUI_LOCATION = new ResourceLocation(EquipSuitChange.MODID,"textures/gui/suit_gui.png");
    public static final ResourceLocation ICON_QUICK_SELECT = new ResourceLocation(EquipSuitChange.MODID,"textures/gui/qs.png");
    public static final ResourceLocation ICON_SEQUENCE = new ResourceLocation(EquipSuitChange.MODID,"textures/gui/squ.png");
    private static final Component FOCUS_SUIT_HUD=Component.translatable("focus suit hud");
    private static int leftPos_0,topPos_0,leftPos_1,topPos_1;
    private final PoseStack matrixStack;
    private static int ModeTipTime = 0;
    private int mode = 0;
    private float alpha = 0.1F ;

    public static FocusSuitHUD Create(PoseStack matrixStack){
        return new FocusSuitHUD(FOCUS_SUIT_HUD,matrixStack);
    }

    protected FocusSuitHUD(Component p_96550_, PoseStack matrixStack) {
        super(p_96550_);
        this.minecraft = Minecraft.getInstance();
        this.width = minecraft.getWindow().getGuiScaledWidth();
        this.height = minecraft.getWindow().getGuiScaledHeight();
        this.matrixStack=matrixStack;
        this.font = minecraft.font;
        this.itemRenderer=minecraft.getItemRenderer();
        this.mode = EquipSuitClientConfig.CHANGE_MODE.get();
    }

    public void render(){
        initPos();
        Integer focus = ((IPlayerInterface) (Minecraft.getInstance().player)).getFocus();
        this.alpha = EquipSuitClientConfig.ALPHA.get() / 1000.0F;

        if(EquipSuitClientConfig.HUD_MODE.get() == 0){
            MutableComponent translatable = Component.translatable(Messages.SUIT_NUM[focus]);
            translatable.setStyle(Style.EMPTY.withColor(Messages.SUIT_NUM_COLORS[focus]));
            this.renderTooltip(matrixStack,translatable,leftPos_0,topPos_0);
            if(ModeTipTime > 0){
                drawString(matrixStack, font,Component.translatable(Messages.TAG_MODE +Messages.MODE_NAME[mode])  ,leftPos_0+10,topPos_0-28, 0xFFFFFF);
                ModeTipTime--;
            }
        }else {
            int v = (int) (alpha * 2550);
            int stringColor = 0x00FFFFFF | v << 24;
            matrixStack.pushPose();
            renderBg();
            for (int i = 0; i < 4; i++) {
                renderNum(i, i == focus);
            }
            renderMode();
            matrixStack.popPose();
            drawString(matrixStack, font,Component.translatable(EquipSuitClientConfig.SUIT_NAME.get().get(focus)).withStyle(Style.EMPTY)  ,leftPos_1 + 18 , topPos_1 + 4, stringColor);
        }
    }

    public void renderBg(){
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0,I_GUI_LOCATION);
        RenderSystem.setShaderColor(1.0F,1.0F,1.0F,this.alpha);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();
        blit(matrixStack, leftPos_1, topPos_1, 0 , 16 , 16, 16,64,64);
        blit(matrixStack, leftPos_1 + 15, topPos_1, 0 , 0 , 64, 16,64,64);
        RenderSystem.setShaderColor(1.0F,1.0F,1.0F,1.0F);
    }

    public void renderNum(int i, boolean isFocus){
        float red = (Messages.SUIT_NUM_COLORS[i] >> 16 & 255) / 255.0F;
        float green =  (Messages.SUIT_NUM_COLORS[i] >> 8 & 255) / 255.0F;
        float blue =  (Messages.SUIT_NUM_COLORS[i] & 255) / 255.0F;
        float alpha = 0.5F * this.alpha;
        int startY = topPos_1 + 16;
        int textureStartY = 26;
        if(isFocus){
            alpha = this.alpha;
            startY = topPos_1 + 15;
            textureStartY = 20;
            RenderSystem.disableDepthTest();
            RenderSystem.enableDepthTest();
        }
        matrixStack.pushPose();
        RenderSystem.setShaderColor(red,green,blue,alpha);
        blit(matrixStack,(i + 1) * 15 + leftPos_1 , startY ,16,textureStartY,16,32 - textureStartY,64,64);
        RenderSystem.setShaderColor(1.0F,1.0F,1.0F,1.0F);
        matrixStack.popPose();
        if(!isFocus) return;
        matrixStack.pushPose();
        matrixStack.scale(0.625F,0.625F,1.0F);
        RenderSystem.setShaderColor(1.0F,1.0F,1.0F,alpha);
        blit(matrixStack, ((i + 1) * 15 + 3 + leftPos_1) * 8 / 5, (startY + 2) * 8 / 5 , 16 * i , textureStartY + 16 , 16  , 32 - textureStartY ,64,64);
        RenderSystem.setShaderColor(1.0F,1.0F,1.0F,1.0F);
        matrixStack.popPose();
    }

    public void renderMode(){
        boolean b = mode == 0;
        matrixStack.pushPose();
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0 ,b ? ICON_SEQUENCE : ICON_QUICK_SELECT);
        RenderSystem.setShaderColor(1.0F,1.0F,1.0F,this.alpha * 10);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();
        blit(matrixStack, leftPos_1, topPos_1 , 0, 0, 16, 16,16,16);
        RenderSystem.setShaderColor(1.0F,1.0F,1.0F,1.0F);
        matrixStack.popPose();
    }

    public void setMode(int i){
        this.mode = i;
        ModeTipTime = 1500;
    }

    public void initPos(){
        this.width = minecraft.getWindow().getGuiScaledWidth();
        this.height = minecraft.getWindow().getGuiScaledHeight();
        leftPos_0 = this.width / 2 + 18 * 4 + 10;
        topPos_0 = this.height - 6;
        leftPos_1 = EquipSuitClientConfig.LAYER_START_X.get() > width -80 ? width - 80 : EquipSuitClientConfig.LAYER_START_X.get();
        topPos_1 = EquipSuitClientConfig.LAYER_START_Y.get() > height ? 0 : height - EquipSuitClientConfig.LAYER_START_Y.get()  ;
    }



}
