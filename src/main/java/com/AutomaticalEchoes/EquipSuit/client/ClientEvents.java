package com.AutomaticalEchoes.EquipSuit.client;

import com.AutomaticalEchoes.EquipSuit.api.config.EquipSuitClientConfig;
import com.AutomaticalEchoes.EquipSuit.api.event.client.EquipSuitConfigScreenEvent;
import com.AutomaticalEchoes.EquipSuit.api.event.client.EquipSuitCreateHudEvent;
import com.AutomaticalEchoes.EquipSuit.api.event.client.EquipSuitKeyBoardEvent;
import com.AutomaticalEchoes.EquipSuit.api.modInterfcae.gui.EquipSuitHudInterface;
import com.AutomaticalEchoes.EquipSuit.api.modInterfcae.player.IPlayerInterface;
import com.AutomaticalEchoes.EquipSuit.api.utils.EquipSuitKeyMapping;
import com.AutomaticalEchoes.EquipSuit.common.CommonModEvents;
import com.AutomaticalEchoes.EquipSuit.common.network.OpenOrCloseSuitInventory;
import com.AutomaticalEchoes.EquipSuit.common.network.SuitChange;
import com.AutomaticalEchoes.EquipSuit.common.network.SuitChangeNext;
import net.minecraft.client.KeyMapping;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.sounds.SoundEvents;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class ClientEvents {
    public static EquipSuitHudInterface HUD = null;
    public static int inputDelay = 0;

    @SubscribeEvent
    public static void onClientSetup(final FMLClientSetupEvent event){
    }

    @SubscribeEvent
    public static void onKeyboardInput(InputEvent.Key event) {
        EquipSuitKeyMapping.KEY_MAPPING.forEach(ClientEvents::ClickCheck);
    }

    @SubscribeEvent
    public static void onOverlayRender(RenderGuiOverlayEvent.Post event){
        if(event.getOverlay()!= VanillaGuiOverlay.HOTBAR.type()) return;
        if(HUD == null) {
            EquipSuitCreateHudEvent createHudEvent = new EquipSuitCreateHudEvent();
            MinecraftForge.EVENT_BUS.post(createHudEvent);
            createHudEvent.getEquipSuitHUD().ifPresent(equipSuitHudInterface -> HUD = equipSuitHudInterface);
        }else {
            if(EquipSuitClientConfig.HUD_MODE.get() == 0){
                HUD.renderSimple(event.getPoseStack(), ((IPlayerInterface) (Minecraft.getInstance().player)).getFocus());
            }else {
                HUD.renderALl(event.getPoseStack(), ((IPlayerInterface) (Minecraft.getInstance().player)).getFocus());
            }
        }
    }

    @SubscribeEvent
    public static void clientTick(TickEvent.ClientTickEvent event){
        if (inputDelay > 0) inputDelay--;
    }

    public static void ClickCheck(KeyMapping keyMapping){
        if(keyMapping.consumeClick() && inputDelay <=0){
            EquipSuitKeyBoardEvent event = new EquipSuitKeyBoardEvent(keyMapping);
            MinecraftForge.EVENT_BUS.post(event);
            if(!event.isCanceled()) OnModKeyClick(keyMapping);
        }
    }

    public static void OnModKeyClick(KeyMapping keyMapping){
        if(EquipSuitKeyMapping.CALL_SUIT_INVENTORY_KEY.isActiveAndMatches(keyMapping.getKey())){
            CommonModEvents.NetWork.sendToServer(new OpenOrCloseSuitInventory());
        }
        if(EquipSuitKeyMapping.MODE_CHANGE.isActiveAndMatches(keyMapping.getKey())){
            int i = EquipSuitClientConfig.CHANGE_MODE.get()==0 ? 1 : 0;
            EquipSuitClientConfig.CHANGE_MODE.set(i);
            Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
        }
        SuitChangeClick(keyMapping);
        SettingScreen(keyMapping);
        inputDelay = 10;
    }

    public static void SettingScreen(KeyMapping keyMapping){
        if(!EquipSuitKeyMapping.CALL_SUIT_SETTING.isActiveAndMatches(keyMapping.getKey())) return;
        EquipSuitConfigScreenEvent equipSuitConfigScreenEvent = new EquipSuitConfigScreenEvent();
        MinecraftForge.EVENT_BUS.post(equipSuitConfigScreenEvent);
        Minecraft.getInstance().setScreen(equipSuitConfigScreenEvent.getScreen());
    }

    public static void SuitChangeClick(KeyMapping keyMapping){
            if(EquipSuitClientConfig.CHANGE_MODE.get().equals(1)){
                if(EquipSuitKeyMapping.SELECT_SUIT_CHANGE_I.isActiveAndMatches(keyMapping.getKey())) SendSuitChange(0);
                if(EquipSuitKeyMapping.SELECT_SUIT_CHANGE_II.isActiveAndMatches(keyMapping.getKey())) SendSuitChange(1);
                if(EquipSuitKeyMapping.SELECT_SUIT_CHANGE_III.isActiveAndMatches(keyMapping.getKey())) SendSuitChange(2);
                if(EquipSuitKeyMapping.SELECT_SUIT_CHANGE_IV.isActiveAndMatches(keyMapping.getKey())) SendSuitChange(3);
            }else if(EquipSuitClientConfig.CHANGE_MODE.get().equals(0)){
                if(EquipSuitKeyMapping.SUIT_CHANGE.isActiveAndMatches(keyMapping.getKey())) SendSuitChange(null);
            }
    }

    public static void SendSuitChange(@Nullable Integer nums) {
        if (nums == null) {
            CommonModEvents.NetWork.sendToServer(new SuitChangeNext());
        } else if (!((IPlayerInterface) Minecraft.getInstance().player).getFocus().equals(nums)) {
            CommonModEvents.NetWork.sendToServer(new SuitChange(nums));
        }
    }
}
