package com.AutomaticalEchoes.EquipSuit.api.modInterfcae.equipsuit;

import com.AutomaticalEchoes.EquipSuit.api.modInterfcae.baseSlot.BaseSlot;
import com.AutomaticalEchoes.EquipSuit.api.modInterfcae.baseSlot.EquipSlot;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import net.minecraft.nbt.CompoundTag;

import java.util.HashMap;

public interface EquipSuit {
    int num();

    HashMap<String, BaseSlot> left();

    HashMap<String, BaseSlot> right();

    void setLeft(HashMap<String, BaseSlot> map);

    void setRight(HashMap<String, BaseSlot> map);

    void setName(String s);

    String getName();

    default void Build(){
        setName(EquipSuitTemplate.NAME[num()]);
        EquipSuitTemplate.BASE_SUIT_MAP.forEach((s, slot) -> left().put(s, new EquipSlot(slot.ContainerType(), slot.getSlotNum() + num() * EquipSuitTemplate.Size)));
        EquipSuitTemplate.BASE_INVENTORY_MAP.forEach((s, slot) -> right().put(s, new EquipSlot(slot.ContainerType(), slot.getSlotNum())));
    }

    default CompoundTag Save(CompoundTag tag) {
        CompoundTag equipSuitTag = new CompoundTag();
        Gson gson = new Gson();
        String left = gson.toJson(left());
        String right = gson.toJson(right());
        equipSuitTag.putString("left",left);
        equipSuitTag.putString("right",right);
        equipSuitTag.putString("name",getName());
        tag.put("equip_suit",equipSuitTag);
        return tag;
    }

    default void Read(CompoundTag tag){
        CompoundTag equipSuitTag = tag.getCompound("equip_suit");
        String left = equipSuitTag.getString("left");
        String right = equipSuitTag.getString("right");
        Gson gson = new Gson();
        setLeft(gson.fromJson(left,new TypeToken<HashMap<String, EquipSlot>>(){}.getType()));
        setRight(gson.fromJson(right,new TypeToken<HashMap<String, EquipSlot>>(){}.getType()));
        setName(equipSuitTag.getString("name"));
    }


}

