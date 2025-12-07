package com.marth7th.solidarytinker.util.method;


import com.marth7th.solidarytinker.tools.tinkeritem.SoulGe;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

public class SoulgeHelper {
    public static boolean isSingleMode(IToolStackView tool) {
        return tool.getPersistentData().getBoolean(SoulGe.IS_SINGLE_MODE);
    }
    public static void setSingleMode(IToolStackView tool, boolean enabled) {
        tool.getPersistentData().putBoolean(SoulGe.IS_SINGLE_MODE, enabled);
    }
    public static int getExtraBurnScale(IToolStackView tool) {
        return tool.getPersistentData().getInt(SoulGe.EXTRA_BURN_SCALE);
    }
    public static void setExtraBurnScale(IToolStackView tool, int scale) {
        tool.getPersistentData().putInt(SoulGe.EXTRA_BURN_SCALE, scale);
    }

}
