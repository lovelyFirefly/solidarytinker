package com.marth7th.solidarytinker.Modifiers.battle.mekanism;

import com.marth7th.solidarytinker.extend.superclass.BattleModifier;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.EntityHitResult;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModifierNBT;
import slimeknights.tconstruct.library.tools.nbt.NamespacedNBT;

public class extremelycold extends BattleModifier {
    @Override
    public boolean havenolevel() {return true;}

    @Override
    public void afterMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damageDealt) {
        if(context.getLivingTarget()!=null){
            LivingEntity target=context.getLivingTarget();
            int a = RANDOM.nextInt(10);
            if(target.hasEffect(MobEffects.MOVEMENT_SLOWDOWN)){
                int timeleft=target.getEffect(MobEffects.MOVEMENT_SLOWDOWN).getDuration();
                int EffectLevel=target.getEffect(MobEffects.MOVEMENT_SLOWDOWN).getAmplifier();
                if(timeleft>=0){
                    target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,EffectLevel,timeleft+10));
                }
            }
            else if(a==1){
                target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,0,10));
            }
        }
    }

    @Override
    public void arrowhurt(ModifierNBT modifiers, NamespacedNBT persistentData, int level, Projectile projectile, AbstractArrow arrow, EntityHitResult hit, LivingEntity attacker, LivingEntity target) {
        if(target!=null){
            int a = RANDOM.nextInt(10);
            if(target.hasEffect(MobEffects.MOVEMENT_SLOWDOWN)){
                int timeleft=target.getEffect(MobEffects.MOVEMENT_SLOWDOWN).getDuration();
                int EffectLevel=target.getEffect(MobEffects.MOVEMENT_SLOWDOWN).getAmplifier();
                if(timeleft>=0){
                    target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,EffectLevel,timeleft+10));
                }
            }
            else if(a==1){
                target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN,0,10));
            }
        }
    }
}