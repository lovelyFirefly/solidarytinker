package com.marth7th.solidarytinker.Modifiers.battle.common;

import com.marth7th.solidarytinker.extend.superclass.BattleModifier;
import com.marth7th.solidarytinker.util.method.ModifierLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.nbt.IToolContext;
import slimeknights.tconstruct.library.tools.stat.ModifierStatsBuilder;
import slimeknights.tconstruct.library.tools.stat.ToolStats;

public class Swift extends BattleModifier {
    @Override
    public void addToolStats(IToolContext context, ModifierEntry modifier, ModifierStatsBuilder builder) {
        ToolStats.ATTACK_SPEED.add(builder, 0.7 * modifier.getLevel());
        ToolStats.ATTACK_DAMAGE.multiply(builder, 0.3);
    }

    @Override
    public void LivingHurtEvent(LivingHurtEvent event) {
        if (event.getEntity() instanceof Player player) {
            if (ModifierLevel.HandsHaveModifierlevel(player, this.getId())) {
                double X = player.getX();
                double Z = player.getZ();
                float a = RANDOM.nextInt(20) - 10;
                float b = RANDOM.nextInt(20) - 10;
                player.moveTo(X + a, player.getY(), Z + b);
            }
        }
    }
}
