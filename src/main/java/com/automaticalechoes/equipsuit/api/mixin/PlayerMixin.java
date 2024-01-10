package com.automaticalechoes.equipsuit.api.mixin;

import com.automaticalechoes.equipsuit.EquipSuitChange;
import com.automaticalechoes.equipsuit.api.modInterfcae.player.IPlayerInterface;
import com.automaticalechoes.equipsuit.api.modInterfcae.player.SuitStack;
import com.automaticalechoes.equipsuit.api.modInterfcae.player.SuitStackImpl;
import com.automaticalechoes.equipsuit.api.utils.EquipSuitHelper;
import com.automaticalechoes.equipsuit.common.container.SuitContainer;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;


@Mixin(EntityPlayer.class)
public abstract class PlayerMixin extends EntityLivingBase implements IPlayerInterface {
    @Shadow protected abstract void damageArmor(float p_damageArmor_1_);

    private final SuitContainer suitContainer = new SuitContainer((EntityPlayer) (Object)this);
    private SuitStack suitStack = new SuitStackImpl().defaultSet();
    private int focus;
    private static final DataParameter<Integer> FOCUS = EntityDataManager.createKey(EntityPlayer.class, DataSerializers.VARINT);
    private static final DataParameter<SuitStack> SUIT_STACK = EntityDataManager.createKey(EntityPlayer.class , EquipSuitChange.SUIT_STACK_SERIALIZER);

    public PlayerMixin(World p_i1594_1_) {
        super(p_i1594_1_);
    }

    @Inject(method = {"entityInit"},at = {@At("RETURN")})
    protected void entityInit(CallbackInfo callbackInfo) {
        this.dataManager.register(FOCUS, 0);
        this.dataManager.register(SUIT_STACK,new SuitStackImpl().defaultSet());
    }

    @Inject(method = {"readEntityFromNBT"},at = {@At("RETURN")})
    public void readAdditionalSaveData(NBTTagCompound compoundTag, CallbackInfo callbackInfo){
        if(compoundTag.hasKey("SuitStack")){
            NBTTagCompound suitTag = compoundTag.getCompoundTag("SuitStack");
            suitStack = SuitStack.fromTag(suitTag);
        }
        this.dataManager.set(SUIT_STACK,suitStack);
        NBTTagList containerTag = compoundTag.getTagList("EquipInventory", 10);
        suitContainer.load(containerTag);
        focus = compoundTag.getInteger("Focus");
        this.dataManager.set(FOCUS,focus);
    }

    @Inject(method = {"writeEntityToNBT"},at = {@At("RETURN")})
    public void addAdditionalSaveData(NBTTagCompound p_36265_ ,CallbackInfo callbackInfo) {
        p_36265_.setTag("EquipInventory", this.suitContainer.save(new NBTTagList()));
        p_36265_.setInteger("Focus",this.dataManager.get(FOCUS));
        p_36265_.setTag("SuitStack",suitStack.toTag());
    }

    @Inject(method = {"onDeath"} ,at = {@At( value = "INVOKE" , target = "Lnet/minecraft/entity/player/InventoryPlayer;dropAllItems()V")})
    public void onDeath(CallbackInfo callbackInfo){
        dropEquipment();
    }

    @Override
    public void dropEquipment(){
        this.suitContainer.dropAll((EntityPlayer) (Object)this);
    }

    public SuitStack getSuitStack() {
        return this.world instanceof WorldClient ? this.dataManager.get(SUIT_STACK) : this.suitStack;
    }

    public Integer getFocus() {
        return this.world instanceof WorldClient ? this.dataManager.get(FOCUS) : this.focus;
    }


    public void setFocus(Integer integer) {
        if(EquipSuitHelper.SuitChange((EntityPlayerMP) (Object)this,integer)){
            this.focus = integer;
            this.dataManager.set(FOCUS,focus);
        }
    }

    @Override
    public boolean setSuitSlotNum(int num, String key, int slotNum) {
        boolean b = suitStack.setSuitSlotNum(num, key, slotNum);
        this.dataManager.set(SUIT_STACK,suitStack);
        return b;
    }

    public void updateFocus(){
        if(EquipSuitHelper.SuitChange((EntityPlayerMP) (Object)this)){
            this.focus = (focus+1)%4;
            this.dataManager.set(FOCUS,focus);
        }
    }

    public void restore(EntityPlayer player){
        this.focus = ((IPlayerInterface) player).getFocus();
        this.suitStack = ((IPlayerInterface) player).getSuitStack();
        this.dataManager.set(FOCUS,focus);
        this.dataManager.set(SUIT_STACK,suitStack);
    }

    public SuitContainer getSuitContainer() {
        return suitContainer;
    }


    public void setSuitName(int num,String s){
        this.suitStack.getEquipSuitList().get(num).setName(s);
        this.dataManager.set(SUIT_STACK,suitStack);
    }
}
