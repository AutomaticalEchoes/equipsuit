package com.AutomaticalEchoes.EquipSuit.client;

import com.AutomaticalEchoes.EquipSuit.api.config.EquipSuitClientConfig;
import com.AutomaticalEchoes.EquipSuit.api.modInterfcae.gui.FocusSuitHud;
import com.AutomaticalEchoes.EquipSuit.api.modInterfcae.player.IPlayerInterface;
import com.AutomaticalEchoes.EquipSuit.api.utils.Messages;
import com.AutomaticalEchoes.EquipSuit.client.gui.FocusSuitHUD;
import com.AutomaticalEchoes.EquipSuit.client.screen.EquipSuitClientConfigScreen;
import com.AutomaticalEchoes.EquipSuit.common.CommonModEvents;
import com.AutomaticalEchoes.EquipSuit.common.network.OpenOrCloseSuitInventory;
import com.AutomaticalEchoes.EquipSuit.common.network.SuitChange;
import com.AutomaticalEchoes.EquipSuit.common.network.SuitChangeNext;
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
        if(ClientModEvents.MODE_CHANGE.consumeClick() && inputDelay <= 0){
            int i = EquipSuitClientConfig.CHANGE_MODE.get()==0 ? 1 : 0;
            EquipSuitClientConfig.CHANGE_MODE.set(i);
            FocusSuitHud.setMode(i);
            Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
            inputDelay = 10;
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
            if(EquipSuitClientConfig.CHANGE_MODE.get().equals(1)){
                if(ClientModEvents.SELECT_SUIT_CHANGE_I.consumeClick()  && inputDelay <=0) SendSuitChange(0);
                if(ClientModEvents.SELECT_SUIT_CHANGE_II.consumeClick() && inputDelay <=0) SendSuitChange(1);
                if(ClientModEvents.SELECT_SUIT_CHANGE_III.consumeClick()&& inputDelay <=0) SendSuitChange(2);
                if(ClientModEvents.SELECT_SUIT_CHANGE_IV.consumeClick() && inputDelay <=0) SendSuitChange(3);
            }else if(EquipSuitClientConfig.CHANGE_MODE.get().equals(0)){
                if (ClientModEvents.SUIT_CHANGE.consumeClick() && inputDelay <=0) SendSuitChange(null);
            }
    }

    public static void SendSuitChange(@Nullable Integer nums){
        if (nums==null){
            CommonModEvents.NetWork.sendToServer(new SuitChangeNext());
        }else if (!((IPlayerInterface) Minecraft.getInstance().player).getFocus().equals(nums)){
            CommonModEvents.NetWork.sendToServer(new SuitChange(nums));
        }
        inputDelay = 10;
    }

}
