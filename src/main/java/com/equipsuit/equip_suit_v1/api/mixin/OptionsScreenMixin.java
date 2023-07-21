package com.equipsuit.equip_suit_v1.api.mixin;

import com.equipsuit.equip_suit_v1.api.utils.Messages;
import com.equipsuit.equip_suit_v1.client.screen.EquipSuitClientConfigScreen;
import net.minecraft.client.Options;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.OptionsScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.chat.CommonComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(OptionsScreen.class)
public class OptionsScreenMixin extends Screen {
    @Final
    @Shadow private Options options;

    protected OptionsScreenMixin(Component p_96550_) {
        super(p_96550_);
    }

//    @Inject(method = {"init"},at = {@At("RETURN")})
//    public void init( CallbackInfo callbackInfo) {
//        MutableComponent translatable = Component.translatable(Messages.EDIT_TITLE);
//        this.addRenderableWidget(new Button(this.width / 2 - 100, this.height / 6 + 150 - 6, 200, 20, translatable , (p_96257_) -> {
//            this.minecraft.setScreen(new EquipSuitClientConfigScreen(this,options, translatable));
//        }));
//    }
}
