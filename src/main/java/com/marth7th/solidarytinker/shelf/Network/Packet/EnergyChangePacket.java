package com.marth7th.solidarytinker.shelf.Network.Packet;

import com.marth7th.solidarytinker.shelf.energy.Cache.ClientEnergyData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class EnergyChangePacket {
    private final int EnergyLevel;

    public EnergyChangePacket(int energyLevel) {
        this.EnergyLevel = energyLevel;
    }

    public EnergyChangePacket(FriendlyByteBuf buf) {
        this.EnergyLevel = buf.readInt();
    }
    public void ToByte(FriendlyByteBuf buf) {
        buf.writeInt(EnergyLevel);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ClientEnergyData.setPlayerEnergyLevel(EnergyLevel);
        });
        return true;
    }
}
