package com.marth7th.solidarytinker.tools.tinkeritem;

import com.marth7th.solidarytinker.config.SolidarytinkerConfig;

public enum MekaToolSpeedLevel {
    SLOW(SolidarytinkerConfig.MekaToolSlowSpeed.get()),
    MEDIUM(SolidarytinkerConfig.MekaToolMediumSpeed.get()),
    HIGH(SolidarytinkerConfig.MekaToolHighSpeed.get()),
    EXTREME(SolidarytinkerConfig.MekaToolExtremeSpeed.get());
    private final int speed;

    MekaToolSpeedLevel(int speed) {
        this.speed = speed;
    }

    public int getSpeed() {
        return speed;
    }
}
