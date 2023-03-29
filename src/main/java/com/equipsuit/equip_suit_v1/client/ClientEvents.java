package com.equipsuit.equip_suit_v1.client;

import com.equipsuit.equip_suit_v1.api.modInterfcae.gui.FocusSuitHud;
import com.equipsuit.equip_suit_v1.api.modInterfcae.player.IPlayerInterface;
import com.equipsuit.equip_suit_v1.client.gui.FocusSuitHUD;
import com.equipsuit.equip_suit_v1.common.CommonModEvents;
import com.equipsuit.equip_suit_v1.common.network.OpenSuitInventory;
import com.equipsuit.equip_suit_v1.common.network.SuitChange;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.client.event.RenderGuiOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class ClientEvents {
    public static FocusSuitHud focusSuitHUD =null;
    @SubscribeEvent
    public static void onKeyboardInput(InputEvent.Key event) {
        if (ClientModEvents.SUIT_CHANGE.consumeClick()) {
            CommonModEvents.NetWork.sendToServer(new SuitChange());
        }
        if(ClientModEvents.CALL_SUIT_INVENTORY_KEY.consumeClick()){
              CommonModEvents.NetWork.sendToServer(new OpenSuitInventory());
        }
    }
    @SubscribeEvent
    public static void onOverlayRender(RenderGuiOverlayEvent event){
        if(focusSuitHUD == null) {
            focusSuitHUD=FocusSuitHUD.Create(event.getPoseStack());
        }
       focusSuitHUD.render(((IPlayerInterface)(Minecraft.getInstance().player)).getFocus().toString());
    }

}
