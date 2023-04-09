package com.equipsuit.equip_suit_v1.api.mixin;

import com.equipsuit.equip_suit_v1.EquipSuitChange;
import com.equipsuit.equip_suit_v1.api.modInterfcae.player.IPlayerInterface;
import com.equipsuit.equip_suit_v1.api.modInterfcae.player.SuitStack;
import com.equipsuit.equip_suit_v1.api.modInterfcae.player.SuitStackImpl;
import com.equipsuit.equip_suit_v1.api.utils.EquipSuitHelper;
import com.equipsuit.equip_suit_v1.api.utils.IPlayerInvWrapper;
import com.equipsuit.equip_suit_v1.api.utils.PlayerSuitContainerWrapper;
import com.equipsuit.equip_suit_v1.common.container.SuitContainer;
import com.equipsuit.equip_suit_v1.common.registry.EntityDataRegister;
import net.minecraft.core.Direction;
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
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import javax.annotation.Nullable;
import java.util.ArrayList;


@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity implements IPlayerInterface {
    private final SuitContainer suitContainer = new SuitContainer((Player) (Object)this);
    private SuitStack suitStack = new SuitStackImpl();
    private int focus;
    private static final EntityDataAccessor<Integer> FOCUS = SynchedEntityData.defineId(Player.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<SuitStack> SUIT_STACK = SynchedEntityData.defineId(Player.class , EntityDataRegister.SUIT_STACK.get());
    protected PlayerMixin(EntityType<? extends LivingEntity> p_20966_, Level p_20967_) {
        super(p_20966_, p_20967_);
    }

    @Inject(method = {"defineSynchedData"},at = {@At("RETURN")})
    protected void defineSynchedData(CallbackInfo callbackInfo) {
        this.entityData.define(FOCUS, 0);
        this.entityData.define(SUIT_STACK,new SuitStackImpl());
    }
    @Inject(method = {"readAdditionalSaveData"},at = {@At("RETURN")})
    public void readAdditionalSaveData(CompoundTag compoundTag,CallbackInfo callbackInfo){
        if(compoundTag.contains("SuitStack")){
            CompoundTag suitTag = compoundTag.getCompound("SuitStack");
            for(int i=0;i<4;i++){
                int[] intArray = suitTag.getIntArray(String.valueOf(i));
                suitStack.setSuitSlotNums(i,intArray);
            }
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
        p_36265_.put("SuitStack",saveSuitArray());
    }

    @Inject(method = {"dropEquipment"} ,at = {@At("RETURN")})
    public void dropEquipment(CallbackInfo callbackInfo){
        if (!this.level.getGameRules().getBoolean(GameRules.RULE_KEEPINVENTORY)) {
            this.suitContainer.dropAll((Player) (Object)this);
        }
    }

    private CompoundTag saveSuitArray(){
        CompoundTag compoundtag = new CompoundTag();
        for(int i=0;i<4;i++){
            int[] ints = this.suitStack.getSuitArrayList().get(i);
            compoundtag.putIntArray(String.valueOf(i),ints);
        }
        return compoundtag;
    }

    public ArrayList<int[]> getSuitList() {
        return this.entityData.get(SUIT_STACK).getSuitArrayList();
    }

    public Integer getFocus() {
        return this.entityData.get(FOCUS);
    }

    public void setFocus(Integer integer) {
        if(EquipSuitHelper.SuitUpdate((Player) (Object)this,integer)){
            this.focus = integer;
            this.entityData.set(FOCUS,integer);
        }
    }

    public boolean setSuitArray(int num, int... ints) {
        try {
            this.suitStack.getSuitArrayList().set(num,ints);
            this.entityData.set(SUIT_STACK,suitStack);
            return true;
        }catch (Exception e){
            return false;
        }
    }

    public void updateFocus(){
        if(EquipSuitHelper.SuitUpdate((Player) (Object)this)){
            this.focus = (focus+1)%4;
            this.entityData.set(FOCUS,focus);
        }
    }

    public SuitContainer getSuitContainer() {
        return suitContainer;
    }

    private final net.minecraftforge.common.util.LazyOptional<net.minecraftforge.items.IItemHandler>
            playerMainHandler = net.minecraftforge.common.util.LazyOptional.of(
            () -> new CombinedInvWrapper(
                    new net.minecraftforge.items.wrapper.PlayerMainInvWrapper(((Player)(Object)this).getInventory()),
                    new PlayerSuitContainerWrapper(suitContainer)));

    private final net.minecraftforge.common.util.LazyOptional<net.minecraftforge.items.IItemHandler>
            playerEquipmentHandler = net.minecraftforge.common.util.LazyOptional.of(
            () -> new net.minecraftforge.items.wrapper.CombinedInvWrapper(
                    new net.minecraftforge.items.wrapper.PlayerArmorInvWrapper(((Player)(Object)this).getInventory()),
                    new net.minecraftforge.items.wrapper.PlayerOffhandInvWrapper(((Player)(Object)this).getInventory()),
                    new PlayerSuitContainerWrapper(suitContainer)));

    private final net.minecraftforge.common.util.LazyOptional<net.minecraftforge.items.IItemHandler>
            playerJoinedHandler = net.minecraftforge.common.util.LazyOptional.of(
            () -> new IPlayerInvWrapper(((Player)(Object)this).getInventory(),suitContainer));

    @Inject(method = "getCapability",at = {@At("HEAD")},remap = false)
    public  void getCapability(net.minecraftforge.common.capabilities.Capability<?> capability, @Nullable Direction facing , CallbackInfoReturnable<net.minecraftforge.common.util.LazyOptional<?>> callbackInfoReturnable){
        if (this.isAlive() && capability == net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            if (facing == null){
                callbackInfoReturnable.setReturnValue(playerJoinedHandler.cast());
            } else if (facing.getAxis().isVertical()) {
                callbackInfoReturnable.setReturnValue(playerMainHandler.cast());
            } else if (facing.getAxis().isHorizontal()){
                callbackInfoReturnable.setReturnValue(playerEquipmentHandler.cast());
            }
        }
        callbackInfoReturnable.setReturnValue(super.getCapability(capability, facing)) ;
    }


}
