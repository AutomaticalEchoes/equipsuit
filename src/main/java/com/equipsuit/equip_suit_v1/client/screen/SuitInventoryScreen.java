package com.equipsuit.equip_suit_v1.client.screen;

import com.equipsuit.equip_suit_v1.EquipSuitChange;
import com.equipsuit.equip_suit_v1.api.modInterfcae.equipsuit.ContainerEquipSuit;
import com.equipsuit.equip_suit_v1.api.modInterfcae.player.IPlayerInterface;
import com.equipsuit.equip_suit_v1.common.CommonEvents;
import com.equipsuit.equip_suit_v1.common.CommonModEvents;
import com.equipsuit.equip_suit_v1.common.container.SuitInventoryMenu;
import com.equipsuit.equip_suit_v1.common.network.SuitChange;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.AbstractButton;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ImageButton;
import net.minecraft.client.gui.components.LockIconButton;
import net.minecraft.client.gui.screens.inventory.AbstractContainerScreen;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.Arrays;

@OnlyIn(Dist.CLIENT)
public class SuitInventoryScreen extends EffectRenderingInventoryScreen<SuitInventoryMenu> {
    public static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation("textures/gui/container/bundle.png");
    private static final ResourceLocation SUIT_INVENTORY = new ResourceLocation(EquipSuitChange.MODID, "textures/screens/suit_inventory.png");
    private static final ResourceLocation SLOT_MARK = new ResourceLocation(EquipSuitChange.MODID, "textures/screens/mark.png");
    private float xMouse;
    private float yMouse;
    private final Button.OnPress IPress;
    private boolean buttonClicked;
    private final TradeOfferButton[] tradeOfferButtons = new TradeOfferButton[4];
    public SuitInventoryScreen(SuitInventoryMenu p_97741_, Inventory p_97742_ ,Component p_97743_) {
        super(p_97741_, p_97742_, Component.translatable(""));
        this.passEvents = true;
        this.titleLabelX = 97;
        this.IPress = p_93751_ -> {
            if(p_93751_ instanceof TradeOfferButton tradeOfferButton){
                CommonModEvents.NetWork.sendToServer(new SuitChange(tradeOfferButton.index));
            }
        };

    }

    @Override
    protected void init() {
        super.init();
        for(int i=0 ;i<4;i++){
            TradeOfferButton tradeOfferButton = new TradeOfferButton(this.leftPos-100, this.topPos + 10 + i * 20, i,IPress);
            tradeOfferButtons[i] = tradeOfferButton;
            this.addRenderableWidget(tradeOfferButton);
        }
    }

    @Override
    public void render(PoseStack p_97795_, int p_97796_, int p_97797_, float p_97798_) {
        this.renderBackground(p_97795_);
        super.render(p_97795_, p_97796_, p_97797_, p_97798_);
        this.renderTooltip(p_97795_, p_97796_,p_97797_);
        this.xMouse = (float)p_97796_;
        this.yMouse = (float)p_97797_;
    }

    @Override
    protected void containerTick() {
        super.containerTick();
        Arrays.stream(tradeOfferButtons).forEach(tradeOfferButton -> {
            tradeOfferButton.active = tradeOfferButton.index !=  ((IPlayerInterface) Minecraft.getInstance().player).getFocus();
        });
    }

