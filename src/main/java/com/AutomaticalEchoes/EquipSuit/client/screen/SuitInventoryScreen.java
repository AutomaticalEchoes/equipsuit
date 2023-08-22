package com.AutomaticalEchoes.EquipSuit.client.screen;

import com.AutomaticalEchoes.EquipSuit.EquipSuitChange;
import com.AutomaticalEchoes.EquipSuit.api.config.EquipSuitClientConfig;
import com.AutomaticalEchoes.EquipSuit.api.modInterfcae.baseSlot.BaseSlot;
import com.AutomaticalEchoes.EquipSuit.api.modInterfcae.containerType.ContainerTypes;
import com.AutomaticalEchoes.EquipSuit.api.modInterfcae.equipsuit.EquipSuit;
import com.AutomaticalEchoes.EquipSuit.api.utils.EquipSuitTemplate;
import com.AutomaticalEchoes.EquipSuit.api.modInterfcae.player.IPlayerInterface;
import com.AutomaticalEchoes.EquipSuit.api.utils.EquipSuitKeyMapping;
import com.AutomaticalEchoes.EquipSuit.api.utils.Messages;
import com.AutomaticalEchoes.EquipSuit.client.gui.BinarySwitchButton;
import com.AutomaticalEchoes.EquipSuit.client.gui.TradeOfferButton;
import com.AutomaticalEchoes.EquipSuit.common.CommonModEvents;
import com.AutomaticalEchoes.EquipSuit.common.container.SuitInventoryMenu;
import com.AutomaticalEchoes.EquipSuit.common.network.SuitChange;
import com.AutomaticalEchoes.EquipSuit.common.network.SuitSingleChange;
import com.AutomaticalEchoes.EquipSuit.common.network.UpdateSuitName;
import com.AutomaticalEchoes.EquipSuit.common.network.UpdateSuitSlot;
import com.mojang.blaze3d.platform.InputConstants;
import com.mojang.blaze3d.platform.Lighting;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Quaternion;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.ClickType;
import net.minecraft.world.inventory.Slot;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

@OnlyIn(Dist.CLIENT)
public class SuitInventoryScreen extends EffectRenderingInventoryScreen<SuitInventoryMenu> {
    public static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation("textures/gui/container/bundle.png");
    private static final ResourceLocation SUIT_INVENTORY = new ResourceLocation(EquipSuitChange.MODID, "textures/screens/suit_inventory.png");
    private static final ResourceLocation SUIT_GUI = new ResourceLocation(EquipSuitChange.MODID, "textures/gui/suit_gui.png");
    private static final int INVENTORY_SIZE = Inventory.INVENTORY_SIZE+ Inventory.ALL_ARMOR_SLOTS.length + 6;
    private static final List<Component> EDITING_MESSAGE = new ArrayList<>();
    private static final  List<Component> WARNING_MESSAGE = new ArrayList<>();
    private static BinarySwitchButton EDIT_BUTTON;
    private static EditBox SUIT_NAME;
    private static int ChangeIndex = 0;
    private float xMouse;
    private float yMouse;
    private final Button.OnPress IndexPress , SlotUpdatePress;
    private boolean buttonClicked =false;
    private boolean canEdit = false;
    private int lastFocus = 0;
    private String key;
    private final TradeOfferButton[] suitIndexButtons = new TradeOfferButton[4];
    private final TradeOfferButton[] slotIndexButtons = new TradeOfferButton[4];

    public SuitInventoryScreen(SuitInventoryMenu p_97741_, Inventory p_97742_ ,Component p_97743_) {
        super(p_97741_, p_97742_, Component.translatable(""));
        this.passEvents = true;
        this.titleLabelX = 12;
        this.IndexPress = p_93751_ -> {
            if(p_93751_ instanceof TradeOfferButton tradeOfferButton){
                CommonModEvents.NetWork.sendToServer(new SuitChange(tradeOfferButton.index));
            }
        };
        this.SlotUpdatePress = p_93751_ -> {
            if(p_93751_ instanceof TradeOfferButton tradeOfferButton){
                buttonClicked = ! buttonClicked;
                canEdit = buttonClicked;
                ChangeIndex = buttonClicked ? tradeOfferButton.index : -1;
                this.key = EquipSuitTemplate.INDEX_KEY[tradeOfferButton.index];
                if(buttonClicked) {
                    resetEditingMessage();
                }else{
                    CommonModEvents.NetWork.sendToServer(new SuitSingleChange());
                }
            }
        };
    }

