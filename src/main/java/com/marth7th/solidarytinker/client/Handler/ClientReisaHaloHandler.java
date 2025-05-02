package com.marth7th.solidarytinker.client.Handler;

import net.minecraft.client.Minecraft;
import net.minecraft.world.entity.player.Player;

public class ClientReisaHaloHandler {
    private static boolean hasHalo=false;
    public static void setHasHalo(boolean status){
        hasHalo=status;
    }
    public static boolean shouldRenderHalo(Player player){
        return hasHalo&&player== Minecraft.getInstance().player;
    }
}
