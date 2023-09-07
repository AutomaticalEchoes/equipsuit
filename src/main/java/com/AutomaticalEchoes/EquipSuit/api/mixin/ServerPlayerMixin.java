package com.AutomaticalEchoes.EquipSuit.api.mixin;

import com.AutomaticalEchoes.EquipSuit.api.modInterfcae.player.IPlayerInterface;
import com.AutomaticalEchoes.EquipSuit.common.container.SuitContainer;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayer.class)
public abstract class ServerPlayerMixin extends Player {
    public ServerPlayerMixin(Level p_36114_, BlockPos p_36115_, float p_36116_, com.mojang.authlib.GameProfile p_36117_) {
        super(p_36114_, p_36115_, p_36116_, p_36117_);
    }

    @Inject(method = "restoreFrom", at = { @At("RETURN")} )
    public void restoreFrom(ServerPlayer p_9016_, boolean p_9017_, CallbackInfo callbackInfo){
        if (p_9017_ || this.level.getGameRules().getBoolean(GameRules.RULE_KEEPINVENTORY) || p_9016_.isSpectator()) {
            SuitContainer suitContainer = ((IPlayerInterface) p_9016_).getSuitContainer();
            ((IPlayerInterface) this).getSuitContainer().replaceWith(suitContainer);
        }
        ((IPlayerInterface) this).restore(p_9016_);
    }
}
