package com.AutomaticalEchoes.EquipSuit.client.gui;

import com.AutomaticalEchoes.EquipSuit.client.screen.TextureType;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.Container;
import net.minecraft.world.item.ItemStack;

public class MapSlotButton extends Button {
    public static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation("textures/gui/container/bundle.png");
    private final int index;
    private ItemStack mirrorItem = ItemStack.EMPTY;
    private int number = 0 ;
    public MapSlotButton(int p_93728_, int p_93729_,int index,Component p_93732_, OnPress p_93733_ ,OnTooltip p_93734_) {
        super(p_93728_, p_93729_, 18,18, p_93732_, p_93733_, p_93734_);
        this.index = index;
    }

    public int getIndex() {
        return index;
    }

    public void setMirrorItem(Container container) {
        this.mirrorItem = container.getItem(number);
    }

    public ItemStack getMirrorItem() {
        return mirrorItem;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @Override
    public void renderButton(PoseStack p_93746_, int p_93747_, int p_93748_, float p_93749_) {
        if(!active){
            super.renderButton(p_93746_, p_93747_, p_93748_, p_93749_);
        }else {
            this.blit(p_93746_,this.x,this.y,p_93747_,TextureType.INVENTORY_SLOT);
            Minecraft.getInstance().getItemRenderer().renderAndDecorateItem(mirrorItem,x + 1,y + 1);
            if (this.isHoveredOrFocused()) {
                renderHighlight(p_93746_);
                this.renderToolTip(p_93746_, p_93747_, p_93748_);
            }
        }
    }

    private void blit(PoseStack p_194036_, int p_194037_, int p_194038_, int p_194039_, TextureType p_194040_) {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE_LOCATION);
        GuiComponent.blit(p_194036_, p_194037_, p_194038_, p_194039_, (float)p_194040_.x, (float)p_194040_.y, p_194040_.w, p_194040_.h, 128, 128);
    }

    public void renderHighlight(PoseStack p_169607_) {
        RenderSystem.colorMask(true, true, true, false);
        fillGradient(p_169607_, x + 1, y + 1, x + 17, y + 17, -2130706433, -2130706433, getBlitOffset());
        RenderSystem.colorMask(true, true, true, true);
        drawCenteredString(p_169607_,Minecraft.getInstance().font,String.valueOf(number),x + 9, y + 5 ,getBlitOffset());
    }
}

