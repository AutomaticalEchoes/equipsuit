package com.automaticalechoes.equipsuit.client.screen;

import com.automaticalechoes.equipsuit.EquipSuitChange;
import com.automaticalechoes.equipsuit.api.config.EquipSuitClientConfig;
import com.automaticalechoes.equipsuit.api.modInterfcae.baseSlot.BaseSlot;
import com.automaticalechoes.equipsuit.api.modInterfcae.containerType.ContainerTypes;
import com.automaticalechoes.equipsuit.api.modInterfcae.equipsuit.EquipSuit;
import com.automaticalechoes.equipsuit.api.modInterfcae.gui.OnPress;
import com.automaticalechoes.equipsuit.api.modInterfcae.player.IPlayerInterface;
import com.automaticalechoes.equipsuit.api.utils.EquipSuitKeyMapping;
import com.automaticalechoes.equipsuit.api.utils.EquipSuitTemplate;
import com.automaticalechoes.equipsuit.api.utils.Messages;
import com.automaticalechoes.equipsuit.client.gui.BinarySwitchButton;
import com.automaticalechoes.equipsuit.client.gui.TradeOfferButton;
import com.automaticalechoes.equipsuit.common.CommonModEvents;
import com.automaticalechoes.equipsuit.common.container.SuitInventoryMenu;
import com.automaticalechoes.equipsuit.common.network.SuitChange;
import com.automaticalechoes.equipsuit.common.network.SuitSingleChange;
import com.automaticalechoes.equipsuit.common.network.UpdateSuitName;
import com.automaticalechoes.equipsuit.common.network.UpdateSuitSlot;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.gui.GuiPageButtonList;
import net.minecraft.client.gui.GuiTextField;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.InventoryEffectRenderer;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ClickType;
import net.minecraft.inventory.Slot;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.BiConsumer;
import java.util.function.Consumer;

@SideOnly(Side.CLIENT)
public class SuitInventoryScreen extends InventoryEffectRenderer {
    public static final ResourceLocation TEXTURE_LOCATION = new ResourceLocation(EquipSuitChange.MODID, "textures/gui/bundle.png");
    private static final ResourceLocation SUIT_INVENTORY = new ResourceLocation(EquipSuitChange.MODID, "textures/screens/suit_inventory.png");
    private static final ResourceLocation SUIT_GUI = new ResourceLocation(EquipSuitChange.MODID, "textures/gui/suit_gui.png");
    private static final int INVENTORY_SIZE = 46;
    private static final List<String> EDITING_MESSAGE = new ArrayList<>();
    private static final List<String> WARNING_MESSAGE = new ArrayList<>();
    private static BinarySwitchButton EDIT_BUTTON;
    private static GuiTextField SUIT_NAME;
    private static int ChangeIndex = 0;
    private float xMouse;
    private float yMouse;
    private final OnPress<TradeOfferButton> IndexPress , SlotUpdatePress;
    private boolean buttonClicked =false;
    private boolean canEdit = false;
    private int lastFocus = 0;
    private static int buttonIds = 0;
    private String key;
    private final TradeOfferButton[] suitIndexButtons = new TradeOfferButton[4];
    private final TradeOfferButton[] slotIndexButtons = new TradeOfferButton[4];

    public SuitInventoryScreen(SuitInventoryMenu menu) {
        super(menu);
        this.allowUserInput = true;
        this.IndexPress = tradeOfferButton -> {
            CommonModEvents.NetWork.sendToServer(new SuitChange(tradeOfferButton.index));
        };
        this.SlotUpdatePress = tradeOfferButton -> {
            buttonClicked = ! buttonClicked;
            canEdit = buttonClicked;
            ChangeIndex = buttonClicked ? tradeOfferButton.index : -1;
            this.key = EquipSuitTemplate.INDEX_KEY[tradeOfferButton.index];
            if(buttonClicked) {
                resetEditingMessage();
            }else{
                CommonModEvents.NetWork.sendToServer(new SuitSingleChange());
            }
        };
    }

    public Boolean mayPlace(){
        return this.canEdit;
    }

    //这玩意就是要空的 不能删， 原版绘制的标题会和装备格重叠影响
//    @Override
//    protected void renderLabels(PoseStack p_97808_, int p_97809_, int p_97810_) { }

