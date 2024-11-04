package com.marth7th.solidarytinker.Modifiers.battle.mekanism;

import com.marth7th.solidarytinker.extend.superclass.BattleModifier;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.EntityHitResult;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModifierNBT;
import slimeknights.tconstruct.library.tools.nbt.NamespacedNBT;

public class brittle extends BattleModifier {
    @Override
    public float staticdamage(IToolStackView tool, int level, ToolAttackContext context, LivingEntity attacker, LivingEntity livingTarget, float baseDamage, float damage) {
        if(livingTarget!=null){
            if(livingTarget.hasEffect(MobEffects.MOVEMENT_SLOWDOWN)){
                int timeleft=livingTarget.getEffect(MobEffects.MOVEMENT_SLOWDOWN).getDuration();
                if(timeleft<400){
                    return damage * 0.8f;
                }
                else if(timeleft>400&&timeleft<800){
                    return damage *1.2f;
                }
                else if(timeleft>1200){
                    return damage *1.2f+livingTarget.getMaxHealth()*0.12f;
                }
            }
        }
        return damage;
    }

    @Override
    public void arrowhurt(ModifierNBT modifiers, NamespacedNBT persistentData, int level, Projectile projectile, EntityHitResult hit,  AbstractArrow arrow,LivingEntity attacker, LivingEntity target) {
        if(target!=null){
            if(target.hasEffect(MobEffects.MOVEMENT_SLOWDOWN)){
                int timeleft=target.getEffect(MobEffects.MOVEMENT_SLOWDOWN).getDuration();
                if(timeleft<400){
                    arrow.setBaseDamage(arrow.getBaseDamage()*0.8f);
                }
                else if(timeleft>400&&timeleft<800){
                    arrow.setBaseDamage(arrow.getBaseDamage()*1.2f);
                }
                else if(timeleft>1200){
                    arrow.setBaseDamage(arrow.getBaseDamage()*1.2f+target.getMaxHealth()*0.12f);
                }
            }
        }
    }
}
