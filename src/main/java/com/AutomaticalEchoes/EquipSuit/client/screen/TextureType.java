package com.AutomaticalEchoes.EquipSuit.client.screen;

public enum TextureType {
    SLOT(0, 0, 18, 20),
    BLOCKED_SLOT(0, 40, 18, 20),
    INVENTORY_SLOT(0,0,18,18),
    BORDER_VERTICAL(0, 18, 1, 20),
    BORDER_HORIZONTAL_TOP(0, 20, 18, 1),
    BORDER_HORIZONTAL_BOTTOM(0, 60, 18, 1),
    BORDER_CORNER_TOP(0, 20, 1, 1),
    BORDER_CORNER_BOTTOM(0, 60, 1, 1);

    public final int x;
    public final int y;
    public final int w;
    public final int h;

    TextureType(int p_169928_, int p_169929_, int p_169930_, int p_169931_) {
        this.x = p_169928_;
        this.y = p_169929_;
        this.w = p_169930_;
        this.h = p_169931_;
    }
}
