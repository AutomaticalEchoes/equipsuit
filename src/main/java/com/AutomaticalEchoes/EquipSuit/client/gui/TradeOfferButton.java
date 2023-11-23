package com.AutomaticalEchoes.EquipSuit.client.gui;

import com.AutomaticalEchoes.EquipSuit.api.modInterfcae.player.IPlayerInterface;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.List;

@OnlyIn(Dist.CLIENT)
public abstract class TradeOfferButton extends Button {
    public final int index;
    public final List<Component> renderTooltip =new ArrayList<>();
    public TradeOfferButton(int p_99205_, int p_99206_, int p_99207_, Component component, OnPress p_99208_, int width, int height) {
        super(p_99205_, p_99206_, width, height, component, p_99208_);
        this.index = p_99207_;
        this.visible = true;
        renderTooltip.add(component);
    }


    @Override
    public boolean isActive() {
        return super.isActive() &&  ((IPlayerInterface) Minecraft.getInstance().player).getFocus() != this.index;
    }

    public int getIndex() {
        return this.index;
    }


    public abstract void renderToolTip(PoseStack p_99211_, int p_99212_, int p_99213_) ;


    @Override
    public void renderButton(PoseStack p_93676_, int p_93677_, int p_93678_, float p_93679_) {
        Minecraft minecraft = Minecraft.getInstance();
        Font font = minecraft.font;
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderTexture(0, WIDGETS_LOCATION);
        RenderSystem.setShaderColor(1.0F,1.0F,1.0F, this.alpha);
        int i = this.getYImage(this.isHovered() || this.isFocused());
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.enableDepthTest();
        this.blit(p_93676_, this.x, this.y + this.height / 2, 0, 46 + (i+1) * 20 - this.height / 2, this.width / 2, this.height / 2);
        this.blit(p_93676_, this.x + this.width / 2, this.y + this.height / 2, 200 - this.width / 2, 46 +  (i+1) * 20 - this.height / 2, this.width / 2, this.height / 2);
        this.blit(p_93676_, this.x, this.y, 0, 46 + i * 20, this.width / 2, this.height / 2);
        this.blit(p_93676_, this.x + this.width / 2, this.y, 200 - this.width / 2, 46 + i * 20, this.width / 2, this.height / 2);
        this.renderBg(p_93676_, minecraft, p_93677_, p_93678_);
        int j = getFGColor();
        drawCenteredString(p_93676_, font, this.getMessage(), this.x + this.width / 2, this.y + (this.height - 8) / 2, j | Mth.ceil(this.alpha * 255.0F) << 24);

        if (this.isHovered() || this.isFocused()) {
            this.renderToolTip(p_93676_,p_93677_,p_93678_);
        }
    }
}
