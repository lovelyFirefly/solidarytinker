package com.marth7th.solidarytinker.Modifiers.Both;

import com.marth7th.solidarytinker.extend.superclass.BattleModifier;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.nbt.IToolContext;
import slimeknights.tconstruct.library.tools.stat.ModifierStatsBuilder;
import slimeknights.tconstruct.library.tools.stat.ToolStats;

public class Sophisticated extends BattleModifier {
    @Override
    public void addToolStats(IToolContext context, ModifierEntry modifier, ModifierStatsBuilder builder) {
        ToolStats.DURABILITY.add(builder, 300 * modifier.getLevel());
        if (builder.getStat(ToolStats.ARMOR) > 0) {
            ToolStats.ARMOR.add(builder, modifier.getLevel());
            ToolStats.ARMOR_TOUGHNESS.add(builder, modifier.getLevel());
        } else {
            ToolStats.ATTACK_SPEED.add(builder, 0.08 * modifier.getLevel());
        }
    }
}
