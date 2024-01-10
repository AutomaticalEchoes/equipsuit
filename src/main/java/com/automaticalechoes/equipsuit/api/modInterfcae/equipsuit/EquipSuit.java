package com.automaticalechoes.equipsuit.api.modInterfcae.equipsuit;

import com.automaticalechoes.equipsuit.api.modInterfcae.baseSlot.BaseSlot;
import com.automaticalechoes.equipsuit.api.modInterfcae.baseSlot.EquipSlot;
import com.automaticalechoes.equipsuit.api.utils.EquipSuitTemplate;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import net.minecraft.nbt.NBTTagCompound;

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
        EquipSuitTemplate.BASE_SUIT_CONTAINER_MAP.forEach((s, slot) -> left().put(s, new EquipSlot(slot.ContainerType(), slot.getSlotNum() + num() * EquipSuitTemplate.Size)));
        EquipSuitTemplate.BASE_EQUIP_MAP.forEach((s, slot) -> right().put(s, new EquipSlot(slot.ContainerType(), slot.getSlotNum())));
    }

    default NBTTagCompound Save(NBTTagCompound tag) {
        NBTTagCompound equipSuitTag = new NBTTagCompound();
        Gson gson = new Gson();
        String left = gson.toJson(left());
        String right = gson.toJson(right());
        equipSuitTag.setString("left",left);
        equipSuitTag.setString("right",right);
        equipSuitTag.setString("name",getName());
        tag.setTag("equip_suit",equipSuitTag);
        return tag;
    }

    default void Read(NBTTagCompound tag){
        NBTTagCompound equipSuitTag = tag.getCompoundTag("equip_suit");
        String left = equipSuitTag.getString("left");
        String right = equipSuitTag.getString("right");
        Gson gson = new Gson();
        setLeft(gson.fromJson(left,new TypeToken<HashMap<String, EquipSlot>>(){}.getType()));
        setRight(gson.fromJson(right,new TypeToken<HashMap<String, EquipSlot>>(){}.getType()));
        setName(equipSuitTag.getString("name"));
    }


}

