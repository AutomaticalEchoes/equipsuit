package com.AutomaticalEchoes.EquipSuit.client.screen;

import com.AutomaticalEchoes.EquipSuit.EquipSuitChange;
import com.AutomaticalEchoes.EquipSuit.api.config.EquipSuitClientConfig;
import com.AutomaticalEchoes.EquipSuit.api.modInterfcae.baseSlot.BaseSlot;
import com.AutomaticalEchoes.EquipSuit.api.modInterfcae.containerType.ContainerTypes;
import com.AutomaticalEchoes.EquipSuit.api.modInterfcae.equipsuit.EquipSuit;
import com.AutomaticalEchoes.EquipSuit.api.modInterfcae.equipsuit.EquipSuitTemplate;
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
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.inventory.EffectRenderingInventoryScreen;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.Slot;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.Matrix4f;
import org.joml.Quaternionf;

import javax.annotation.Nullable;
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
    protected void renderLabels(GuiGraphics p_281635_, int p_282681_, int p_283686_) {
    }

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
                public void renderToolTip(GuiGraphics p_99211_, int p_99212_, int p_99213_) {
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
            TradeOfferButton tradeOfferButton = new TradeOfferButton(x + 1, y + 7 + i * 15, i, Component.translatable(Messages.PART[i]), SlotUpdatePress, 14, 14) {
                @Override
                public void renderToolTip(GuiGraphics p_99211_, int p_99212_, int p_99213_) {
                    if (this.isHovered && !buttonClicked) {
                        p_99211_.renderTooltip(font,Component.translatable(Messages.PART_NAME[index]),(int)xMouse,(int)yMouse);
                    }
                }
            };
            slotIndexButtons[i] = tradeOfferButton;
            this.addRenderableWidget(tradeOfferButton);
        }
    }

    @Override
    protected void renderBg(GuiGraphics p_97787_, float p_97788_, int p_97789_, int p_97790_) {
        int i = this.leftPos;
        int j = this.topPos;
        p_97787_.blit(INVENTORY_LOCATION,i, j, 0, 0, this.imageWidth, this.imageHeight);
        renderEntityInInventoryFollowsMouse(p_97787_,i + 51, j + 75, 30, (float)(i + 51) - this.xMouse, (float)(j + 75 - 50) - this.yMouse, this.minecraft.player);
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

    @Override
    public void render(GuiGraphics p_97795_, int p_97796_, int p_97797_, float p_97798_) {
        this.renderBackground(p_97795_);
        super.render(p_97795_, p_97796_, p_97797_, p_97798_);
        if(buttonClicked){
            p_97795_.renderComponentTooltip(this.font,EDITING_MESSAGE, p_97796_,p_97797_);
            p_97795_.renderComponentTooltip(this.font,WARNING_MESSAGE,this.leftPos-10 ,this.topPos-40 );
        }else {
            this.renderTooltip(p_97795_, p_97796_,p_97797_);
        }
        this.xMouse = (float)p_97796_;
        this.yMouse = (float)p_97797_;
        p_97795_.drawString(font,Component.translatable(Messages.TAG_MODE +Messages.MODE_NAME[EquipSuitClientConfig.CHANGE_MODE.get()])  ,leftPos,topPos+170, 0xFFFFFF);
    }

    @Override
    protected void containerTick() {
        super.containerTick();
        Integer focus = ((IPlayerInterface) Minecraft.getInstance().player).getFocus();
        EquipSuit equipSuit = ((IPlayerInterface) Minecraft.getInstance().player).getSuitStack().getEquipSuitList().get(focus);
        EDIT_BUTTON.binary = canEdit;
        SUIT_NAME.setEditable(SUIT_NAME.isFocused());
        if(InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), 257) && SUIT_NAME.isFocused()){
           SUIT_NAME.setFocused(false);
           SUIT_NAME.setValue(equipSuit.getName());
        }
        if(lastFocus != focus) SUIT_NAME.setValue(equipSuit.getName());
        Arrays.stream(suitIndexButtons).forEach(tradeOfferButton -> tradeOfferButton.active = tradeOfferButton.index != focus && !buttonClicked && !canEdit);
        Arrays.stream(slotIndexButtons).forEach(tradeOfferButton -> tradeOfferButton.active = canEdit &&(!buttonClicked || tradeOfferButton.index == ChangeIndex));
        this.lastFocus = focus;
    }

    protected void renderSuitInventory(GuiGraphics p_97787_, int p_97790_){
        int i = this.leftPos;
        int j = this.topPos - 24;
        p_97787_.blit(SUIT_INVENTORY, i-102, j, 0, 0, 100,200,100,200);
        for(int k = 0; k < 36; k++) {
            this.blit(p_97787_,this.leftPos-((4 -( k % 4)) * 18 + 5), (int) (this.topPos - 5 +  ( 7 + Math.ceil(k / 4) * 19)),TextureType.INVENTORY_SLOT);
        }
    }

    public static void renderEntityInInventoryFollowsMouse(GuiGraphics p_282802_, int p_275688_, int p_275245_, int p_275535_, float p_275604_, float p_275546_, LivingEntity p_275689_) {
        float f = (float)Math.atan((double)(p_275604_ / 40.0F));
        float f1 = (float)Math.atan((double)(p_275546_ / 40.0F));
        // Forge: Allow passing in direct angle components instead of mouse position
        renderEntityInInventoryFollowsAngle(p_282802_, p_275688_, p_275245_, p_275535_, f, f1, p_275689_);
    }

    public static void renderEntityInInventoryFollowsAngle(GuiGraphics p_282802_, int p_275688_, int p_275245_, int p_275535_, float angleXComponent, float angleYComponent, LivingEntity p_275689_) {
        float f = angleXComponent;
        float f1 = angleYComponent;
        Quaternionf quaternionf = (new Quaternionf()).rotateZ((float)Math.PI);
        Quaternionf quaternionf1 = (new Quaternionf()).rotateX(f1 * 20.0F * ((float)Math.PI / 180F));
        quaternionf.mul(quaternionf1);
        float f2 = p_275689_.yBodyRot;
        float f3 = p_275689_.getYRot();
        float f4 = p_275689_.getXRot();
        float f5 = p_275689_.yHeadRotO;
        float f6 = p_275689_.yHeadRot;
        p_275689_.yBodyRot = 180.0F + f * 20.0F;
        p_275689_.setYRot(180.0F + f * 40.0F);
        p_275689_.setXRot(-f1 * 20.0F);
        p_275689_.yHeadRot = p_275689_.getYRot();
        p_275689_.yHeadRotO = p_275689_.getYRot();
        renderEntityInInventory(p_282802_, p_275688_, p_275245_, p_275535_, quaternionf, quaternionf1, p_275689_);
        p_275689_.yBodyRot = f2;
        p_275689_.setYRot(f3);
        p_275689_.setXRot(f4);
        p_275689_.yHeadRotO = f5;
        p_275689_.yHeadRot = f6;
    }

    public static void renderEntityInInventory(GuiGraphics p_282665_, int p_283622_, int p_283401_, int p_281360_, Quaternionf p_281880_, @Nullable Quaternionf p_282882_, LivingEntity p_282466_) {
        p_282665_.pose().pushPose();
        p_282665_.pose().translate((double)p_283622_, (double)p_283401_, 50.0D);
        p_282665_.pose().mulPoseMatrix((new Matrix4f()).scaling((float)p_281360_, (float)p_281360_, (float)(-p_281360_)));
        p_282665_.pose().mulPose(p_281880_);
        Lighting.setupForEntityInInventory();
        EntityRenderDispatcher entityrenderdispatcher = Minecraft.getInstance().getEntityRenderDispatcher();
        if (p_282882_ != null) {
            p_282882_.conjugate();
            entityrenderdispatcher.overrideCameraOrientation(p_282882_);
        }

        entityrenderdispatcher.setRenderShadow(false);
        RenderSystem.runAsFancy(() -> {
            entityrenderdispatcher.render(p_282466_, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, p_282665_.pose(), p_282665_.bufferSource(), 15728880);
        });
        p_282665_.flush();
        entityrenderdispatcher.setRenderShadow(true);
        p_282665_.pose().popPose();
        Lighting.setupFor3DItems();
    }

    private void markSlot(GuiGraphics guiGraphics ,int slotNum , int suit ,String s){
        int num = EquipSuitTemplate.KEY_INDEX.get(s);
        float red = (Messages.SUIT_NUM_COLORS[suit] >> 16 & 255) / 255.0F;
        float green =  (Messages.SUIT_NUM_COLORS[suit] >> 8 & 255) / 255.0F;
        float blue =  (Messages.SUIT_NUM_COLORS[suit] & 255) / 255.0F;
        RenderSystem.setShader(GameRenderer::getPositionTexShader);
        RenderSystem.setShaderColor(red, green, blue, 1.0F);
        RenderSystem.setShaderTexture(0, SUIT_GUI);
        int x = this.leftPos-((4 -( slotNum % 4)) * 18 + 4);
        int y =(int) (this.topPos - 5 +  ( 7 + Math.ceil(slotNum / 4) * 19));
        guiGraphics.blit(SUIT_GUI,x  + suit * 4 , y  ,8,4, 4, 4,16,16);
        guiGraphics.blit(SUIT_GUI, x  + suit * 4 , y  ,num * 4,12, 4, 4,16,16);
        RenderSystem.setShaderColor(1.0F,1.0F,1.0F,1.0F);
    }

    private void blit(GuiGraphics p_281273_, int p_282428_, int p_281897_, TextureType p_281917_) {
        p_281273_.blit(TEXTURE_LOCATION, p_282428_, p_281897_, 0, (float)p_281917_.x, (float)p_281917_.y, p_281917_.w, p_281917_.h, 128, 128);
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
        if(!SUIT_NAME.isFocused()){
            super.onClose();
            if(this.canEdit){
                CommonModEvents.NetWork.sendToServer(new SuitSingleChange());
                this.canEdit =!canEdit;
            }
        }
    }

    @Override
    public boolean keyPressed(int p_97765_, int p_97766_, int p_97767_) {
        InputConstants.Key mouseKey = InputConstants.getKey(p_97765_, p_97766_);
        if(EquipSuitKeyMapping.CALL_SUIT_INVENTORY_KEY.getKey().equals(mouseKey)){
            this.onClose();
            return true;
        }
        return super.keyPressed(p_97765_, p_97766_, p_97767_);
    }
}
