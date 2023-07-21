package com.equipsuit.equip_suit_v1.client;

import com.equipsuit.equip_suit_v1.api.config.EquipSuitClientConfig;
import com.equipsuit.equip_suit_v1.api.modInterfcae.gui.FocusSuitHud;
import com.equipsuit.equip_suit_v1.api.modInterfcae.player.IPlayerInterface;
import com.equipsuit.equip_suit_v1.api.utils.EquipSuitHelper;
import com.equipsuit.equip_suit_v1.api.utils.Messages;
import com.equipsuit.equip_suit_v1.client.gui.FocusSuitHUD;
import com.equipsuit.equip_suit_v1.client.screen.EquipSuitClientConfigScreen;
import com.equipsuit.equip_suit_v1.common.CommonModEvents;
import com.equipsuit.equip_suit_v1.common.network.OpenOrCloseSuitInventory;
import com.equipsuit.equip_suit_v1.common.network.SuitChange;
import com.equipsuit.equip_suit_v1.common.network.SuitChangeNext;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.sounds.SoundEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class ClientEvents {
    public static FocusSuitHud FocusSuitHud =null;
    public static int inputDelay = 0;
    @SubscribeEvent
    public static void onKeyboardInput(InputEvent.Key event) {
        if(ClientModEvents.CALL_SUIT_INVENTORY_KEY.consumeClick()){
            CommonModEvents.NetWork.sendToServer(new OpenOrCloseSuitInventory());
        }
        if(ClientModEvents.MODE_CHANGE.consumeClick()){
            int i = EquipSuitClientConfig.CHANGE_MODE.get()==0 ? 1 : 0;
            EquipSuitClientConfig.CHANGE_MODE.set(i);
            FocusSuitHud.setMode(i);
            Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
        }
        SuitChangeClick();
        SettingScreen();
    }


    @SubscribeEvent
    public static void onOverlayRender(RenderGuiOverlayEvent event){
        if(FocusSuitHud == null) {
            FocusSuitHud=FocusSuitHUD.Create(event.getPoseStack());
        }
       FocusSuitHud.render();
    }

    @SubscribeEvent
    public static void clientTick(TickEvent.ClientTickEvent event){
        if (inputDelay > 0) inputDelay--;
    }

    public static void SettingScreen(){
        if(!ClientModEvents.CALL_SUIT_SETTING.consumeClick()) return;
        MutableComponent translatable = Component.translatable(Messages.EDIT_TITLE);
        Minecraft.getInstance().setScreen(new EquipSuitClientConfigScreen(translatable));
    }

    public static void SuitChangeClick(){
        if(inputDelay <=0){
            if(EquipSuitClientConfig.CHANGE_MODE.get().equals(1)){
                if(ClientModEvents.SELECT_SUIT_CHANGE_I.consumeClick())   SendSuitChange(0);
                if(ClientModEvents.SELECT_SUIT_CHANGE_II.consumeClick())  SendSuitChange(1);
                if(ClientModEvents.SELECT_SUIT_CHANGE_III.consumeClick()) SendSuitChange(2);
                if(ClientModEvents.SELECT_SUIT_CHANGE_IV.consumeClick())  SendSuitChange(3);
            }else if(EquipSuitClientConfig.CHANGE_MODE.get().equals(0)){
                if (ClientModEvents.SUIT_CHANGE.consumeClick()) SendSuitChange(null);
            }
        }
    }

    public static void SendSuitChange(@Nullable Integer nums){
        if (nums==null){
            CommonModEvents.NetWork.sendToServer(new SuitChangeNext());
        }else {
            CommonModEvents.NetWork.sendToServer(new SuitChange(nums));
        }
        inputDelay = 5;
    }

}
