package com.marth7th.solidarytinker.Modifiers.battle.common;

import com.marth7th.solidarytinker.config.SolidarytinkerConfig;
import com.marth7th.solidarytinker.extend.superclass.BattleModifier;
import com.marth7th.solidarytinker.register.solidarytinkerEffects;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModifierNBT;
import slimeknights.tconstruct.library.tools.nbt.NamespacedNBT;

public class NeverEnd extends BattleModifier {
    @Override
    public void afterMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damageDealt) {
        if (context.getLivingTarget() != null) {
            if (context.getAttacker().hasEffect(solidarytinkerEffects.bloodanger.get())) {
                int effectlevel = (context.getAttacker().getEffect(solidarytinkerEffects.bloodanger.get())).getAmplifier();
                context.getTarget().hurt(DamageSource.playerAttack((Player) context.getAttacker()).bypassMagic(), context.getLivingTarget().getMaxHealth() *( SolidarytinkerConfig.HoshinoRedTemperature.get()/100f) * (effectlevel + 1));
            }
        }
    }

    @Override
    public void arrowhurt(ModifierNBT modifiers, NamespacedNBT persistentData, int level, Projectile projectile, EntityHitResult hit, AbstractArrow arrow, LivingEntity attacker, LivingEntity target) {
        if (attacker.hasEffect(solidarytinkerEffects.bloodanger.get())) {
            int effectlevel = (attacker.getEffect(solidarytinkerEffects.bloodanger.get())).getAmplifier();
            int count = attacker.getArrowCount();
            int MaxCount = SolidarytinkerConfig.HoshinoArrowCount.get();
            int HoshinoMaxArrowCountDamage = SolidarytinkerConfig.HoshinoMaxArrowCountDamage.get();
            int HoshinoArrowCountDamage = SolidarytinkerConfig.HoshinoArrowCountDamage.get();
            target.invulnerableTime = 0;
            target.hurt(DamageSource.playerAttack((Player) attacker).bypassMagic(), target.getMaxHealth() * (SolidarytinkerConfig.HoshinoRedTemperature.get() / 100f) * (effectlevel + 1));
            if (count >= 0 && count < MaxCount) {
                arrow.setBaseDamage(arrow.getBaseDamage() * (1 + ((HoshinoArrowCountDamage / 100f) * count)));
            } else if (count >= MaxCount) {
                arrow.setBaseDamage(arrow.getBaseDamage() * HoshinoMaxArrowCountDamage);
                attacker.setArrowCount(0);
                attacker.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 600, 1, true, true));
            }
        }
    }

    @Override
    public float staticdamage(IToolStackView tool, int level, ToolAttackContext context, LivingEntity attacker, LivingEntity livingTarget, float baseDamage, float damage) {
        int MaxCount = SolidarytinkerConfig.HoshinoArrowCount.get();
        int HoshinoMaxArrowCountDamage = SolidarytinkerConfig.HoshinoMaxArrowCountDamage.get();
        int HoshinoArrowCountDamage = SolidarytinkerConfig.HoshinoArrowCountDamage.get();
        if (attacker.getArrowCount() >= 0 && attacker.getArrowCount() < MaxCount) {
            return damage * (1f + (HoshinoArrowCountDamage / 100F * attacker.getArrowCount()));
        } else if (attacker.getArrowCount() >= MaxCount) {
            attacker.setArrowCount(0);
            attacker.addEffect(new MobEffectInstance(MobEffects.DAMAGE_RESISTANCE, 600, 1, true, true));
            return damage * HoshinoMaxArrowCountDamage;
        }
        return damage;
    }

    @Override
    public void onInventoryTick(IToolStackView tool, ModifierEntry modifier, Level world, LivingEntity entity, int index, boolean isSelected, boolean isCorrectSlot, ItemStack stack) {
        if (entity.getHealth() <= entity.getMaxHealth() * 0.3f && entity.hasEffect(solidarytinkerEffects.bloodanger.get())) {
            entity.addEffect(new MobEffectInstance(solidarytinkerEffects.bloodanger.get(), 200, 0));
        }
    }
}
