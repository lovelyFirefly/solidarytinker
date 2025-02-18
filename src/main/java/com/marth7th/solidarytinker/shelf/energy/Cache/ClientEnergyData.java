package com.marth7th.solidarytinker.shelf.energy.Cache;


//用于中转和记录
public class ClientEnergyData {
    private static int PlayerEnergyLevel;

    public static int getPlayerEnergyLevel() {
        return PlayerEnergyLevel;
    }

    public static void setPlayerEnergyLevel(int level) {
        ClientEnergyData.PlayerEnergyLevel = level;
    }
}
