package com.AutomaticalEchoes.EquipSuit.common.registry;

import com.AutomaticalEchoes.EquipSuit.EquipSuitChange;
import com.AutomaticalEchoes.EquipSuit.common.container.SuitInventoryMenu;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ContainerRegister {
    public static final DeferredRegister<MenuType<?>> REGISTRY = DeferredRegister.create(ForgeRegistries.MENU_TYPES, EquipSuitChange.MODID);
    public static final RegistryObject<MenuType<SuitInventoryMenu>> SUIT_INVENTORY_MENU =REGISTRY.register("suit_inventory_menu",() -> new MenuType<>(SuitInventoryMenu::Create));


}
