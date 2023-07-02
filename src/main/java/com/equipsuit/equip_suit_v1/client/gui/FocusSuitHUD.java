package com.equipsuit.equip_suit_v1.client.gui;

import com.equipsuit.equip_suit_v1.EquipSuitChange;
import com.equipsuit.equip_suit_v1.api.config.EquipSuitClientConfig;
import com.equipsuit.equip_suit_v1.api.modInterfcae.equipsuit.ContainerEquipSuit;
import com.equipsuit.equip_suit_v1.api.modInterfcae.equipsuit.ContainerEquipSuitImpl;
import com.equipsuit.equip_suit_v1.api.modInterfcae.gui.FocusSuitHud;
import com.equipsuit.equip_suit_v1.api.modInterfcae.player.IPlayerInterface;
import com.equipsuit.equip_suit_v1.api.utils.Messages;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.realms.RealmsLabel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.inventory.tooltip.BundleTooltip;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.Optional;

@OnlyIn(Dist.CLIENT)
public class FocusSuitHUD extends Screen implements Widget, FocusSuitHud {
    public static final ResourceLocation I_WIDGETS_LOCATION = new ResourceLocation(EquipSuitChange.MODID,"textures/gui/widgets.png");
    private static final Component FOCUS_SUIT_HUD=Component.translatable("focus suit hud");
    private static int leftPos,topPos;
    private final PoseStack matrixStack;
    private float scale = 0.7F;
    private static int ModeTipTime = 0;
    private static int SuitTipTime = 0;
    private int mode = 0;
    public static FocusSuitHUD Create(PoseStack matrixStack){
        return new FocusSuitHUD(FOCUS_SUIT_HUD,matrixStack);
    }

    protected FocusSuitHUD(Component p_96550_, PoseStack matrixStack) {
        super(p_96550_);
        this.minecraft = Minecraft.getInstance();
        this.width = minecraft.getWindow().getGuiScaledWidth();
        this.height = minecraft.getWindow().getGuiScaledHeight();
        this.matrixStack=matrixStack;
        this.font=minecraft.font;
        this.itemRenderer=minecraft.getItemRenderer();
        this.mode = EquipSuitClientConfig.CHANGE_MODE.get();
    }

    public void render(){
        initPos();
        Integer focus = ((IPlayerInterface) (Minecraft.getInstance().player)).getFocus();
        if(EquipSuitClientConfig.HUD_MODE.get() == 0){
            MutableComponent translatable = Component.translatable(Messages.SUIT_NUM[focus]);
            translatable.setStyle(Style.EMPTY.withColor(Messages.SUIT_NUM_COLORS[focus]));
            this.renderTooltip(matrixStack,translatable,leftPos,topPos);
        }else {
            matrixStack.pushPose();
            matrixStack.scale(scale,scale,1.0F);
            renderBg();
            renderFocus(focus);
            matrixStack.popPose();
            renderNum();
        }

        if(ModeTipTime > 0){
            drawString(matrixStack, font,Component.translatable(Messages.TAG_MODE +Messages.MODE_NAME[mode])  ,leftPos+10,topPos-28, 0xFFFFFF);
            ModeTipTime--;
        }
    }

    public void renderBg(){
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0,I_WIDGETS_LOCATION);
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();
        this.blit(matrixStack, 0, (int) ((this.height - 22) /scale), 0 , 0 , 81, 22);
    }

    public void renderNum(){
        int y = (int) (this.height - 22 + 11 * scale - 4 * scale);
        for (int i = 0; i < 4; i++) {
            MutableComponent translatable = Component.translatable(Messages.SUIT_NUM[i]);
            translatable.setStyle(Style.EMPTY.withColor(Messages.SUIT_NUM_COLORS[i]));
            drawCenteredString(matrixStack,font,translatable, (int) ((i * 20 + 12) * scale) , y,16777215 | Mth.ceil( 255.0F) << 24);
        }
    }

    public void renderFocus( int focus){
        this.blit(matrixStack, focus * 20, (int)((this.height - 22) / scale), 1 , 23 , 24, 23);

    }

    public void setMode(int i){
        this.mode = i;
        ModeTipTime = 1500;
    }
    public void focusSuitChange(){
        SuitTipTime = 1500;
    }
    public void initPos(){
        this.width = minecraft.getWindow().getGuiScaledWidth();
        this.height = minecraft.getWindow().getGuiScaledHeight();
        leftPos = this.width / 2 + 18 * 4 + 10;
        topPos = this.height - 6;
    }



}
