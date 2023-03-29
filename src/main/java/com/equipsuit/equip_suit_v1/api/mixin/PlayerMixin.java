package com.equipsuit.equip_suit_v1.api.mixin;

import com.equipsuit.equip_suit_v1.api.modInterfcae.player.IPlayerInterface;
import com.equipsuit.equip_suit_v1.api.modInterfcae.equipsuit.ContainerEquipSuit;
import com.equipsuit.equip_suit_v1.api.modInterfcae.player.SuitStack;
import com.equipsuit.equip_suit_v1.api.modInterfcae.player.SuitStackImpl;
import com.equipsuit.equip_suit_v1.api.utils.EquipSuitHelper;
import com.equipsuit.equip_suit_v1.common.container.SuitContainer;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntArrayTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.HashMap;


@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity implements IPlayerInterface {
    private final SuitContainer suitContainer = new SuitContainer();
    private final SuitStack suitStack = new SuitStackImpl();
    private int focus;
    private static final EntityDataAccessor<Integer> FOCUS = SynchedEntityData.defineId(Player.class, EntityDataSerializers.INT);

    protected PlayerMixin(EntityType<? extends LivingEntity> p_20966_, Level p_20967_) {
        super(p_20966_, p_20967_);
    }

    @Inject(method = {"defineSynchedData"},at = {@At("RETURN")})
    protected void defineSynchedData(CallbackInfo callbackInfo) {
        this.entityData.define(FOCUS, 0);
    }
    @Inject(method = {"readAdditionalSaveData"},at = {@At("RETURN")})
    public void readAdditionalSaveData(CompoundTag compoundTag,CallbackInfo callbackInfo){
        ListTag suitTag =compoundTag.getList("SuitArrayList",10);
        for(int i=0;i<4;i++){
            int[] intArray = suitTag.getIntArray(i);
            suitStack.setSuitSlotNums(i,intArray);
        }
        ListTag containerTag = compoundTag.getList("EquipInventory", 10);
        suitContainer.load(containerTag);
        focus = compoundTag.getInt("Focus");
        this.entityData.set(FOCUS,focus);
    }

    @Inject(method = {"addAdditionalSaveData"},at = {@At("RETURN")})
    public void addAdditionalSaveData(CompoundTag p_36265_ ,CallbackInfo callbackInfo) {
        p_36265_.put("EquipInventory", this.suitContainer.save(new ListTag()));
        p_36265_.putInt("Focus",this.entityData.get(FOCUS));
        p_36265_.put("SuitArrayList",saveSuitArray());
    }

    private ListTag saveSuitArray(){
        ListTag listTag=new ListTag();
        for(int i=0;i<4;i++){
            int[] ints = suitStack.getSuitArrayList().get(i);
            listTag.add(new IntArrayTag(ints));
        }
        return listTag;
    }

    public void suiChange() {
        HashMap<Integer, int[]> suitArrayList = suitStack.getSuitArrayList();
        EquipSuitHelper.SuitChangeWithoutOff((Player)(Object)this,ContainerEquipSuit.buildInt(this.suitContainer,suitArrayList.get(getFocus())).build());
        EquipSuitHelper.SuitChangeWithoutOff((Player)(Object)this,ContainerEquipSuit.buildInt(this.suitContainer,suitArrayList.get((getFocus() + 1) % 4)).build());
        this.entityData.set(FOCUS,(getFocus() + 1) % 4);
    }

    public HashMap<Integer, int[]> getSuitList() {
        return suitStack.getSuitArrayList();
    }

    public Integer getFocus() {
        return this.entityData.get(FOCUS);
    }

    public void setFocus(Integer integer) {
        this.focus = integer;
        this.entityData.set(FOCUS,integer);
    }

    public SuitContainer getSuitContainer() {
        return suitContainer;
    }

}