    @Override
    public void initGui() {
        super.initGui();
        int x = this.guiLeft - 94;
        int y = this.guiTop  - 5;
        initWarningMessage();

        Integer focus = ((IPlayerInterface) this.mc.player).getFocus();
        EquipSuit equipSuit = ((IPlayerInterface) this.mc.player).getSuitStack().getEquipSuitList().get(focus);
        EDIT_BUTTON = new BinarySwitchButton(buttonIds++, x + 1 , y - 4 ,14,10,"") {
            @Override
            public void onSwitchCase(boolean SwitchBinary) {
                canEdit =!canEdit;
                buttonClicked = false;
                CommonModEvents.NetWork.sendToServer(new SuitSingleChange());
            }
        };
        this.addButton(EDIT_BUTTON);
        for(int i=0 ;i<4;i++){
            ITextComponent translatable = new TextComponentTranslation(Messages.SUIT_NUM[i]);
            translatable.setStyle(new Style().setColor(Messages.SUIT_NUM_COLORS_FORMAT[i]));
            TradeOfferButton tradeOfferButton = new TradeOfferButton(buttonIds++ ,x + 1 + i * 12, y - 16, i, 13, 11 , translatable, IndexPress) {
                @Override
                public void renderToolTip(int p_99212_, int p_99213_) {

                }
            };

            suitIndexButtons[i] = tradeOfferButton;
            this.addButton(tradeOfferButton);
        }

        SUIT_NAME = new GuiTextField(0, this.fontRenderer,x + 54 ,y - 13 ,56 ,14);
        SUIT_NAME.setText(equipSuit.getName());
        SUIT_NAME.setMaxStringLength(10);
        SUIT_NAME.setCanLoseFocus(true);
        SUIT_NAME.setGuiResponder(new GuiPageButtonList.GuiResponder() {
            @Override
            public void setEntryValue(int i, boolean b) {

            }

            @Override
            public void setEntryValue(int i, float v) {

            }

            @Override
            public void setEntryValue(int i, String s) {
                Integer focus1 = ((IPlayerInterface) Minecraft.getMinecraft().player).getFocus();
                CommonModEvents.NetWork.sendToServer(new UpdateSuitName(focus1,s));
            }
        });
//        this.addRenderableWidget(SUIT_NAME);

        for (int i=0;i<4;i++){
            TradeOfferButton tradeOfferButton = new TradeOfferButton(buttonIds++ ,x + 1, y + 7 + i * 15, i, 14, 14,  new TextComponentTranslation(EquipSuitTemplate.PART[i]), SlotUpdatePress) {
                @Override
                public void renderToolTip(int p_99212_, int p_99213_) {
                    if (this.isMouseOver() && !buttonClicked) {
                        GlStateManager.disableDepth();
                        SuitInventoryScreen.this.drawHoveringText(Messages.PART_NAME[index],(int)xMouse,(int)yMouse);
                        GlStateManager.enableDepth();
                    }
                }
            };
            slotIndexButtons[i] = tradeOfferButton;
            this.addButton(tradeOfferButton);
        }
    }

    @Override
    public void drawScreen(int p_97796_, int p_97797_, float p_97798_) {
        this.drawDefaultBackground();
        super.drawScreen(p_97796_, p_97797_, p_97798_);
        if(buttonClicked){
//            renderComponentTooltip(p_97795_,EDITING_MESSAGE, p_97796_,p_97797_);
//            renderComponentTooltip(p_97795_,WARNING_MESSAGE,this.leftPos-10 ,this.topPos-40 );
            drawHoveringText(EDITING_MESSAGE, p_97796_, p_97797_);
            drawHoveringText(WARNING_MESSAGE,this.guiLeft -10 ,this.guiTop -40 );
        }else {
            this.renderHoveredToolTip( p_97796_, p_97797_);
        }
        SUIT_NAME.drawTextBox();
        this.xMouse = (float)p_97796_;
        this.yMouse = (float)p_97797_;
        drawString(fontRenderer, Messages.TAG_MODE + Messages.MODE_NAME[EquipSuitClientConfig.CHANGE_MODE ? 0 : 1]  , guiLeft,guiTop + 170, 0xFFFFFF);
    }

