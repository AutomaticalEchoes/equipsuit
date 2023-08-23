package com.AutomaticalEchoes.EquipSuit.client.gui;

import com.AutomaticalEchoes.EquipSuit.EquipSuitChange;
import com.AutomaticalEchoes.EquipSuit.api.modInterfcae.equipsuit.EquipSuit;
import com.AutomaticalEchoes.EquipSuit.api.modInterfcae.gui.EquipSuitHudInterface;
import com.AutomaticalEchoes.EquipSuit.api.modInterfcae.player.IPlayerInterface;
import com.AutomaticalEchoes.EquipSuit.api.utils.Messages;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class EquipSuitHud extends Screen implements EquipSuitHudInterface {
    public static final ResourceLocation I_GUI_LOCATION = new ResourceLocation(EquipSuitChange.MODID,"textures/gui/suit_gui.png");
    public static final ResourceLocation ICON_QUICK_SELECT = new ResourceLocation(EquipSuitChange.MODID,"textures/gui/qs.png");
    public static final ResourceLocation ICON_SEQUENCE = new ResourceLocation(EquipSuitChange.MODID,"textures/gui/squ.png");
    private static final Component FOCUS_SUIT_HUD=Component.translatable("focus suit hud");
    private final GuiGraphics guiGraphics;
    private static int ModeTipTime = 0;
    private static int LastMode = 0;

    public static EquipSuitHud Create(GuiGraphics guiGraphics){
        return new EquipSuitHud(FOCUS_SUIT_HUD,guiGraphics);
    }

    protected EquipSuitHud(Component p_96550_, GuiGraphics guiGraphics) {
        super(p_96550_);
        this.minecraft = Minecraft.getInstance();
        this.width = minecraft.getWindow().getGuiScaledWidth();
        this.height = minecraft.getWindow().getGuiScaledHeight();
        this.guiGraphics = guiGraphics;
        this.font = minecraft.font;
    }

    @Override
    public void renderSimple(int focus){
        int leftPos_0 = GameWindowWidth() / 2 + 18 * 4 + 10;
        int topPos_0 = GameWindowHeight() - 6;
        MutableComponent translatable = Component.translatable(Messages.SUIT_NUM[focus]);
        translatable.setStyle(Style.EMPTY.withColor(Messages.SUIT_NUM_COLORS[focus]));
        this.guiGraphics.renderTooltip(font,translatable,leftPos_0,topPos_0);
        if(LastMode != ChangeMod()){
            ModeTipTime = 1500;
        }
        if(ModeTipTime > 0){
            guiGraphics.drawString(font,Component.translatable(Messages.TAG_MODE +Messages.MODE_NAME[ChangeMod()])  ,leftPos_0+10,topPos_0-28, 0xFFFFFF);
            ModeTipTime--;
        }
        LastMode = ChangeMod();

    }

    @Override
    public void renderALl(int focus) {
        int v = (int) (Alpha() * 2550);
        int stringColor = 0x00FFFFFF | v << 24;
        renderBg();
        for (int i = 0; i < 4; i++) {
            renderNum(i, i == focus);
        }
        EquipSuit equipSuit = ((IPlayerInterface) Minecraft.getInstance().player).getSuitStack().getEquipSuitList().get(focus);
        renderMode();
        guiGraphics.drawString(font,Component.translatable(equipSuit.getName()).withStyle(Style.EMPTY)  ,StartX() + 18 , StartY() + 4, stringColor);
    }

    public void renderBg(){
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        guiGraphics.setColor(1.0F,1.0F,1.0F,Alpha() /10);
        guiGraphics.blit(I_GUI_LOCATION, StartX(), StartY(), 0 , 16 , 16, 16,64,64);
        guiGraphics.blit(I_GUI_LOCATION, StartX() + 15, StartY(), 0 , 0 , 64, 16,64,64);
        RenderSystem.setShaderColor(1.0F,1.0F,1.0F,1.0F);
    }

    public void renderNum(int i, boolean isFocus){
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        float red = (Messages.SUIT_NUM_COLORS[i] >> 16 & 255) / 255.0F;
        float green =  (Messages.SUIT_NUM_COLORS[i] >> 8 & 255) / 255.0F;
        float blue =  (Messages.SUIT_NUM_COLORS[i] & 255) / 255.0F;
        float alpha = 0.05F * Alpha();
        int startY = StartY() + 16;
        int textureStartY = 26;
        if(isFocus){
            alpha = 0.09F * Alpha();
            startY = StartY() + 15;
            textureStartY = 20;
            RenderSystem.disableDepthTest();
            RenderSystem.enableDepthTest();
        }
        guiGraphics.setColor(red,green,blue,alpha);
        guiGraphics.blit(I_GUI_LOCATION,(i + 1) * 15 + StartX() , startY ,16,textureStartY,16,32 - textureStartY,64,64);
        guiGraphics.setColor(1.0F,1.0F,1.0F,1.0F);
        if(!isFocus) return;
        guiGraphics.pose().pushPose();
        guiGraphics.pose().scale(0.625F,0.625F,1.0F);
        guiGraphics.setColor(1.0F,1.0F,1.0F,alpha);
        guiGraphics.blit(I_GUI_LOCATION, ((i + 1) * 15 + 3 + StartX()) * 8 / 5, (startY + 2) * 8 / 5 , 16 * i , textureStartY + 16 , 16  , 32 - textureStartY ,64,64);
        guiGraphics.setColor(1.0F,1.0F,1.0F,1.0F);
        guiGraphics.pose().popPose();
    }

    public void renderMode(){
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();
        guiGraphics.setColor(1.0F,1.0F,1.0F,Alpha());
        guiGraphics.blit(ChangeMod() == 0 ? ICON_SEQUENCE : ICON_QUICK_SELECT,StartX(),StartY() , 0, 0, 16, 16,16,16);
        guiGraphics.setColor(1.0F,1.0F,1.0F,1.0F);
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
