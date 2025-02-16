package com.marth7th.solidarytinker.register;

import com.marth7th.solidarytinker.solidarytinker;
import slimeknights.tconstruct.library.tools.stat.FloatToolStat;
import slimeknights.tconstruct.library.tools.stat.ToolStatId;
import slimeknights.tconstruct.library.tools.stat.ToolStats;

public class solidarytinkerToolstats {
    public static final FloatToolStat ENERGY_STORE = ToolStats.register(new FloatToolStat(name("energy_capacity"), -16760319, 0.0F, 0.0F, Integer.MAX_VALUE));
    public static final FloatToolStat MANA_STORE = ToolStats.register(new FloatToolStat(name("mana_store"), -16760319, 0.0F, 0.0F, Integer.MAX_VALUE));

    public solidarytinkerToolstats() {
    }

    private static ToolStatId name(String name) {
        return new ToolStatId(solidarytinker.MOD_ID, name);
    }
}
