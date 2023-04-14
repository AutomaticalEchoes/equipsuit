package com.equipsuit.equip_suit_v1.api.mixin;

import com.equipsuit.equip_suit_v1.api.modInterfcae.player.IPlayerInterface;
import com.equipsuit.equip_suit_v1.api.modInterfcae.player.SuitStack;
import com.equipsuit.equip_suit_v1.api.modInterfcae.player.SuitStackImpl;
import com.equipsuit.equip_suit_v1.api.utils.EquipSuitHelper;
import com.equipsuit.equip_suit_v1.common.container.SuitContainer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;


@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity implements IPlayerInterface {
    private final SuitContainer suitContainer = new SuitContainer((Player) (Object)this);
    private SuitStack suitStack = new SuitStackImpl();
    private int focus;
    private static final EntityDataAccessor<Integer> FOCUS = SynchedEntityData.defineId(Player.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<CompoundTag> SUIT_STACK = SynchedEntityData.defineId(Player.class ,EntityDataSerializers.COMPOUND_TAG);
    protected PlayerMixin(EntityType<? extends LivingEntity> p_20966_, Level p_20967_) {
        super(p_20966_, p_20967_);
    }

    @Inject(method = {"defineSynchedData"},at = {@At("RETURN")})
    protected void defineSynchedData(CallbackInfo callbackInfo) {
        this.entityData.define(FOCUS, 0);
        this.entityData.define(SUIT_STACK,new SuitStackImpl().toTag());
    }
    @Inject(method = {"readAdditionalSaveData"},at = {@At("RETURN")})
    public void readAdditionalSaveData(CompoundTag compoundTag,CallbackInfo callbackInfo){
        if(compoundTag.contains("SuitStack")){
            CompoundTag suitTag = compoundTag.getCompound("SuitStack");
            suitStack = suitStack.readTag(suitTag);
        }
        this.entityData.set(SUIT_STACK,suitStack.toTag());
        ListTag containerTag = compoundTag.getList("EquipInventory", 10);
        suitContainer.load(containerTag);
        focus = compoundTag.getInt("Focus");
        this.entityData.set(FOCUS,focus);

    }

    @Inject(method = {"addAdditionalSaveData"},at = {@At("RETURN")})
    public void addAdditionalSaveData(CompoundTag p_36265_ ,CallbackInfo callbackInfo) {
        p_36265_.put("EquipInventory", this.suitContainer.save(new ListTag()));
        p_36265_.putInt("Focus",this.entityData.get(FOCUS));
        p_36265_.put("SuitStack",this.suitStack.toTag());
    }

    @Inject(method = {"dropEquipment"} ,at = {@At("RETURN")})
    public void dropEquipment(CallbackInfo callbackInfo){
        if (!this.level.getGameRules().getBoolean(GameRules.RULE_KEEPINVENTORY)) {
            this.suitContainer.dropAll((Player) (Object)this);
        }
    }



    public ArrayList<int[]> getSuitList() {
        return suitStack.readTag(this.entityData.get(SUIT_STACK)).getSuitArrayList();
    }

    public Integer getFocus() {
        return this.entityData.get(FOCUS);
    }

    public void setFocus(Integer integer) {
        if(EquipSuitHelper.SuitChange((Player) (Object)this,integer)){
            this.focus = integer;
            this.entityData.set(FOCUS,integer);
        }
    }

    public boolean setSuitArray(int num, int... ints) {
        try {
            this.suitStack.getSuitArrayList().set(num,ints);
            this.entityData.set(SUIT_STACK,suitStack.toTag());
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public void updateFocus(){
        if(EquipSuitHelper.SuitChange((Player) (Object)this)){
            this.focus = (focus+1)%4;
            this.entityData.set(FOCUS,focus);
        }
    }

    public void restore(Player player){
        this.focus = player.getEntityData().get(FOCUS);
        this.suitStack.readTag(player.getEntityData().get(SUIT_STACK));
        this.entityData.set(FOCUS,player.getEntityData().get(FOCUS));
        this.entityData.set(SUIT_STACK,player.getEntityData().get(SUIT_STACK));
    }

    public SuitContainer getSuitContainer() {
        return suitContainer;
    }

}
