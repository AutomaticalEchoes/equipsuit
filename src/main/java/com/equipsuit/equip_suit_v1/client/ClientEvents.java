package com.equipsuit.equip_suit_v1.client;

import com.equipsuit.equip_suit_v1.api.config.EquipSuitClientConfig;
import com.equipsuit.equip_suit_v1.api.modInterfcae.gui.FocusSuitHud;
import com.equipsuit.equip_suit_v1.api.modInterfcae.player.IPlayerInterface;
import com.equipsuit.equip_suit_v1.api.utils.EquipSuitHelper;
import com.equipsuit.equip_suit_v1.api.utils.Messages;
import com.equipsuit.equip_suit_v1.client.gui.FocusSuitHUD;
import com.equipsuit.equip_suit_v1.common.CommonModEvents;
import com.equipsuit.equip_suit_v1.common.network.OpenOrCloseSuitInventory;
import com.equipsuit.equip_suit_v1.common.network.SuitChange;
import com.equipsuit.equip_suit_v1.common.network.SuitChangeNext;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class ClientEvents {
    public static FocusSuitHud focusSuitHUD =null;
    @SubscribeEvent
    public static void onKeyboardInput(InputEvent.KeyInputEvent event) {
        if(ClientModEvents.CALL_SUIT_INVENTORY_KEY.consumeClick()){
            CommonModEvents.NetWork.sendToServer(new OpenOrCloseSuitInventory());
        }
        if(ClientModEvents.MODE_CHANGE.consumeClick()){
            int i =EquipSuitClientConfig.CHANGE_MODE.get()==0?1:0;
            EquipSuitClientConfig.CHANGE_MODE.set(i);
            focusSuitHUD.setMode(i);
            Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
        }
        if(EquipSuitClientConfig.CHANGE_MODE.get().equals(0)){
            if (ClientModEvents.SUIT_CHANGE.consumeClick()) sendSuitChange(null);
        }else if(EquipSuitClientConfig.CHANGE_MODE.get().equals(1)){
            if(ClientModEvents.SELECT_SUIT_CHANGE_I.consumeClick())   sendSuitChange(0);
            if(ClientModEvents.SELECT_SUIT_CHANGE_II.consumeClick())  sendSuitChange(1);
            if(ClientModEvents.SELECT_SUIT_CHANGE_III.consumeClick()) sendSuitChange(2);
            if(ClientModEvents.SELECT_SUIT_CHANGE_IV.consumeClick())  sendSuitChange(3);
        }
    }

    @SubscribeEvent
    public static void onOverlayRender(RenderGameOverlayEvent event){
        if(focusSuitHUD == null) {
            focusSuitHUD=FocusSuitHUD.Create(event.getMatrixStack());
        }
       focusSuitHUD.render(Messages.SUIT_NUM[((IPlayerInterface)(Minecraft.getInstance().player)).getFocus()]);
    }

    public static void sendSuitChange(@Nullable Integer num){
        if(num == null){
            CommonModEvents.NetWork.sendToServer(new SuitChangeNext());
        }else {
            CommonModEvents.NetWork.sendToServer(new SuitChange(num));
        }
        Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK,1.0F));
    }

}
