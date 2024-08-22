package com.marth7th.solidarytinker.Modifiers.battle.common;

import com.marth7th.solidarytinker.extend.superclass.BattleModifier;
import com.marth7th.solidarytinker.register.solidarytinkerEffects;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectInstance;
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

public class neverend extends BattleModifier {
    @Override
    public void afterMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damageDealt) {
        if(context.getLivingTarget() != null){
        if(context.getAttacker().hasEffect(solidarytinkerEffects.bloodanger.get())){
            int effectlevel = (context.getAttacker().getEffect(solidarytinkerEffects.bloodanger.get())).getAmplifier();
            context.getTarget().hurt(DamageSource.playerAttack((Player) context.getAttacker()),context.getLivingTarget().getMaxHealth() * 0.15f * effectlevel);
        }
        }
    }

    @Override
    public void arrowhurt(ModifierNBT modifiers, NamespacedNBT persistentData, int level, Projectile projectile, AbstractArrow arrow, EntityHitResult hit, LivingEntity attacker, LivingEntity target) {
        if(attacker.hasEffect(solidarytinkerEffects.bloodanger.get())){
            int effectlevel = (attacker.getEffect(solidarytinkerEffects.bloodanger.get())).getAmplifier();
            target.invulnerableTime=0;
            target.hurt(DamageSource.playerAttack((Player) attacker),target.getMaxHealth() * 0.15f * effectlevel);
            arrow.setBaseDamage(arrow.getBaseDamage());
        }
    }
    @Override
    public void onInventoryTick(IToolStackView tool, ModifierEntry modifier, Level world, LivingEntity entity, int index, boolean isSelected, boolean isCorrectSlot, ItemStack stack) {
        if(entity.getHealth()<=entity.getMaxHealth() * 0.3f&&entity.hasEffect(solidarytinkerEffects.bloodanger.get())){
            entity.addEffect(new MobEffectInstance(solidarytinkerEffects.bloodanger.get(),200,0));
            }
        }
    }
