package com.marth7th.solidarytinker.shelf.Network.Packet;

import com.marth7th.solidarytinker.tools.tinkeritem.MekaTool;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class MekaKeyBoardPacket extends BasePacket {
    //工具速度字段
    private final int ToolLevel;

    public MekaKeyBoardPacket(int ToolLevel) {
        //构造函数声明，这样可以生成包的时候传入信息
        this.ToolLevel = ToolLevel;
    }
    //写入和读取这个包的信息
    public MekaKeyBoardPacket(FriendlyByteBuf buf) {
        ToolLevel = buf.readInt();
    }
    //写入和读取这个包的信息
    @Override
    public void ToByte(FriendlyByteBuf buf) {
        buf.writeInt(ToolLevel);
    }

    //执行处
    @Override
    public void PacketHandler(Supplier<NetworkEvent.Context> supplier, NetworkEvent.Context context, ServerPlayer player, ServerLevel level) {
        if (player.getMainHandItem().getItem() instanceof MekaTool mekaTool) {
            ItemStack stack = player.getMainHandItem();
            //直接读发过来的这个包的信息，来执行对应的实际效果
            if (ToolLevel == 0) {
                mekaTool.setToolLevel(1, stack);
            } else if (ToolLevel == 1) {
                mekaTool.setToolLevel(2, stack);
            } else if (ToolLevel == 2) {
                mekaTool.setToolLevel(3, stack);
            } else if (ToolLevel == 3) {
                mekaTool.setToolLevel(0, stack);
            }
        }
    }
}
