package com.marth7th.solidarytinker.shelf.Network;

import com.marth7th.solidarytinker.shelf.Network.Packet.EnergyChangePacket;
import com.marth7th.solidarytinker.shelf.Network.Packet.MekaKeyBoardPacket;
import com.marth7th.solidarytinker.shelf.Network.Packet.SoulGeAttackPacket;
import com.marth7th.solidarytinker.solidarytinker;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.simple.SimpleChannel;

public class STChannel {
    public static int packetID = 0;
    private static SimpleChannel INSTANCE;

    public static int id() {
        return packetID++;
    }

    public static void register() {
        SimpleChannel net = NetworkRegistry.ChannelBuilder
                .named(new ResourceLocation(solidarytinker.MOD_ID, "messages"))
                .networkProtocolVersion(() -> "1.0")
                .clientAcceptedVersions(s -> true)
                .serverAcceptedVersions(s -> true)
                .simpleChannel();
        net.messageBuilder(MekaKeyBoardPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(MekaKeyBoardPacket::new)
                .encoder(MekaKeyBoardPacket::ToByte)
                .consumerMainThread(MekaKeyBoardPacket::handle)
                .add();
        net.messageBuilder(SoulGeAttackPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(SoulGeAttackPacket::new)
                .encoder(SoulGeAttackPacket::ToByte)
                .consumerMainThread(SoulGeAttackPacket::handle)
                .add();
        //能量
        net.messageBuilder(EnergyChangePacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(EnergyChangePacket::new)
                .encoder(EnergyChangePacket::ToByte)
                .consumerMainThread(EnergyChangePacket::handle)
                .add();
        INSTANCE = net;
    }

    public static <MSG> void SendToServer(MSG msg) {
        INSTANCE.sendToServer(msg);
    }

    public static <MSG> void SendToPlayer(MSG msg, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), msg);
    }

    public STChannel() {
    }
}
