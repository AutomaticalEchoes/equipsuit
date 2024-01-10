package com.automaticalechoes.equipsuit.common.container;

import com.automaticalechoes.equipsuit.client.screen.SuitInventoryScreen;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

import javax.annotation.Nullable;

public class SuitInventoryHandler implements IGuiHandler {
    @Nullable
    @Override
    public Object getServerGuiElement(int i, EntityPlayer entityPlayer, World world, int i1, int i2, int i3) {
        return new SuitInventoryMenu(entityPlayer.inventory);
    }

    @Nullable
    @Override
    public Object getClientGuiElement(int i, EntityPlayer entityPlayer, World world, int i1, int i2, int i3) {
        return new SuitInventoryScreen(new SuitInventoryMenu(entityPlayer.inventory));
    }
}
