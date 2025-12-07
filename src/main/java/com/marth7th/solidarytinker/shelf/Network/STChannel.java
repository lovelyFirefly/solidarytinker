package com.marth7th.solidarytinker.shelf.Network;

import com.marth7th.solidarytinker.shelf.Network.Packet.*;
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
        //mekatool
        net.messageBuilder(MekaKeyBoardPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(MekaKeyBoardPacket::new)
                .encoder(MekaKeyBoardPacket::ToByte)
                .consumerMainThread(MekaKeyBoardPacket::handle)
                .add();
        //魂戈
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
        net.messageBuilder(NumberBlockChangePacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(NumberBlockChangePacket::new)
                .encoder(NumberBlockChangePacket::ToByte)
                .consumerMainThread(NumberBlockChangePacket::handle)
                .add();
        net.messageBuilder(StagnationUpdatePacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(StagnationUpdatePacket::new)
                .encoder(StagnationUpdatePacket::ToByte)
                .consumerMainThread(StagnationUpdatePacket::handle)
                .add();
        net.messageBuilder(RunItemActivationPacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(RunItemActivationPacket::new)
                .encoder(RunItemActivationPacket::ToByte)
                .consumerMainThread(RunItemActivationPacket::handle)
                .add();
        net.messageBuilder(HaloUpdatePacket.class, id(), NetworkDirection.PLAY_TO_CLIENT)
                .decoder(HaloUpdatePacket::new)
                .encoder(HaloUpdatePacket::toByte)
                .consumerMainThread(HaloUpdatePacket::handle)
                .add();
        net.messageBuilder(SoulgeConfigPacket.class, id(), NetworkDirection.PLAY_TO_SERVER)
                .decoder(SoulgeConfigPacket::new)
                .encoder(SoulgeConfigPacket::toBytes)
                .consumerMainThread(SoulgeConfigPacket::handle)
                .add();
        INSTANCE = net;
    }

    public static <MSG> void SendToServer(MSG msg) {
        INSTANCE.sendToServer(msg);
    }

    public static <MSG> void SendToPlayer(MSG msg, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.PLAYER.with(() -> player), msg);
    }

    public static <MSG> void sendToClient(MSG msg) {
        INSTANCE.send(PacketDistributor.ALL.noArg(), msg);
    }

    public static <MSG> void sendToTrackingAndSelf(MSG msg, ServerPlayer player) {
        INSTANCE.send(PacketDistributor.TRACKING_ENTITY_AND_SELF.with(() -> player), msg);
    }

    public STChannel() {
    }
}
