package com.equipsuit.equip_suit_v1.common.registry;

import com.equipsuit.equip_suit_v1.EquipSuitChange;
import com.equipsuit.equip_suit_v1.api.modInterfcae.player.SuitStack;
import com.equipsuit.equip_suit_v1.api.modInterfcae.player.SuitStackImpl;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntArrayTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.syncher.EntityDataSerializer;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;


public class EntityDataRegister {
    public static final DeferredRegister<EntityDataSerializer<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.Keys.ENTITY_DATA_SERIALIZERS, EquipSuitChange.MODID);
    public static final RegistryObject<EntityDataSerializer<SuitStack>> SUIT_STACK = REGISTRY.register("player_suit_stack", () -> new EntityDataSerializer<>() {
        @Override
        public void write(FriendlyByteBuf p_135025_, SuitStack p_135026_) {
            ListTag listTag=new ListTag();
            for(int i=0;i<4;i++){
                int[] ints = p_135026_.getSuitArrayList().get(i);
                listTag.add(new IntArrayTag(ints));
            }
            CompoundTag compoundTag=new CompoundTag();
            compoundTag.put("SuitStack",listTag);
            p_135025_.writeNbt(compoundTag);
        }

        @Override
        public SuitStack read(FriendlyByteBuf p_135024_) {
            CompoundTag compoundTag = p_135024_.readNbt();
            SuitStackImpl suitStack=new SuitStackImpl();
            if(compoundTag.contains("SuitStack")){
                ListTag suitTag = compoundTag.getList("SuitStack",10);
                for(int i=0;i<4;i++){
                    int[] intArray = suitTag.getIntArray(i);
                    suitStack.setSuitSlotNums(i,intArray);
                }
            }
            return suitStack;
        }

        @Override
        public SuitStack copy(SuitStack p_135023_) {
            SuitStackImpl suitStack=new SuitStackImpl();
            suitStack.setSuitArrayList(p_135023_.getSuitArrayList());
            return suitStack;
        }
    });
}
