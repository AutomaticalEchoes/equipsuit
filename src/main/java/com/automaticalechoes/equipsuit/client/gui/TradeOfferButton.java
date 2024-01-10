package com.automaticalechoes.equipsuit.client.gui;

import com.automaticalechoes.equipsuit.api.modInterfcae.gui.OnPress;
import com.automaticalechoes.equipsuit.api.modInterfcae.player.IPlayerInterface;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.ArrayList;
import java.util.List;

@SideOnly(Side.CLIENT)
public abstract class TradeOfferButton extends GuiButton {
    public final int index;
//    public final List<ITextComponent> renderTooltip = new ArrayList<>();
    private final OnPress<TradeOfferButton> onPress;

    public TradeOfferButton(int id, int p_99205_, int p_99206_, int p_99207_,  int width, int height, ITextComponent textComponents , OnPress<TradeOfferButton> onPress) {
        super(id, p_99205_, p_99206_, width, height, textComponents.getFormattedText());
        this.index = p_99207_;
        this.visible = true;
        this.onPress = onPress;
//        renderTooltip.add(textComponents);
    }
//    public TradeOfferButton(int p_99205_, int p_99206_, int p_99207_, Component component, Button.OnPress p_99208_, int width, int height) {
//        super(p_99205_, p_99206_, width, height, component, p_99208_);
//        this.index = p_99207_;
//        this.visible = true;
//        renderTooltip.add(component);
//    }




    public void tick(EntityPlayer entityPlayer) {
        this.enabled = enabled &&  ((IPlayerInterface) entityPlayer).getFocus() != this.index;
    }

    public int getIndex() {
        return this.index;
    }


    public abstract void renderToolTip( int p_99212_, int p_99213_) ;

    @Override
    public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
        return super.mousePressed(mc, mouseX, mouseY);
    }

    @Override
    public void mouseReleased(int p_mouseReleased_1_, int p_mouseReleased_2_) {
        super.mouseReleased(p_mouseReleased_1_, p_mouseReleased_2_);
        onPress.action(this);
    }

    @Override
    public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
        if (this.visible)
        {
            FontRenderer fontrenderer = mc.fontRenderer;
            mc.getTextureManager().bindTexture(BUTTON_TEXTURES);
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            this.hovered = mouseX >= this.x && mouseY >= this.y && mouseX < this.x + this.width && mouseY < this.y + this.height;
            int i = this.getHoverState(this.hovered);
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
            GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
            this.drawTexturedModalRect(this.x, this.y + this.height / 2, 0, 46 + (i+1) * 20 - this.height / 2, this.width / 2, this.height / 2);
            this.drawTexturedModalRect(this.x + this.width / 2, this.y + this.height / 2, 200 - this.width / 2, 46 +  (i+1) * 20 - this.height / 2, this.width / 2, this.height / 2);
            this.drawTexturedModalRect(this.x, this.y, 0, 46 + i * 20, this.width / 2, this.height / 2);
            this.drawTexturedModalRect(this.x + this.width / 2, this.y, 200 - this.width / 2, 46 + i * 20, this.width / 2, this.height / 2);

            this.mouseDragged(mc, mouseX, mouseY);
            int j = 14737632;

            if (packedFGColour != 0)
            {
                j = packedFGColour;
            }
            else
            if (!this.enabled)
            {
                j = 10526880;
            }
            else if (this.hovered)
            {
                j = 16777120;
            }

            this.drawCenteredString(fontrenderer, this.displayString, this.x + this.width / 2, this.y + (this.height - 8) / 2, j);
        }
    }






    //
//    @Override
//    public void renderButton(PoseStack p_93676_, int p_93677_, int p_93678_, float p_93679_) {
//        Minecraft minecraft = Minecraft.getInstance();
//        Font font = minecraft.font;
//        RenderSystem.setShader(GameRenderer::getPositionTexShader);
//        RenderSystem.setShaderTexture(0, WIDGETS_LOCATION);
//        RenderSystem.setShaderColor(1.0F,1.0F,1.0F, this.alpha);
//        int i = this.getYImage(this.isHoveredOrFocused());
//        RenderSystem.enableBlend();
//        RenderSystem.defaultBlendFunc();
//        RenderSystem.enableDepthTest();
//        this.blit(p_93676_, this.x, this.y + this.height / 2, 0, 46 + (i+1) * 20 - this.height / 2, this.width / 2, this.height / 2);
//        this.blit(p_93676_, this.x + this.width / 2, this.y + this.height / 2, 200 - this.width / 2, 46 +  (i+1) * 20 - this.height / 2, this.width / 2, this.height / 2);
//        this.blit(p_93676_, this.x, this.y, 0, 46 + i * 20, this.width / 2, this.height / 2);
//        this.blit(p_93676_, this.x + this.width / 2, this.y, 200 - this.width / 2, 46 + i * 20, this.width / 2, this.height / 2);
//        this.renderBg(p_93676_, minecraft, p_93677_, p_93678_);
//        int j = getFGColor();
//        drawCenteredString(p_93676_, font, this.getMessage(), this.x + this.width / 2, this.y + (this.height - 8) / 2, j | Mth.ceil(this.alpha * 255.0F) << 24);
//
//        if (this.isHoveredOrFocused()) {
//            this.renderToolTip(p_93676_,p_93677_,p_93678_);
//        }
//    }
}
