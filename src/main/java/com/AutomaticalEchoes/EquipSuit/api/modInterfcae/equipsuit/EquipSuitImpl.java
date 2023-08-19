package com.AutomaticalEchoes.EquipSuit.api.modInterfcae.equipsuit;

import com.AutomaticalEchoes.EquipSuit.api.modInterfcae.baseSlot.BaseSlot;

import java.util.HashMap;

public class EquipSuitImpl implements EquipSuit {
    private final int number;
    private HashMap<String, BaseSlot> left = new HashMap<>();
    private HashMap<String, BaseSlot> right = new HashMap<>();

    public EquipSuitImpl(int number) {
        this.number = number;
    }

    @Override
    public int num() {
        return number;
    }

    @Override
    public HashMap<String, BaseSlot> left() {
        return left;
    }

    @Override
    public HashMap<String, BaseSlot> right() {
        return right;
    }

    @Override
    public void setLeft(HashMap<String, BaseSlot> map) {
        this.left = map;
    }

    @Override
    public void setRight(HashMap<String, BaseSlot> map) {
        this.right = map;
    }
}