    public Boolean mayPlace(){
        return this.canEdit;
    }

    //这玩意就是要空的 不能删， 原版绘制的标题会和装备格重叠影响
    @Override
    protected void renderLabels(PoseStack p_97808_, int p_97809_, int p_97810_) { }
    @Override
    protected void init() {
        super.init();
        int x = this.leftPos - 94;
        int y = this.topPos  - 5;
        initWarningMessage();
        Integer focus = ((IPlayerInterface) Minecraft.getInstance().player).getFocus();
        EquipSuit equipSuit = ((IPlayerInterface) Minecraft.getInstance().player).getSuitStack().getEquipSuitList().get(focus);
        EDIT_BUTTON = new BinarySwitchButton(x + 1 , y - 4 ,14,10) {
            @Override
            public void onSwitchCase(boolean SwitchBinary) {
                canEdit =!canEdit;
                buttonClicked = false;
                CommonModEvents.NetWork.sendToServer(new SuitSingleChange());
            }
        };
        this.addRenderableWidget(EDIT_BUTTON);
        for(int i=0 ;i<4;i++){
            MutableComponent translatable = Component.translatable(Messages.SUIT_NUM[i]);
            translatable.setStyle(Style.EMPTY.withColor(Messages.SUIT_NUM_COLORS[i]));
            TradeOfferButton tradeOfferButton = new TradeOfferButton(x + 1 + i * 12, y - 16, i,translatable, IndexPress, 13, 11) {
                @Override
                public void renderToolTip(PoseStack p_99211_, int p_99212_, int p_99213_) {
                }
            };
            suitIndexButtons[i] = tradeOfferButton;
            this.addRenderableWidget(tradeOfferButton);
        }

        SUIT_NAME = new EditBox(this.minecraft.font,x + 54 ,y - 13 ,56 ,14,Component.translatable("name"));
        SUIT_NAME.setValue(equipSuit.getName());
        SUIT_NAME.setMaxLength(10);
        SUIT_NAME.setCanLoseFocus(true);
        SUIT_NAME.setResponder(s -> {
            Integer focus1 = ((IPlayerInterface) Minecraft.getInstance().player).getFocus();
            CommonModEvents.NetWork.sendToServer(new UpdateSuitName(focus1,s));
        });
        this.addRenderableWidget(SUIT_NAME);

        for (int i=0;i<4;i++){
            TradeOfferButton tradeOfferButton = new TradeOfferButton(x + 1, y + 7 + i * 15, i, Component.translatable(EquipSuitTemplate.PART[i]), SlotUpdatePress, 14, 14) {
                @Override
                public void renderToolTip(PoseStack p_99211_, int p_99212_, int p_99213_) {
                    if (this.isHovered && !buttonClicked) {
                        renderTooltip(p_99211_,Component.translatable(Messages.PART_NAME[index]),(int)xMouse,(int)yMouse);
                    }
                }
            };
            slotIndexButtons[i] = tradeOfferButton;
            this.addRenderableWidget(tradeOfferButton);
        }
    }

    @Override
    public void render(PoseStack p_97795_, int p_97796_, int p_97797_, float p_97798_) {
        this.renderBackground(p_97795_);
        super.render(p_97795_, p_97796_, p_97797_, p_97798_);
        if(buttonClicked){
            renderComponentTooltip(p_97795_,EDITING_MESSAGE, p_97796_,p_97797_);
            renderComponentTooltip(p_97795_,WARNING_MESSAGE,this.leftPos-10 ,this.topPos-40 );
        }else {
            this.renderTooltip(p_97795_, p_97796_,p_97797_);
        }
        this.xMouse = (float)p_97796_;
        this.yMouse = (float)p_97797_;
        drawString(p_97795_, font,Component.translatable(Messages.TAG_MODE +Messages.MODE_NAME[EquipSuitClientConfig.CHANGE_MODE.get()])  ,leftPos,topPos+170, 0xFFFFFF);
    }

