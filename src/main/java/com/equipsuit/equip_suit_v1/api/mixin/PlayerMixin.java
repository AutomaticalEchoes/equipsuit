package com.equipsuit.equip_suit_v1.api.mixin;

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
import net.minecraft.world.entity.player.Player;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;



@Mixin(Player.class)
public abstract class PlayerMixin implements IPlayerInterface {
    private final SuitContainer suitContainer = new SuitContainer();
    private final SuitStack suitStack = new SuitStackImpl(suitContainer);
    private final SuitInventoryMenu suitInventoryMenu = new SuitInventoryMenu(((Player)(Object)this).getInventory(),9);
    private Integer focus = 0;
    public PlayerMixin(Integer focus) {
        this.focus = focus;
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
    }

    @Inject(method = {"addAdditionalSaveData"},at = {@At("RETURN")})
    public void addAdditionalSaveData(CompoundTag p_36265_ ,CallbackInfo callbackInfo) {
        p_36265_.put("EquipInventory", this.suitContainer.save(new ListTag()));
        p_36265_.putInt("Focus",this.focus);
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
        EquipSuitHelper.SuitChangeWithoutOff((Player)(Object)this,suitArrayList.get(focus));
        EquipSuitHelper.SuitChangeWithoutOff((Player)(Object)this,suitArrayList.get(focus+1));
        focus++;
    }

    public NonNullList<ContainerEquipSuit> getSuitList() {
        return suitStack.getSuitArrayList();
    }

    public Integer getFocus() {
        return focus;
    }

    public void setFocus(Integer integer) {
        this.focus=integer;
    }

    public SuitContainer getSuitContainer() {
        return suitContainer;
    }

    public SuitInventoryMenu getSuitInventoryMenu() {
        return suitInventoryMenu;
    }
}
