package com.AutomaticalEchoes.EquipSuit.api.mixin;

import com.AutomaticalEchoes.EquipSuit.api.modInterfcae.player.IPlayerInterface;
import com.AutomaticalEchoes.EquipSuit.api.modInterfcae.player.SuitStack;
import com.AutomaticalEchoes.EquipSuit.api.modInterfcae.player.SuitStackImpl;
import com.AutomaticalEchoes.EquipSuit.api.utils.EquipSuitHelper;
import com.AutomaticalEchoes.EquipSuit.common.container.SuitContainer;
import com.AutomaticalEchoes.EquipSuit.common.registry.SerializerRegistry;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity implements IPlayerInterface {
    private final SuitContainer suitContainer = new SuitContainer((Player) (Object)this);
    private SuitStack suitStack = new SuitStackImpl().defaultSet();
    private int focus;
    private static final EntityDataAccessor<Integer> FOCUS = SynchedEntityData.defineId(Player.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<SuitStack> SUIT_STACK = SynchedEntityData.defineId(Player.class , SerializerRegistry.SUIT_STACK.get());
    protected PlayerMixin(EntityType<? extends LivingEntity> p_20966_, Level p_20967_) {
        super(p_20966_, p_20967_);
    }

    @Inject(method = {"defineSynchedData"},at = {@At("RETURN")})
    protected void defineSynchedData(CallbackInfo callbackInfo) {
        this.entityData.define(FOCUS, 0);
        this.entityData.define(SUIT_STACK,new SuitStackImpl().defaultSet());
    }

    @Inject(method = {"readAdditionalSaveData"},at = {@At("RETURN")})
    public void readAdditionalSaveData(CompoundTag compoundTag,CallbackInfo callbackInfo){
        if(compoundTag.contains("SuitStack")){
            CompoundTag suitTag = compoundTag.getCompound("SuitStack");
            suitStack = SuitStack.fromTag(suitTag);
        }
        this.entityData.set(SUIT_STACK,suitStack);
        ListTag containerTag = compoundTag.getList("EquipInventory", 10);
        suitContainer.load(containerTag);
        focus = compoundTag.getInt("Focus");
        this.entityData.set(FOCUS,focus);
    }

    @Inject(method = {"addAdditionalSaveData"},at = {@At("RETURN")})
    public void addAdditionalSaveData(CompoundTag p_36265_ ,CallbackInfo callbackInfo) {
        p_36265_.put("EquipInventory", this.suitContainer.save(new ListTag()));
        p_36265_.putInt("Focus",this.entityData.get(FOCUS));
        p_36265_.put("SuitStack",suitStack.toTag());
    }

    @Inject(method = {"dropEquipment"} ,at = {@At("RETURN")})
    public void dropEquipment(CallbackInfo callbackInfo){
        if (!this.level.getGameRules().getBoolean(GameRules.RULE_KEEPINVENTORY)) {
            this.suitContainer.dropAll((Player) (Object)this);
        }
    }

    public SuitStack getSuitStack() {
        return  this.level.isClientSide() ? this.entityData.get(SUIT_STACK) : this.suitStack;
    }

    public Integer getFocus() {
        return this.level.isClientSide() ? this.entityData.get(FOCUS) : this.focus;
    }

    public void setFocus(Integer integer) {
        if(EquipSuitHelper.SuitChange((ServerPlayer) (Object)this,integer)){
            this.focus = integer;
            this.entityData.set(FOCUS,focus);
        }
    }

    @Override
    public boolean setSuitSlotNum(int num, String key, int slotNum) {
        boolean b = suitStack.setSuitSlotNum(num, key, slotNum);
        this.entityData.set(SUIT_STACK,suitStack);
        return b;
    }

    public void updateFocus(){
        if(EquipSuitHelper.SuitChange((ServerPlayer) (Object)this)){
            this.focus = (focus+1)%4;
            this.entityData.set(FOCUS,focus);
        }
    }

    public void restore(Player player){
        this.focus = ((IPlayerInterface) player).getFocus();
        this.suitStack = ((IPlayerInterface) player).getSuitStack();
        this.entityData.set(FOCUS,focus);
        this.entityData.set(SUIT_STACK,suitStack);
    }

    public SuitContainer getSuitContainer() {
        return suitContainer;
    }

    public void setSuitName(int num,String s){
        this.suitStack.getEquipSuitList().get(num).setName(s);
        this.entityData.set(SUIT_STACK,suitStack);
    }
}
