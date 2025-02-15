package com.marth7th.solidarytinker.shelf.Network.Packet;

import com.marth7th.solidarytinker.tools.tinkeritem.MekaTool;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class MekaKeyBoardPacket extends BasePacket {
    private final int ToolLevel;

    public MekaKeyBoardPacket(int ToolLevel) {
        this.ToolLevel = ToolLevel;
    }

    public MekaKeyBoardPacket(FriendlyByteBuf buf) {
        ToolLevel = buf.readInt();
    }

    @Override
    public void ToByte(FriendlyByteBuf buf) {
        buf.writeInt(ToolLevel);
    }

    @Override
    public void PacketHandler(Supplier<NetworkEvent.Context> supplier, NetworkEvent.Context context, ServerPlayer player, ServerLevel level) {
        if (player.getMainHandItem().getItem() instanceof MekaTool mekaTool) {
            if (ToolLevel == 0) {
                mekaTool.setToolLevel(1);
            } else if (ToolLevel == 1) {
                mekaTool.setToolLevel(2);
            } else if (ToolLevel == 2) {
                mekaTool.setToolLevel(3);
            } else if (ToolLevel == 3) {
                mekaTool.setToolLevel(0);
            }
        }
    }
}
