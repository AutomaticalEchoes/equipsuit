package com.equipsuit.equip_suit_v1.client.gui;

import com.equipsuit.equip_suit_v1.EquipSuitChange;
import com.equipsuit.equip_suit_v1.api.modInterfcae.gui.FocusSuitHud;
import com.equipsuit.equip_suit_v1.api.utils.Messages;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.realms.RealmsLabel;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FocusSuitHUD extends Screen implements Widget, FocusSuitHud {
    private static final Component FOCUS_SUIT_HUD=Component.translatable("focus suit hud");
    private static int leftPos,topPos;
    private final PoseStack matrixStack;
    private static int time = 0;
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

    }

    public void render(String s){
        initPos();
        MutableComponent translatable = Component.translatable(s);
        this.renderTooltip(matrixStack,translatable,leftPos,topPos);
        if(time > 0){
            drawString(matrixStack, font,Component.translatable(Messages.TAG_MODE +Messages.MODE_NAME[mode])  ,leftPos+10,topPos-28, 0xFFFFFF);
            time--;
        }

    }
    public void setMode(int i){
        this.mode = i;
        time = 1500;
    }
    public void initPos(){
        this.width = minecraft.getWindow().getGuiScaledWidth();
        this.height = minecraft.getWindow().getGuiScaledHeight();
        leftPos = this.width / 2 + 18 * 4 + 10;
        topPos = this.height - 6;
    }



}
