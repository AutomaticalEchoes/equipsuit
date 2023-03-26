package com.equipsuit.equip_suit_v1.client.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.Widget;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class FocusSuitHUD extends Screen implements Widget {
    private static final Component FOCUS_SUIT_HUD=Component.translatable("focus suit hud");
    public static FocusSuitHUD Create(PoseStack matrixStack){
        return new FocusSuitHUD(FOCUS_SUIT_HUD,matrixStack);
    }

    protected FocusSuitHUD(Component p_96550_, PoseStack matrixStack) {
        super(p_96550_);
    }
    public void render(){

    }
}