    @Override
    public void updateScreen() {
        super.updateScreen();
        IPlayerInterface iPlayer = (IPlayerInterface) Minecraft.getMinecraft().player;
        Integer focus = iPlayer.getFocus();
        EquipSuit equipSuit = iPlayer.getSuitStack().getEquipSuitList().get(focus);
        EDIT_BUTTON.binary = canEdit;
//        SUIT_NAME.setEnabled(SUIT_NAME.isFocused());
//        if(InputConstants.isKeyDown(Minecraft.getInstance().getWindow().getWindow(), 257) && SUIT_NAME.isFocused()){
//           SUIT_NAME.setFocus(false);
//           SUIT_NAME.setValue(equipSuit.getName());
//        }
        SUIT_NAME.setEnabled(SUIT_NAME.isFocused());
        SUIT_NAME.setTextColor(-1);
        if(SUIT_NAME.isFocused() && Keyboard.isKeyDown(Keyboard.KEY_RETURN)){
            SUIT_NAME.setFocused(false);
            SUIT_NAME.setText(equipSuit.getName());
        }
        if(lastFocus != focus) SUIT_NAME.setText(equipSuit.getName());
        Arrays.stream(suitIndexButtons).forEach(tradeOfferButton -> tradeOfferButton.enabled = tradeOfferButton.index != focus && !buttonClicked && !canEdit);
        Arrays.stream(slotIndexButtons).forEach(tradeOfferButton -> tradeOfferButton.enabled = canEdit &&(!buttonClicked || tradeOfferButton.index == ChangeIndex));
        this.lastFocus = focus;
    }

    @Override
    protected void drawGuiContainerBackgroundLayer(float p_97788_, int p_97789_, int p_97790_) {
//        RenderSystem.setShader(GameRenderer::getPositionTexShader);
//        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
//        RenderSystem.setShaderTexture(0, INVENTORY_LOCATION);
        this.mc.getTextureManager().bindTexture(INVENTORY_BACKGROUND);
        int i = this.guiLeft;
        int j = this.guiTop;
        this.drawTexturedModalRect(i, j, 0, 0, this.xSize, this.ySize);
        renderEntityInInventory(i + 51, j + 75, 30, (float)(i + 51) - this.xMouse, (float)(j + 75 - 50) - this.yMouse, this.mc.player);
        renderSuitInventory(p_97790_);
        ArrayList<EquipSuit> equipSuitList = ((IPlayerInterface) Minecraft.getMinecraft().player).getSuitStack().getEquipSuitList();
        equipSuitList.forEach(new Consumer<EquipSuit>() {
            @Override
            public void accept(EquipSuit equipSuit) {
                equipSuit.left().forEach(new BiConsumer<String, BaseSlot>() {
                    @Override
                    public void accept(String s, BaseSlot slot) {
                        if(slot.ContainerType() == ContainerTypes.TYPE_SUIT)
                        markSlot(slot.getSlotNum(),equipSuit.num(),s);
                    }
                });
            }
        });
    }

    protected void renderSuitInventory(int p_97790_){
//        RenderSystem.setShader(GameRenderer::getPositionTexShader);
//        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
//        RenderSystem.setShaderTexture(0, SUIT_INVENTORY);
        this.mc.getTextureManager().bindTexture(SUIT_INVENTORY);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        int i = this.guiLeft;
        int j = this.guiTop - 24;
        drawModalRectWithCustomSizedTexture(i-102, j, 0, 0, 100,200,100,200);
        for(int k = 0; k < 36; k++) {
            this.blit(this.guiLeft-((4 -( k % 4)) * 18 + 5), (int) (this.guiTop - 5 +  ( 7 + Math.ceil(k / 4) * 19)),p_97790_,TextureType.INVENTORY_SLOT);
        }
    }

