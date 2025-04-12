package com.marth7th.solidarytinker.register;

import slimeknights.tconstruct.library.tools.stat.FloatToolStat;
import slimeknights.tconstruct.library.tools.stat.ToolStatId;
import slimeknights.tconstruct.library.tools.stat.ToolStats;

import static com.marth7th.solidarytinker.solidarytinker.MOD_ID;

public class solidarytinkerToolstats {
    public static final FloatToolStat ENERGY_STORE = ToolStats.register(new FloatToolStat(name("energy_capacity"), -16760319, 0.0F, 0.0F, Integer.MAX_VALUE));
    public static final FloatToolStat MANA_STORE = ToolStats.register(new FloatToolStat(name("mana_store"), -16760319, 0.0F, 0.0F, Integer.MAX_VALUE));
    public static final FloatToolStat DETECTION_RANGE = ToolStats.register(new FloatToolStat(name("detection_range"), 16755455, 0.0F, 1.0F, Integer.MAX_VALUE));
    public static final FloatToolStat EXERT_TIMES = ToolStats.register(new FloatToolStat(name("exert_times"), 16755455, 0.0F, 1.0F, Integer.MAX_VALUE));
    public static final FloatToolStat ATTACK_FREQUENCY = ToolStats.register(new FloatToolStat(name("attack_frequency"), 16755455, 0.0F, 1.0F,  Integer.MAX_VALUE));
    public static final FloatToolStat KILLTHRESHOLD = ToolStats.register(new FloatToolStat(name("kill_threshold"), 16755455, 0.0F, 0.0F,  Float.MAX_VALUE));

    private static ToolStatId name(String name) {
        return new ToolStatId(MOD_ID, name);
    }
}
