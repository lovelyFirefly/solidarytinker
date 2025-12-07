package com.marth7th.solidarytinker.shelf.Network.Packet;

import com.marth7th.solidarytinker.util.method.SoulgeHelper;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ToolStack;

import java.util.function.Supplier;

public class SoulgeConfigPacket {
    private final boolean isSingleMode;
    private final int scale;

    public SoulgeConfigPacket(boolean isSingleMode,int scale) {
        this.isSingleMode = isSingleMode;
        this.scale = scale;
    }

    public SoulgeConfigPacket(FriendlyByteBuf buf) {
        this.isSingleMode = buf.readBoolean();
        this.scale = buf.readInt();
    }

    public void toBytes(FriendlyByteBuf buf) {
        buf.writeBoolean(isSingleMode);
        buf.writeInt(scale);
    }

    public void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            ServerPlayer sender = context.getSender();
            if (sender != null) {
                IToolStackView tool = ToolStack.from(sender.getMainHandItem());
                SoulgeHelper.setSingleMode(tool, isSingleMode);
                SoulgeHelper.setExtraBurnScale(tool, scale);
            }
        });
        context.setPacketHandled(true);
    }
}