    public static void renderEntityInInventory(int p_drawEntityOnScreen_0_, int p_drawEntityOnScreen_1_, int p_drawEntityOnScreen_2_, float p_drawEntityOnScreen_3_, float p_drawEntityOnScreen_4_, EntityLivingBase p_drawEntityOnScreen_5_) {
        GlStateManager.enableColorMaterial();
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)p_drawEntityOnScreen_0_, (float)p_drawEntityOnScreen_1_, 50.0F);
        GlStateManager.scale((float)(-p_drawEntityOnScreen_2_), (float)p_drawEntityOnScreen_2_, (float)p_drawEntityOnScreen_2_);
        GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
        float lvt_6_1_ = p_drawEntityOnScreen_5_.renderYawOffset;
        float lvt_7_1_ = p_drawEntityOnScreen_5_.rotationYaw;
        float lvt_8_1_ = p_drawEntityOnScreen_5_.rotationPitch;
        float lvt_9_1_ = p_drawEntityOnScreen_5_.prevRotationYawHead;
        float lvt_10_1_ = p_drawEntityOnScreen_5_.rotationYawHead;
        GlStateManager.rotate(135.0F, 0.0F, 1.0F, 0.0F);
        RenderHelper.enableStandardItemLighting();
        GlStateManager.rotate(-135.0F, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(-((float)Math.atan((double)(p_drawEntityOnScreen_4_ / 40.0F))) * 20.0F, 1.0F, 0.0F, 0.0F);
        p_drawEntityOnScreen_5_.renderYawOffset = (float)Math.atan((double)(p_drawEntityOnScreen_3_ / 40.0F)) * 20.0F;
        p_drawEntityOnScreen_5_.rotationYaw = (float)Math.atan((double)(p_drawEntityOnScreen_3_ / 40.0F)) * 40.0F;
        p_drawEntityOnScreen_5_.rotationPitch = -((float)Math.atan((double)(p_drawEntityOnScreen_4_ / 40.0F))) * 20.0F;
        p_drawEntityOnScreen_5_.rotationYawHead = p_drawEntityOnScreen_5_.rotationYaw;
        p_drawEntityOnScreen_5_.prevRotationYawHead = p_drawEntityOnScreen_5_.rotationYaw;
        GlStateManager.translate(0.0F, 0.0F, 0.0F);
        RenderManager lvt_11_1_ = Minecraft.getMinecraft().getRenderManager();
        lvt_11_1_.setPlayerViewY(180.0F);
        lvt_11_1_.setRenderShadow(false);
        lvt_11_1_.renderEntity(p_drawEntityOnScreen_5_, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, false);
        lvt_11_1_.setRenderShadow(true);
        p_drawEntityOnScreen_5_.renderYawOffset = lvt_6_1_;
        p_drawEntityOnScreen_5_.rotationYaw = lvt_7_1_;
        p_drawEntityOnScreen_5_.rotationPitch = lvt_8_1_;
        p_drawEntityOnScreen_5_.prevRotationYawHead = lvt_9_1_;
        p_drawEntityOnScreen_5_.rotationYawHead = lvt_10_1_;
        GlStateManager.popMatrix();
        RenderHelper.disableStandardItemLighting();
        GlStateManager.disableRescaleNormal();
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.disableTexture2D();
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
    }

