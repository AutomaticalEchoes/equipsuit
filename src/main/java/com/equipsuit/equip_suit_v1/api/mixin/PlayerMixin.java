package com.equipsuit.equip_suit_v1.api.mixin;

import com.equipsuit.equip_suit_v1.api.equipsuit.ContainerEquipSuit;
import com.equipsuit.equip_suit_v1.api.equipsuit.EquipSuit;
import com.equipsuit.equip_suit_v1.api.utils.EquipSuitHelper;
import com.equipsuit.equip_suit_v1.common.container.SuitContainer;
import com.equipsuit.equip_suit_v1.common.container.SuitInventoryMenu;
import com.mojang.authlib.GameProfile;
import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntArrayTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.player.ProfilePublicKey;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;



@Mixin(Player.class)
public abstract class PlayerMixin implements IPlayerInterface{
    private final SuitContainer suitContainer=new SuitContainer();
    private final NonNullList<ContainerEquipSuit> suitArrayList = NonNullList.withSize(4,ContainerEquipSuit.build(suitContainer));
    private final SuitInventoryMenu suitInventoryMenu = new SuitInventoryMenu(invokerGetInventory(),9);
    private Integer focus=0;


    @Inject(method = {"<readAdditionalSaveData>"},at = {@At("RETURN")})
    public void readAdditionalSaveData(CompoundTag compoundTag,CallbackInfo callbackInfo){
        ListTag suitTag =compoundTag.getList("SuitArrayList",10);
        for(int i=0;i<4;i++){
            int[] intArray = suitTag.getIntArray(i);
            suitArrayList.set(i,ContainerEquipSuit.build(suitContainer,intArray));
        }
        ListTag containerTag = compoundTag.getList("EquipInventory", 10);
        suitContainer.load(containerTag);
        focus = compoundTag.getInt("Focus");
    }
    @Inject(method = {"<addAdditionalSaveData>"},at = {@At("RETURN")})
    public void addAdditionalSaveData(CompoundTag p_36265_ ,CallbackInfo callbackInfo) {
        p_36265_.put("EquipInventory", this.suitContainer.save(new ListTag()));
        p_36265_.putInt("Focus",this.focus);
        p_36265_.put("SuitArrayList",saveSuitArray());
    }

    private ListTag saveSuitArray(){
        ListTag listTag=new ListTag();
        suitArrayList.stream().filter(suit -> {
            int[] ints = suit.getContainerSlotNums().stream().mapToInt(value -> (int)value).toArray();
            return listTag.add(new IntArrayTag(ints));
        });
        return listTag;
    }

    @Override
    public void suiChange() {
        EquipSuitHelper.SuitChangeWithoutOff((Player)(Object)this,suitArrayList.get(focus));
        EquipSuitHelper.SuitChangeWithoutOff((Player)(Object)this,suitArrayList.get(focus+1));
        focus++;
    }

    @Override
    public NonNullList<ContainerEquipSuit> getSuitList() {
        return suitArrayList;
    }

    @Override
    public Integer getFocus() {
        return focus;
    }

    @Override
    public void setFocus(Integer integer) {
        this.focus=integer;
    }

    @Override
    public SuitContainer getSuitContainer() {
        return suitContainer;
    }

    public SuitInventoryMenu getSuitInventoryMenu() {
        return suitInventoryMenu;
    }
}
