package com.marth7th.solidarytinker.shelf.Network.Packet;

import com.marth7th.solidarytinker.shelf.energy.Cache.BlockAmountData;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class NumberBlockChangePacket {
    private final int CURRENT_BLOCK_AMOUNT;

    public NumberBlockChangePacket(int currentBlockAmount) {
        this.CURRENT_BLOCK_AMOUNT = currentBlockAmount;
    }

    public NumberBlockChangePacket(FriendlyByteBuf buf) {
        this.CURRENT_BLOCK_AMOUNT = buf.readInt();
    }
    public void ToByte(FriendlyByteBuf buf) {
        buf.writeInt(CURRENT_BLOCK_AMOUNT);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            BlockAmountData.setPlayerBlockNumber(CURRENT_BLOCK_AMOUNT);
        });
        return true;
    }
}
