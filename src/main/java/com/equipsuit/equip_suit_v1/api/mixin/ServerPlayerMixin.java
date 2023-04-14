package com.equipsuit.equip_suit_v1.api.mixin;

import com.equipsuit.equip_suit_v1.EquipSuitChange;
import com.equipsuit.equip_suit_v1.api.modInterfcae.player.IPlayerInterface;
import com.equipsuit.equip_suit_v1.common.container.SuitContainer;
import com.mojang.authlib.GameProfile;
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
    public ServerPlayerMixin(Level p_36114_, BlockPos p_36115_, float p_36116_, GameProfile p_36117_) {
        super(p_36114_, p_36115_, p_36116_, p_36117_);
    }

    @Inject(method = "restoreFrom", at = { @At("RETURN")} )
    public void restoreFrom(ServerPlayer p_9016_, boolean p_9017_, CallbackInfo callbackInfo){
        if (this.level.getGameRules().getBoolean(GameRules.RULE_KEEPINVENTORY) || p_9016_.isSpectator()) {
            SuitContainer suitContainer = ((IPlayerInterface) (Object) p_9016_).getSuitContainer();
            EquipSuitChange.LOGGER.info(suitContainer.toString());
            ((IPlayerInterface)(Object)this).getSuitContainer().replaceWith(suitContainer);
        }
        ((IPlayerInterface)(Object)this).restore(p_9016_);
    }
}