    @Override
    protected void renderBg(PoseStack p_97787_, float p_97788_, int p_97789_, int p_97790_) {
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, INVENTORY_LOCATION);
        int i = this.leftPos;
        int j = this.topPos;
        this.blit(p_97787_, i, j, 0, 0, this.imageWidth, this.imageHeight);
        renderEntityInInventory(i + 51, j + 75, 30, (float)(i + 51) - this.xMouse, (float)(j + 75 - 50) - this.yMouse, this.minecraft.player);
        renderSuitInventory(p_97787_,p_97790_);
        ArrayList<int[]> suitList = ((IPlayerInterface) Minecraft.getInstance().player).getSuitList();
        suitList.forEach((ints) -> Arrays.stream(ints).forEach(num -> markSlot(  p_97787_ ,num , suitList.indexOf(ints))));

    }
    protected void renderSuitInventory(PoseStack p_97787_, int p_97790_){
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, SUIT_INVENTORY);
        int i = this.leftPos;
        int j = this.topPos;
        this.blit(p_97787_, i-82, j, 3, 2, 80,180);
        for(int k = 0; k < 36; k++) {
            this.blit(p_97787_,this.leftPos-((4 -( k % 4)) * 18 + 5), (int) (this.topPos +  ( 7 + Math.ceil(k / 4) * 19)),p_97790_,  Texture.INVENTORY_SLOT);
        }
    }

    public static void renderEntityInInventory(int p_98851_, int p_98852_, int p_98853_, float p_98854_, float p_98855_, LivingEntity p_98856_) {
        float f = (float)Math.atan((double)(p_98854_ / 40.0F));
        float f1 = (float)Math.atan((double)(p_98855_ / 40.0F));
        PoseStack posestack = RenderSystem.getModelViewStack();
        posestack.pushPose();
        posestack.translate((double)p_98851_, (double)p_98852_, 1050.0D);
        posestack.scale(1.0F, 1.0F, -1.0F);
        RenderSystem.applyModelViewMatrix();
        PoseStack posestack1 = new PoseStack();
        posestack1.translate(0.0D, 0.0D, 1000.0D);
        posestack1.scale((float)p_98853_, (float)p_98853_, (float)p_98853_);
        Quaternion quaternion = Vector3f.ZP.rotationDegrees(180.0F);
        Quaternion quaternion1 = Vector3f.XP.rotationDegrees(f1 * 20.0F);
        quaternion.mul(quaternion1);
        posestack1.mulPose(quaternion);
        float f2 = p_98856_.yBodyRot;
        float f3 = p_98856_.getYRot();
        float f4 = p_98856_.getXRot();
        float f5 = p_98856_.yHeadRotO;
        float f6 = p_98856_.yHeadRot;
        p_98856_.yBodyRot = 180.0F + f * 20.0F;
        p_98856_.setYRot(180.0F + f * 40.0F);
        p_98856_.setXRot(-f1 * 20.0F);
        p_98856_.yHeadRot = p_98856_.getYRot();
        p_98856_.yHeadRotO = p_98856_.getYRot();
        Lighting.setupForEntityInInventory();
        EntityRenderDispatcher entityrenderdispatcher = Minecraft.getInstance().getEntityRenderDispatcher();
        quaternion1.conj();
        entityrenderdispatcher.overrideCameraOrientation(quaternion1);
        entityrenderdispatcher.setRenderShadow(false);
        MultiBufferSource.BufferSource multibuffersource$buffersource = Minecraft.getInstance().renderBuffers().bufferSource();
        RenderSystem.runAsFancy(() -> {
            entityrenderdispatcher.render(p_98856_, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, posestack1, multibuffersource$buffersource, 15728880);
        });
        multibuffersource$buffersource.endBatch();
        entityrenderdispatcher.setRenderShadow(true);
        p_98856_.yBodyRot = f2;
        p_98856_.setYRot(f3);
        p_98856_.setXRot(f4);
        p_98856_.yHeadRotO = f5;
        p_98856_.yHeadRot = f6;
        posestack.popPose();
        RenderSystem.applyModelViewMatrix();
        Lighting.setupFor3DItems();
    }

    private void markSlot(PoseStack poseStack ,int num , int suit ){
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, SLOT_MARK);
        int x = this.leftPos-((4 -( num % 4)) * 18 + 4);
        int y =(int) (this.topPos +  ( 7 + Math.ceil(num / 4) * 19));
        this.blit(poseStack, x , y , 0,0, 24, 24);
    }


    private void blit(PoseStack p_194036_, int p_194037_, int p_194038_, int p_194039_, Texture p_194040_) {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE_LOCATION);
        GuiComponent.blit(p_194036_, p_194037_, p_194038_, p_194039_, (float)p_194040_.x, (float)p_194040_.y, p_194040_.w, p_194040_.h, 128, 128);
    }

    @Override
    public boolean mouseClicked(double p_97748_, double p_97749_, int p_97750_) {
        Slot slot = this.IFindSlot(p_97748_, p_97749_);
        if(slot!=null){
            Minecraft.getInstance().player.sendSystemMessage(Component.translatable(String.valueOf(slot.index)));
        }
        return super.mouseClicked(p_97748_, p_97749_, p_97750_);
    }
    private Slot IFindSlot(double p_97748_, double p_97749_){
        for(int i = 0; i < this.menu.slots.size(); ++i) {
            Slot slot = this.menu.slots.get(i);
            if (this.isHovering(slot.x,slot.y,16,16,p_97748_, p_97749_) && slot.isActive()) {
                return slot;
            }
        }
        return null;
    }
    @OnlyIn(Dist.CLIENT)
    class TradeOfferButton extends Button {
        final int index;

        public TradeOfferButton(int p_99205_, int p_99206_, int p_99207_, Button.OnPress p_99208_) {
            super(p_99205_, p_99206_, 20, 20, Component.translatable(String.valueOf(p_99207_+1)), p_99208_);
            this.index = p_99207_;
            this.visible = true;
        }

        @Override
        public boolean isActive() {
            return super.isActive() &&  ((IPlayerInterface) Minecraft.getInstance().player).getFocus() != this.index;
        }

        public int getIndex() {
            return this.index;
        }


        public void renderToolTip(PoseStack p_99211_, int p_99212_, int p_99213_) {
//            if (this.isHovered && MerchantScreen.this.menu.getOffers().size() > this.index + this.scrollOff) {
//                if (p_99212_ < this.x + 20) {
//                    ItemStack itemstack = MerchantScreen.this.menu.getOffers().get(this.index + MerchantScreen.this.scrollOff).getCostA();
//                    MerchantScreen.this.renderTooltip(p_99211_, itemstack, p_99212_, p_99213_);
//                } else if (p_99212_ < this.x + 50 && p_99212_ > this.x + 30) {
//                    ItemStack itemstack2 = MerchantScreen.this.menu.getOffers().get(this.index + MerchantScreen.this.scrollOff).getCostB();
//                    if (!itemstack2.isEmpty()) {
//                        MerchantScreen.this.renderTooltip(p_99211_, itemstack2, p_99212_, p_99213_);
//                    }
//                } else if (p_99212_ > this.x + 65) {
//                    ItemStack itemstack1 = MerchantScreen.this.menu.getOffers().get(this.index + MerchantScreen.this.scrollOff).getResult();
//                    MerchantScreen.this.renderTooltip(p_99211_, itemstack1, p_99212_, p_99213_);
//                }
//            }

        }
    }

    @OnlyIn(Dist.CLIENT)
    static enum Texture {
        SLOT(0, 0, 18, 20),
        BLOCKED_SLOT(0, 40, 18, 20),
        INVENTORY_SLOT(0,0,18,18),
        BORDER_VERTICAL(0, 18, 1, 20),
        BORDER_HORIZONTAL_TOP(0, 20, 18, 1),
        BORDER_HORIZONTAL_BOTTOM(0, 60, 18, 1),
        BORDER_CORNER_TOP(0, 20, 1, 1),
        BORDER_CORNER_BOTTOM(0, 60, 1, 1);

        public final int x;
        public final int y;
        public final int w;
        public final int h;

        private Texture(int p_169928_, int p_169929_, int p_169930_, int p_169931_) {
            this.x = p_169928_;
            this.y = p_169929_;
            this.w = p_169930_;
            this.h = p_169931_;
        }
    }
}
