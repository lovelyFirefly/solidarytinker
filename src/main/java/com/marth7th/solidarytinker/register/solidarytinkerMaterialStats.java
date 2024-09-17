package com.marth7th.solidarytinker.register;

import com.c2h6s.etshtinker.tools.stats.fluidChamberMaterialStats;
import com.c2h6s.etshtinker.tools.stats.ionizerMaterialStats;
import slimeknights.tconstruct.library.materials.IMaterialRegistry;
import slimeknights.tconstruct.library.materials.MaterialRegistry;

public class solidarytinkerMaterialStats {
    public solidarytinkerMaterialStats() {
    }
    public static void setup() {
        IMaterialRegistry registry = MaterialRegistry.getInstance();
        registry.registerStatType(ionizerMaterialStats.TYPE, MaterialRegistry.RANGED);
        registry.registerStatType(fluidChamberMaterialStats.TYPE, MaterialRegistry.RANGED);
    }
}

