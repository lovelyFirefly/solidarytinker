package com.marth7th.solidarytinker.Modifiers.battle.technology;

import com.marth7th.solidarytinker.extend.superclass.BattleModifier;
import com.marth7th.solidarytinker.register.solidarytinkerEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.EnderMan;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.EntityHitResult;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModifierNBT;
import slimeknights.tconstruct.library.tools.nbt.NamespacedNBT;

public class MercuryPoisoning extends BattleModifier {
    @Override
    public void afterMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damageDealt) {
        if (context.getLivingTarget() instanceof EnderMan enderMan && context.getPlayerAttacker() != null) {
            enderMan.forceAddEffect(new MobEffectInstance(solidarytinkerEffects.mercurypoisoning.get(), 300 * modifier.getLevel(), 3 * modifier.getLevel() - 1), context.getPlayerAttacker());
        } else if (context.getLivingTarget() != null && context.getPlayerAttacker() != null) {
            context.getLivingTarget().forceAddEffect(new MobEffectInstance(solidarytinkerEffects.mercurypoisoning.get(), 100 * modifier.getLevel(), modifier.getLevel() - 1), context.getPlayerAttacker());
        }
    }

    @Override
    public void arrowhurt(ModifierNBT modifiers, NamespacedNBT persistentData, int level, Projectile projectile, EntityHitResult hit, AbstractArrow arrow, LivingEntity attacker, LivingEntity target) {
        if (target instanceof EnderMan enderMan) {
            enderMan.forceAddEffect(new MobEffectInstance(solidarytinkerEffects.mercurypoisoning.get(), 300 * level, 3 * level - 1), attacker);
        } else if (target != null) {
            target.forceAddEffect(new MobEffectInstance(solidarytinkerEffects.mercurypoisoning.get(), 100 * level, level - 1), attacker);
        }
    }
}
