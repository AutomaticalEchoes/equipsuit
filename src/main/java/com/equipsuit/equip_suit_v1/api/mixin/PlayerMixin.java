package com.equipsuit.equip_suit_v1.api.mixin;

import com.equipsuit.equip_suit_v1.EquipSuitChange;
import com.equipsuit.equip_suit_v1.api.ModInterfcae.player.IPlayerInterface;
import com.equipsuit.equip_suit_v1.api.ModInterfcae.equipsuit.ContainerEquipSuit;
import com.equipsuit.equip_suit_v1.api.ModInterfcae.player.SuitStack;
import com.equipsuit.equip_suit_v1.api.ModInterfcae.player.SuitStackImpl;
import com.equipsuit.equip_suit_v1.api.utils.EquipSuitHelper;
import com.equipsuit.equip_suit_v1.common.container.SuitContainer;
import com.equipsuit.equip_suit_v1.common.container.SuitInventoryMenu;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntArrayTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;



@Mixin(Player.class)
public abstract class PlayerMixin extends LivingEntity implements IPlayerInterface {
    private final SuitContainer suitContainer = new SuitContainer();
    private final SuitStack suitStack = new SuitStackImpl(suitContainer);
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
            suitStack.getSuitArrayList().set(i,ContainerEquipSuit.buildInt(suitContainer,intArray));
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
        suitStack.getSuitArrayList().stream().filter(suit -> {
            int[] ints = suit.getContainerSlotNums().stream().mapToInt(value -> (int)value).toArray();
            return listTag.add(new IntArrayTag(ints));
        });
        return listTag;
    }

    public void suiChange() {
        NonNullList<ContainerEquipSuit> suitArrayList = suitStack.getSuitArrayList();
        EquipSuitHelper.SuitChangeWithoutOff((Player)(Object)this,suitArrayList.get(getFocus()).build());
        EquipSuitHelper.SuitChangeWithoutOff((Player)(Object)this,suitArrayList.get((getFocus() + 1) % 4).build());
        this.entityData.set(FOCUS,(getFocus() + 1) % 4);
    }

    public NonNullList<ContainerEquipSuit> getSuitList() {
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
