package com.automaticalechoes.equipsuit.api.mixin;

import com.automaticalechoes.equipsuit.api.modInterfcae.player.IPlayerInterface;
import com.automaticalechoes.equipsuit.common.container.SuitContainer;
import com.mojang.authlib.GameProfile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(EntityPlayerMP.class)
public abstract class ServerPlayerMixin extends EntityPlayer {


    public ServerPlayerMixin(World p_i45324_1_, GameProfile p_i45324_2_) {
        super(p_i45324_1_, p_i45324_2_);
    }

    @Inject(method = "copyFrom", at = { @At("RETURN")} )
    public void restoreFrom(EntityPlayerMP p_9016_, boolean p_9017_, CallbackInfo callbackInfo){
        if (p_9017_ || this.world.getGameRules().getBoolean("keepInventory") || p_9016_.isSpectator()) {
            SuitContainer suitContainer = ((IPlayerInterface) p_9016_).getSuitContainer();
            ((IPlayerInterface) this).getSuitContainer().replaceWith(suitContainer);
        }
        ((IPlayerInterface) this).restore(p_9016_);
    }

    @Inject(method = {"onDeath"} ,at = {@At( value = "INVOKE" , target = "Lnet/minecraft/entity/player/InventoryPlayer;dropAllItems()V")})
    public void onDeath(CallbackInfo callbackInfo){
        ((IPlayerInterface) (Object)this).dropEquipment();
    }
}