//    public static void renderEntityInInventory(int p_98851_, int p_98852_, int p_98853_, float p_98854_, float p_98855_, LivingEntity p_98856_) {
//        float f = (float)Math.atan(p_98854_ / 40.0F);
//        float f1 = (float)Math.atan(p_98855_ / 40.0F);
//        PoseStack posestack = RenderSystem.getModelViewStack();
//        posestack.pushPose();
//        posestack.translate((double)p_98851_, (double)p_98852_, 1050.0D);
//        posestack.scale(1.0F, 1.0F, -1.0F);
//        RenderSystem.applyModelViewMatrix();
//        PoseStack posestack1 = new PoseStack();
//        posestack1.translate(0.0D, 0.0D, 1000.0D);
//        posestack1.scale((float)p_98853_, (float)p_98853_, (float)p_98853_);
//        Quaternion quaternion = Vector3f.ZP.rotationDegrees(180.0F);
//        Quaternion quaternion1 = Vector3f.XP.rotationDegrees(f1 * 20.0F);
//        quaternion.mul(quaternion1);
//        posestack1.mulPose(quaternion);
//        float f2 = p_98856_.yBodyRot;
//        float f3 = p_98856_.getYRot();
//        float f4 = p_98856_.getXRot();
//        float f5 = p_98856_.yHeadRotO;
//        float f6 = p_98856_.yHeadRot;
//        p_98856_.yBodyRot = 180.0F + f * 20.0F;
//        p_98856_.setYRot(180.0F + f * 40.0F);
//        p_98856_.setXRot(-f1 * 20.0F);
//        p_98856_.yHeadRot = p_98856_.getYRot();
//        p_98856_.yHeadRotO = p_98856_.getYRot();
//        Lighting.setupForEntityInInventory();
//        EntityRenderDispatcher entityrenderdispatcher = Minecraft.getInstance().getEntityRenderDispatcher();
//        quaternion1.conj();
//        entityrenderdispatcher.overrideCameraOrientation(quaternion1);
//        entityrenderdispatcher.setRenderShadow(false);
//        MultiBufferSource.BufferSource multibuffersource$buffersource = Minecraft.getInstance().renderBuffers().bufferSource();
//        RenderSystem.runAsFancy(() -> {
//            entityrenderdispatcher.render(p_98856_, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F, posestack1, multibuffersource$buffersource, 15728880);
//        });
//        multibuffersource$buffersource.endBatch();
//        entityrenderdispatcher.setRenderShadow(true);
//        p_98856_.yBodyRot = f2;
//        p_98856_.setYRot(f3);
//        p_98856_.setXRot(f4);
//        p_98856_.yHeadRotO = f5;
//        p_98856_.yHeadRot = f6;
//        posestack.popPose();
//        RenderSystem.applyModelViewMatrix();
//        Lighting.setupFor3DItems();
//    }

    private void markSlot(int slotNum , int suit ,String s){
        int num = EquipSuitTemplate.KEY_INDEX.get(s);
        float red = (Messages.SUIT_NUM_COLORS[suit] >> 16 & 255) / 255.0F;
        float green =  (Messages.SUIT_NUM_COLORS[suit] >> 8 & 255) / 255.0F;
        float blue =  (Messages.SUIT_NUM_COLORS[suit] & 255) / 255.0F;
//        RenderSystem.setShaderColor(red, green, blue, 1.0F);
//        RenderSystem.setShaderTexture(0, SUIT_GUI);
        this.mc.getTextureManager().bindTexture(SUIT_GUI);
        GlStateManager.color(red, green, blue, 1.0F);
        int x = this.guiLeft-((4 -( slotNum % 4)) * 18 + 4);
        int y =(int) (this.guiTop - 5 +  ( 7 + Math.ceil(slotNum / 4) * 19));
        drawModalRectWithCustomSizedTexture(x  + suit * 4 , y  ,8,4, 4, 4,16,16);
        drawModalRectWithCustomSizedTexture(x  + suit * 4 , y  ,num * 4,12, 4, 4,16,16);
//        RenderSystem.setShaderColor(1.0F,1.0F,1.0F,1.0F);
        GlStateManager.color(1.0F,1.0F,1.0F,1.0F);
    }

    private void blit(int p_194037_, int p_194038_, int p_194039_, TextureType p_194040_) {
//        RenderSystem.setShaderColor(1.0F, 1.0F, 1.0F, 1.0F);
//        RenderSystem.setShaderTexture(0, TEXTURE_LOCATION);
//        GuiComponent.blit(p_194036_, p_194037_, p_194038_, p_194039_, (float)p_194040_.x, (float)p_194040_.y, p_194040_.w, p_194040_.h, 128, 128);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.mc.getTextureManager().bindTexture(TEXTURE_LOCATION);
        GuiIngame.drawModalRectWithCustomSizedTexture(p_194037_, p_194038_, (float)p_194040_.x, (float)p_194040_.y, p_194040_.w, p_194040_.h, 128, 128);
    }

    @Override
    protected void renderHoveredToolTip(int p_191948_1_, int p_191948_2_) {
        for (GuiButton guiButton : buttonList) {
            if(guiButton instanceof TradeOfferButton && guiButton.isMouseOver()){
                ((TradeOfferButton)guiButton).renderToolTip(p_191948_1_,p_191948_2_);
                return;
            }
        }
        super.renderHoveredToolTip(p_191948_1_, p_191948_2_);
    }

    @Override
    public void mouseClicked(int p_97748_, int p_97749_, int p_97750_) throws IOException {
        if(buttonClicked){
            Slot slot = this.getSlotUnderMouse();
            if(slot!=null && slot.slotNumber > INVENTORY_SIZE - 1 ){
                IPlayerInterface player = (IPlayerInterface) Minecraft.getMinecraft().player;
                Integer focus = player.getFocus();
                int newSlotNum = slot.slotNumber - INVENTORY_SIZE;
                CommonModEvents.NetWork.sendToServer(new UpdateSuitSlot(focus,key,newSlotNum));
                CommonModEvents.NetWork.sendToServer(new SuitSingleChange());
                buttonClicked = ! buttonClicked;
                canEdit = false;

            }
            if(slotIndexButtons[ChangeIndex].mousePressed(this.mc,p_97748_,p_97749_)){
                this.slotIndexButtons[ChangeIndex].mouseReleased(p_97748_,p_97749_);
            }
            if(EDIT_BUTTON.mousePressed(this.mc,p_97748_,p_97749_)){
                EDIT_BUTTON.mouseReleased(p_97748_,p_97749_);
            }
        }else{
            super.mouseClicked(p_97748_, p_97749_, p_97750_);
            SUIT_NAME.mouseClicked(p_97748_, p_97749_, p_97750_);
        }
    }

    protected void keyTyped(char p_keyTyped_1_, int p_keyTyped_2_) throws IOException {
        if(SUIT_NAME.isFocused()){
            SUIT_NAME.textboxKeyTyped(p_keyTyped_1_,p_keyTyped_2_);
        }else if(EquipSuitKeyMapping.CALL_SUIT_INVENTORY_KEY.isKeyDown()){
            onGuiClosed();
        }else {
            super.keyTyped(p_keyTyped_1_,p_keyTyped_2_);
        }
    }


