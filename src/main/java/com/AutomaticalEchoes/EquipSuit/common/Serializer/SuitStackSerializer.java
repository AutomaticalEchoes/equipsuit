package com.AutomaticalEchoes.EquipSuit.common.Serializer;

import com.AutomaticalEchoes.EquipSuit.api.modInterfcae.player.SuitStack;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.syncher.EntityDataSerializer;

public class SuitStackSerializer implements EntityDataSerializer<SuitStack> {
    @Override
    public void write(FriendlyByteBuf p_135025_, SuitStack p_135026_) {
        p_135025_.writeNbt(p_135026_.toTag());
    }

    @Override
    public SuitStack read(FriendlyByteBuf p_135024_) {
        return SuitStack.fromTag(p_135024_.readNbt());
    }

    @Override
    public SuitStack copy(SuitStack p_135023_) {
        return p_135023_;
    }
}
