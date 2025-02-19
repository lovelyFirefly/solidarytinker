package com.marth7th.solidarytinker.Modifiers.battle.common;

import com.marth7th.solidarytinker.config.SolidarytinkerConfig;
import com.marth7th.solidarytinker.extend.superclass.BattleModifier;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.EntityHitResult;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModifierNBT;
import slimeknights.tconstruct.library.tools.nbt.NamespacedNBT;

public class Crushing extends BattleModifier {
    public boolean havenolevel() {
        return true;
    }

    @Override
    public void afterMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damageDealt) {
        LivingEntity entity = context.getAttacker();
        LivingEntity target = context.getLivingTarget();
        if (entity instanceof Player player) {
            if (target != null && player.getMaxHealth() > target.getHealth()) {
                if (SolidarytinkerConfig.damascus_steel.get()) {
                    target.kill();
                    target.die(DamageSource.playerAttack(player));
                } else target.hurt(DamageSource.playerAttack(player), 1000);
            }
        }
    }

    @Override
    public void arrowhurt(ModifierNBT modifiers, NamespacedNBT persistentData, int level, Projectile projectile, EntityHitResult hit, AbstractArrow arrow, LivingEntity attacker, LivingEntity target) {
        if (attacker instanceof Player player) {
            if (attacker.getMaxHealth() > target.getHealth()) {
                target.invulnerableTime = 0;
                if (SolidarytinkerConfig.damascus_steel.get()) {
                    target.die(DamageSource.playerAttack(player));
                    target.kill();
                } else target.hurt(DamageSource.playerAttack(player), 1000);
            }
        }
    }
}