//    @Override
//    public boolean keyPressed(int p_97765_, int p_97766_, int p_97767_) {
//
//            InputConstants.Key mouseKey = InputConstants.getKey(p_97765_, p_97766_);
//            if (p_97765_ == 256 && this.shouldCloseOnEsc()) {
//                this.onClose();
//                return true;
//            } else if (p_97765_ == 258) {
//                boolean flag = !hasShiftDown();
//                if (!this.changeFocus(flag)) {
//                    this.changeFocus(flag);
//                }
//                return false;
//            } else if (this.minecraft.options.keyInventory.isActiveAndMatches(mouseKey) || EquipSuitKeyMapping.CALL_SUIT_INVENTORY_KEY.getKey().equals(mouseKey)) {
//                if (!SUIT_NAME.isFocused()) this.onClose();
//                return true;
//            } else {
//                boolean handled = this.checkHotbarKeyPressed(p_97765_, p_97766_);// Forge MC-146650: Needs to return true when the key is handled
//                if (this.hoveredSlot != null && this.hoveredSlot.hasItem()) {
//                    if (this.minecraft.options.keyPickItem.isActiveAndMatches(mouseKey)) {
//                        this.slotClicked(this.hoveredSlot, this.hoveredSlot.index, 0, ClickType.CLONE);
//                        handled = true;
//                    } else if (this.minecraft.options.keyDrop.isActiveAndMatches(mouseKey)) {
//                        this.slotClicked(this.hoveredSlot, this.hoveredSlot.index, hasControlDown() ? 1 : 0, ClickType.THROW);
//                        handled = true;
//                    }
//                } else if (this.minecraft.options.keyDrop.isActiveAndMatches(mouseKey)) {
//                    handled = true; // Forge MC-146650: Emulate MC bug, so we don't drop from hotbar when pressing drop without hovering over a item.
//                }
//                return handled || (this.getFocused() != null && this.getFocused().keyPressed(p_97765_, p_97766_, p_97767_));
//            }
//        }
//    }
//
//    private Slot IFindSlot(double p_97748_, double p_97749_){
//        for(int i = 0; i < this.inventorySlots.inventorySlots.size(); ++i) {
//            Slot slot = this.inventorySlots.getSlot(i);
//            if (this.getSlotUnderMouse(slot.xPos,slot.yPos,16,16,p_97748_, p_97749_) && slot.isEnabled()) {
//                return slot;
//            }
//        }
//        return null;
//    }

    public void resetEditingMessage(){
        EDITING_MESSAGE.clear();
        EDITING_MESSAGE.add(Messages.TAG_EDITING);
        EDITING_MESSAGE.add(Messages.TAG_SUIT + Messages.SUIT_NUM[((IPlayerInterface) Minecraft.getMinecraft().player).getFocus()]);
        EDITING_MESSAGE.add(Messages.TAG_PART + Messages.PART_NAME[ChangeIndex]);
    }

    public void initWarningMessage(){
        WARNING_MESSAGE.clear();
        WARNING_MESSAGE.add(Messages.TAG_WARNING);
        WARNING_MESSAGE.add(Messages.NO_CLICK_RESULT);
        WARNING_MESSAGE.add(Messages.NO_CLICK_RESULT_1);
    }



    @Override
    public void onGuiClosed() {
        super.onGuiClosed();
        if(this.canEdit){
            CommonModEvents.NetWork.sendToServer(new SuitSingleChange());
            this.canEdit =!canEdit;
        }
    }
}


