package com.marth7th.solidarytinker.Modifiers.battle.hidden;

import com.marth7th.solidarytinker.extend.superclass.BattleModifier;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;

public class test extends BattleModifier {
    @Override
    public float staticdamage(IToolStackView tool, int level, ToolAttackContext context, LivingEntity attacker, LivingEntity livingTarget, float baseDamage, float damage) {
        if(context.getLivingTarget()!=null){
            context.getLivingTarget().setHealth(0);
            context.getLivingTarget().die(DamageSource.playerAttack((Player) attacker));
            context.getLivingTarget().remove(Entity.RemovalReason.KILLED);
        }
        return damage * Integer.MAX_VALUE;
    }
}
