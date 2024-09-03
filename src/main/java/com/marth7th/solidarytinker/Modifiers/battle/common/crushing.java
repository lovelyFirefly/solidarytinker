package com.marth7th.solidarytinker.Modifiers.battle.common;

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

public class crushing extends BattleModifier {
    @Override
    public void afterMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damageDealt) {
        LivingEntity entity =context.getAttacker();
        LivingEntity target = context.getLivingTarget();
        if(entity instanceof Player player){
            if (target != null) {
                if(player.getMaxHealth()>target.getMaxHealth()){
                    target.kill();
                    target.die(DamageSource.playerAttack(player));
                    target.setHealth(0);
                }
            }
        }
    }

    @Override
    public void arrowhurt(ModifierNBT modifiers, NamespacedNBT persistentData, int level, Projectile projectile, AbstractArrow arrow, EntityHitResult hit, LivingEntity attacker, LivingEntity target) {
        if(attacker instanceof Player player){
            if(player.getMaxHealth()>target.getHealth()){
                target.invulnerableTime=0;
                target.kill();
                target.die(DamageSource.playerAttack(player));
                target.setHealth(0);
            }
        }
    }
}
