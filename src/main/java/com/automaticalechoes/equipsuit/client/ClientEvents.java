package com.automaticalechoes.equipsuit.client;

import com.automaticalechoes.equipsuit.EquipSuitChange;
import com.automaticalechoes.equipsuit.api.config.EquipSuitClientConfig;
import com.automaticalechoes.equipsuit.api.event.client.EquipSuitCreateHudEvent;
import com.automaticalechoes.equipsuit.api.event.client.EquipSuitKeyBoardEvent;
import com.automaticalechoes.equipsuit.api.modInterfcae.gui.EquipSuitHudInterface;
import com.automaticalechoes.equipsuit.api.modInterfcae.player.IPlayerInterface;
import com.automaticalechoes.equipsuit.api.utils.EquipSuitKeyMapping;
import com.automaticalechoes.equipsuit.common.CommonModEvents;
import com.automaticalechoes.equipsuit.common.network.OpenOrCloseSuitInventory;
import com.automaticalechoes.equipsuit.common.network.SuitChange;
import com.automaticalechoes.equipsuit.common.network.SuitChangeNext;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.client.event.GuiScreenEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.InputEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;

import javax.annotation.Nullable;

@Mod.EventBusSubscriber(Side.CLIENT)
public class ClientEvents {
    public static EquipSuitHudInterface HUD = null;
    public static int inputDelay = 0;


    public static void onClientSetup(FMLInitializationEvent event){
    }

    @SubscribeEvent
    public static void onKeyboardInput(InputEvent.KeyInputEvent event) {
        EquipSuitKeyMapping.KEY_MAPPING.forEach(ClientEvents::ClickCheck);
    }

    @SubscribeEvent
    public static void onOverlayRender(RenderGameOverlayEvent event){
        if(event.getType() != RenderGameOverlayEvent.ElementType.HOTBAR) return;
        if(HUD == null) {
            EquipSuitCreateHudEvent createHudEvent = new EquipSuitCreateHudEvent();
            MinecraftForge.EVENT_BUS.post(createHudEvent);
            createHudEvent.getEquipSuitHUD().ifPresent(equipSuitHudInterface -> HUD = equipSuitHudInterface);
        }else {
            HUD.initResolution(event.getResolution());
            if(EquipSuitClientConfig.HUD_MODE){
                HUD.renderSimple(((IPlayerInterface) (Minecraft.getMinecraft().player)).getFocus());
            }else {
                HUD.renderALl(((IPlayerInterface) (Minecraft.getMinecraft().player)).getFocus());
            }
        }
    }

    @SubscribeEvent
    public static void clientTick(TickEvent.ClientTickEvent event){
        if (inputDelay > 0) inputDelay--;
    }

    public static void ClickCheck(KeyBinding keyMapping){
        if(keyMapping.isKeyDown() && inputDelay <=0){
            EquipSuitKeyBoardEvent event = new EquipSuitKeyBoardEvent(keyMapping);
            MinecraftForge.EVENT_BUS.post(event);
            if(!event.isCanceled()) OnModKeyClick(keyMapping);
        }
    }

    public static void OnModKeyClick(KeyBinding keyMapping){
        if(EquipSuitKeyMapping.CALL_SUIT_INVENTORY_KEY.isActiveAndMatches(keyMapping.getKeyCode())){
            CommonModEvents.NetWork.sendToServer(new OpenOrCloseSuitInventory());
        }
        if(EquipSuitKeyMapping.MODE_CHANGE.isActiveAndMatches(keyMapping.getKeyCode())){
            EquipSuitClientConfig.CHANGE_MODE = !EquipSuitClientConfig.CHANGE_MODE;
            MinecraftForge.EVENT_BUS.post(new ConfigChangedEvent.OnConfigChangedEvent(EquipSuitChange.MODID,"equipsuit-client-setting",false,false));
//            Minecraft.getMinecraft().getSoundHandler().playSound(.forUI(SoundEvents.UI_BUTTON_CLICK, 1.0F));
        }
        SuitChangeClick(keyMapping);
//        SettingScreen(keyMapping);
        inputDelay = 10;
    }

//    public static void SettingScreen(KeyBinding keyMapping){
//        if(!EquipSuitKeyMapping.CALL_SUIT_SETTING.isActiveAndMatches(keyMapping.getKeyCode())) return;
//        EquipSuitConfigScreenEvent equipSuitConfigScreenEvent = new EquipSuitConfigScreenEvent();
//        MinecraftForge.EVENT_BUS.post(equipSuitConfigScreenEvent);
//        Minecraft.getInstance().setScreen(equipSuitConfigScreenEvent.getScreen());
//    }

    public static void SuitChangeClick(KeyBinding keyMapping){
            if(EquipSuitClientConfig.CHANGE_MODE){
                if(EquipSuitKeyMapping.SELECT_SUIT_CHANGE_I.isActiveAndMatches(keyMapping.getKeyCode())) SendSuitChange(0);
                if(EquipSuitKeyMapping.SELECT_SUIT_CHANGE_II.isActiveAndMatches(keyMapping.getKeyCode())) SendSuitChange(1);
                if(EquipSuitKeyMapping.SELECT_SUIT_CHANGE_III.isActiveAndMatches(keyMapping.getKeyCode())) SendSuitChange(2);
                if(EquipSuitKeyMapping.SELECT_SUIT_CHANGE_IV.isActiveAndMatches(keyMapping.getKeyCode())) SendSuitChange(3);
            }else{
                if(EquipSuitKeyMapping.SUIT_CHANGE.isActiveAndMatches(keyMapping.getKeyCode())) SendSuitChange(null);
            }
    }

    public static void SendSuitChange(@Nullable Integer nums) {
        if (nums == null) {
            CommonModEvents.NetWork.sendToServer(new SuitChangeNext());
        } else if (!((IPlayerInterface) Minecraft.getMinecraft().player).getFocus().equals(nums)) {
            CommonModEvents.NetWork.sendToServer(new SuitChange(nums));
        }
    }
}
