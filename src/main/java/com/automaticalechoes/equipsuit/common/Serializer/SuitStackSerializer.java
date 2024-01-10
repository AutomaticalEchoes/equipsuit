package com.automaticalechoes.equipsuit.common.Serializer;

import com.automaticalechoes.equipsuit.api.modInterfcae.player.SuitStack;
import net.minecraft.network.PacketBuffer;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializer;

import java.io.IOException;

public class SuitStackSerializer implements DataSerializer<SuitStack> {
    @Override
    public void write(PacketBuffer p_135025_, SuitStack p_135026_) {
        p_135025_.writeCompoundTag(p_135026_.toTag());
    }

    @Override
    public SuitStack read(PacketBuffer p_135024_) {
        try {
            return SuitStack.fromTag(p_135024_.readCompoundTag());
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public DataParameter<SuitStack> createKey(int i) {
        return new DataParameter<>(i, this);
    }

    @Override
    public SuitStack copyValue(SuitStack p_135023_) {
        return p_135023_;
    }
}
