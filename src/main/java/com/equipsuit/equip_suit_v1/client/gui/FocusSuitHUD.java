package com.equipsuit.equip_suit_v1.client.gui;

import com.equipsuit.equip_suit_v1.api.modInterfcae.gui.FocusSuitHud;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FocusSuitHUD extends Screen implements Widget, FocusSuitHud {
    private static final Component FOCUS_SUIT_HUD=Component.translatable("focus suit hud");
    private static final int imageWidth = 18;
    private static final int imageHeight = 14;

    private static int leftPos,topPos;
    private PoseStack matrixStack;
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

    }
    public void render(String s){
        initPos();
        Component component = Component.translatable(s);
        this.renderTooltip(matrixStack,component,leftPos,topPos);
    }
    public void initPos(){
        this.width = minecraft.getWindow().getGuiScaledWidth();
        this.height = minecraft.getWindow().getGuiScaledHeight();
        leftPos = this.width / 2 + 18 * 4 + 10;
        topPos = this.height - 5;
    }



}
