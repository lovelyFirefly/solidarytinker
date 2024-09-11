package com.marth7th.solidarytinker.Modifiers.battle.biomancy;

import com.marth7th.solidarytinker.extend.superclass.BattleModifier;
import net.minecraft.world.entity.LivingEntity;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

public class swell extends BattleModifier {
    @Override
    public float staticdamage(IToolStackView tool, int level, ToolAttackContext context, LivingEntity attacker, LivingEntity livingTarget, float baseDamage, float damage) {
        float a = attacker.getAbsorptionAmount();
        if(a>0&&a<60){
            return damage + a * 0.5f * level;
        }
        else if(a>60){
            return damage + a * 1f * level;
        }
        return damage;
    }
}