    @Override
    protected void containerTick() {
        super.containerTick();
        Integer focus = ((IPlayerInterface) Minecraft.getInstance().player).getFocus();
        EquipSuit equipSuit = ((IPlayerInterface) Minecraft.getInstance().player).getSuitStack().getEquipSuitList().get(focus);
        EDIT_BUTTON.binary = canEdit;
        SUIT_NAME.setEditable(SUIT_NAME.isFocused());
        if(InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), 257) && SUIT_NAME.isFocused()){
           SUIT_NAME.setFocus(false);
           SUIT_NAME.setValue(equipSuit.getName());
        }
        if(lastFocus != focus) SUIT_NAME.setValue(equipSuit.getName());
        Arrays.stream(suitIndexButtons).forEach(tradeOfferButton -> tradeOfferButton.active = tradeOfferButton.index != focus && !buttonClicked && !canEdit);
        Arrays.stream(slotIndexButtons).forEach(tradeOfferButton -> tradeOfferButton.active = canEdit &&(!buttonClicked || tradeOfferButton.index == ChangeIndex));
        this.lastFocus = focus;
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
        ArrayList<EquipSuit> equipSuitList = ((IPlayerInterface) Minecraft.getInstance().player).getSuitStack().getEquipSuitList();
        equipSuitList.forEach(new Consumer<EquipSuit>() {
            @Override
            public void accept(EquipSuit equipSuit) {
                equipSuit.left().forEach(new BiConsumer<String, BaseSlot>() {
                    @Override
                    public void accept(String s, BaseSlot slot) {
                        if(slot.ContainerType() == ContainerTypes.TYPE_SUIT)
                        markSlot(p_97787_,slot.getSlotNum(),equipSuit.num(),s);
                    }
                });
            }
        });
    }

    protected void renderSuitInventory(PoseStack p_97787_, int p_97790_){
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, SUIT_INVENTORY);
        int i = this.leftPos;
        int j = this.topPos - 24;
        this.blit(p_97787_, i-102, j, 0, 0, 100,200,100,200);
        for(int k = 0; k < 36; k++) {
            this.blit(p_97787_,this.leftPos-((4 -( k % 4)) * 18 + 5), (int) (this.topPos - 5 +  ( 7 + Math.ceil(k / 4) * 19)),p_97790_,TextureType.INVENTORY_SLOT);
        }
    }

    public static void renderEntityInInventory(int p_98851_, int p_98852_, int p_98853_, float p_98854_, float p_98855_, LivingEntity p_98856_) {
        float f = (float)Math.atan(p_98854_ / 40.0F);
        float f1 = (float)Math.atan(p_98855_ / 40.0F);
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

    private void markSlot(PoseStack poseStack ,int slotNum , int suit ,String s){
        int num = EquipSuitTemplate.KEY_INDEX.get(s);
        float red = (Messages.SUIT_NUM_COLORS[suit] >> 16 & 255) / 255.0F;
        float green =  (Messages.SUIT_NUM_COLORS[suit] >> 8 & 255) / 255.0F;
        float blue =  (Messages.SUIT_NUM_COLORS[suit] & 255) / 255.0F;
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(red, green, blue, 1.0F);
        RenderSystem.setShaderTexture(0, SUIT_GUI);
        int x = this.leftPos-((4 -( slotNum % 4)) * 18 + 4);
        int y =(int) (this.topPos - 5 +  ( 7 + Math.ceil(slotNum / 4) * 19));
        blit(poseStack, x  + suit * 4 , y , getBlitOffset() ,8,4, 4, 4,16,16);
        blit(poseStack, x  + suit * 4 , y , getBlitOffset() ,num * 4,12, 4, 4,16,16);
        RenderSystem.setShaderColor(1.0F,1.0F,1.0F,1.0F);
    }

    private void blit(PoseStack p_194036_, int p_194037_, int p_194038_, int p_194039_, TextureType p_194040_) {
        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
        RenderSystem.setShaderTexture(0, TEXTURE_LOCATION);
        GuiComponent.blit(p_194036_, p_194037_, p_194038_, p_194039_, (float)p_194040_.x, (float)p_194040_.y, p_194040_.w, p_194040_.h, 128, 128);
    }

    @Override
    public boolean mouseClicked(double p_97748_, double p_97749_, int p_97750_) {
        if(buttonClicked){
            Slot slot = this.IFindSlot(p_97748_, p_97749_);
            if(slot!=null && slot.index > INVENTORY_SIZE - 1 ){
                IPlayerInterface player = (IPlayerInterface) Minecraft.getInstance().player;
                Integer focus = player.getFocus();
                int newSlotNum = slot.index - INVENTORY_SIZE;
                CommonModEvents.NetWork.sendToServer(new UpdateSuitSlot(focus,key,newSlotNum));
                CommonModEvents.NetWork.sendToServer(new SuitSingleChange());
                buttonClicked = ! buttonClicked;
                canEdit = false;
                return true;
            }
            return slotIndexButtons[ChangeIndex].mouseClicked(p_97748_,p_97749_,p_97750_)|| EDIT_BUTTON.mouseClicked(p_97748_,p_97749_,p_97750_);
        }else{
            return super.mouseClicked(p_97748_, p_97749_, p_97750_);
        }
    }

    @Override
    public boolean keyPressed(int p_97765_, int p_97766_, int p_97767_) {
        InputConstants.Key mouseKey = InputConstants.getKey(p_97765_, p_97766_);
        if (p_97765_ == 256 && this.shouldCloseOnEsc()) {
            this.onClose();
            return true;
        } else if (p_97765_ == 258) {
            boolean flag = !hasShiftDown();
            if (!this.changeFocus(flag)) {
                this.changeFocus(flag);
            }
            return false;
        } else if (this.minecraft.options.keyInventory.isActiveAndMatches(mouseKey) || EquipSuitKeyMapping.CALL_SUIT_INVENTORY_KEY.getKey().equals(mouseKey)) {
            if(!SUIT_NAME.isFocused()) this.onClose();
            return true;
        } else {
            boolean handled = this.checkHotbarKeyPressed(p_97765_, p_97766_);// Forge MC-146650: Needs to return true when the key is handled
            if (this.hoveredSlot != null && this.hoveredSlot.hasItem()) {
                if (this.minecraft.options.keyPickItem.isActiveAndMatches(mouseKey)) {
                    this.slotClicked(this.hoveredSlot, this.hoveredSlot.index, 0, ClickType.CLONE);
                    handled = true;
                } else if (this.minecraft.options.keyDrop.isActiveAndMatches(mouseKey)) {
                    this.slotClicked(this.hoveredSlot, this.hoveredSlot.index, hasControlDown() ? 1 : 0, ClickType.THROW);
                    handled = true;
                }
            } else if (this.minecraft.options.keyDrop.isActiveAndMatches(mouseKey)) {
                handled = true; // Forge MC-146650: Emulate MC bug, so we don't drop from hotbar when pressing drop without hovering over a item.
            }
            return handled || (this.getFocused() != null && this.getFocused().keyPressed(p_97765_, p_97766_, p_97767_));
        }
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

    public void resetEditingMessage(){
        EDITING_MESSAGE.clear();
        EDITING_MESSAGE.add(Component.translatable(Messages.TAG_EDITING ));
        EDITING_MESSAGE.add(Component.translatable(Messages.TAG_SUIT + Messages.SUIT_NUM[((IPlayerInterface) Minecraft.getInstance().player).getFocus()]));
        EDITING_MESSAGE.add(Component.translatable(Messages.TAG_PART + Messages.PART_NAME[ChangeIndex]));
    }

    public void initWarningMessage(){
        WARNING_MESSAGE.clear();
        WARNING_MESSAGE.add(Component.translatable(Messages.TAG_WARNING).withStyle(Style.EMPTY.withColor(16184082)));
        WARNING_MESSAGE.add(Component.translatable(Messages.NO_CLICK_RESULT).withStyle(Style.EMPTY.withColor(16184082)));
        WARNING_MESSAGE.add(Component.translatable(Messages.NO_CLICK_RESULT_1).withStyle(Style.EMPTY.withColor(16184082)));
    }

    @Override
    public void onClose() {
        super.onClose();
        if(this.canEdit){
            CommonModEvents.NetWork.sendToServer(new SuitSingleChange());
            this.canEdit =!canEdit;
        }
    }
}
