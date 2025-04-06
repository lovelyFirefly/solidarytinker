package com.marth7th.solidarytinker.register;

import com.marth7th.solidarytinker.tools.Stats.SoulGeHeartMaterialStats;
import slimeknights.tconstruct.library.materials.IMaterialRegistry;
import slimeknights.tconstruct.library.materials.MaterialRegistry;

public class solidarytinkerMaterialStat {
    public solidarytinkerMaterialStat(){}

    public static void setup() {
        IMaterialRegistry registry = MaterialRegistry.getInstance();
        registry.registerStatType(SoulGeHeartMaterialStats.TYPE, MaterialRegistry.MELEE_HARVEST);
    }
}
