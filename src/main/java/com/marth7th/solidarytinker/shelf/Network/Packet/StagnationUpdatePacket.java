package com.marth7th.solidarytinker.shelf.Network.Packet;

import com.marth7th.solidarytinker.client.Hud.HoshinoStagnationHUD;
import net.minecraft.client.Minecraft;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public class StagnationUpdatePacket {

    private final int STAGNATION_WAIT_TIME1;
    private final int STAGNATION_WAIT_TIME2;
    private final int STAGNATION_WAIT_TIME3;
    private final int STAGNATION_WAIT_TIME4;
    private final int STAGNATION_REMAIN_TIME;

    public StagnationUpdatePacket(int StagnationWaitTime1,int StagnationWaitTime2,int StagnationWaitTime3, int StagnationWaitTime4, int stagnationRemainTime) {
        this.STAGNATION_WAIT_TIME1 = StagnationWaitTime1;
        this.STAGNATION_WAIT_TIME2 = StagnationWaitTime2;
        this.STAGNATION_WAIT_TIME3 = StagnationWaitTime3;
        this.STAGNATION_WAIT_TIME4 = StagnationWaitTime4;
        this.STAGNATION_REMAIN_TIME = stagnationRemainTime;
    }

    public StagnationUpdatePacket(FriendlyByteBuf buf) {
        this.STAGNATION_WAIT_TIME1 = buf.readInt();
        this.STAGNATION_WAIT_TIME2 = buf.readInt();
        this.STAGNATION_WAIT_TIME3 = buf.readInt();
        this.STAGNATION_WAIT_TIME4 = buf.readInt();
        this.STAGNATION_REMAIN_TIME = buf.readInt();
    }

    public void ToByte(FriendlyByteBuf buf) {
        buf.writeInt(STAGNATION_WAIT_TIME1);
        buf.writeInt(STAGNATION_WAIT_TIME2);
        buf.writeInt(STAGNATION_WAIT_TIME3);
        buf.writeInt(STAGNATION_WAIT_TIME4);
        buf.writeInt(STAGNATION_REMAIN_TIME);
    }

    public boolean handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        context.enqueueWork(() -> {
            HoshinoStagnationHUD.setStagnationWaitTime1(STAGNATION_WAIT_TIME1);
            HoshinoStagnationHUD.setStagnationWaitTime2(STAGNATION_WAIT_TIME2);
            HoshinoStagnationHUD.setStagnationWaitTime3(STAGNATION_WAIT_TIME3);
            HoshinoStagnationHUD.setStagnationWaitTime4(STAGNATION_WAIT_TIME4);
            HoshinoStagnationHUD.setRemainingStagnationTime(STAGNATION_REMAIN_TIME);
        });
        return true;
    }
}
