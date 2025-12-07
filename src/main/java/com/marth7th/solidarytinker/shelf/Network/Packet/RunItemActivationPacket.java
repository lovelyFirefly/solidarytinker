package com.marth7th.solidarytinker.shelf.Network.Packet;

import com.marth7th.solidarytinker.shelf.energy.Cache.BlockAmountData;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class RunItemActivationPacket {
    private final int ARMOR_INDEX;

    public RunItemActivationPacket(int index) {
        this.ARMOR_INDEX = index;
    }

    public RunItemActivationPacket(FriendlyByteBuf buf) {
        this.ARMOR_INDEX = buf.readInt();
    }
    public void ToByte(FriendlyByteBuf buf) {
        buf.writeInt(ARMOR_INDEX);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            var localplayer=Minecraft.getInstance().player;
            if(localplayer==null)return;
            var itemstack= localplayer.getItemBySlot(EquipmentSlot.values()[ARMOR_INDEX]);
            Minecraft.getInstance().gameRenderer.displayItemActivation(itemstack);
        });
        return true;
    }
}
