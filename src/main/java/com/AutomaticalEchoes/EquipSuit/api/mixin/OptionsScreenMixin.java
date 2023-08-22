package com.AutomaticalEchoes.EquipSuit.api.mixin;

import net.minecraft.client.Options;
import net.minecraft.client.gui.screens.OptionsScreen;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;


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
