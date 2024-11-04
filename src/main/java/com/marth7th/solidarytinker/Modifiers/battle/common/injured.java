package com.marth7th.solidarytinker.Modifiers.battle.common;

import com.marth7th.solidarytinker.extend.superclass.BattleModifier;
import com.marth7th.solidarytinker.register.solidarytinkerEffects;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.EntityHitResult;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHealEvent;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModifierNBT;
import slimeknights.tconstruct.library.tools.nbt.NamespacedNBT;

public class injured extends BattleModifier {
    {
        MinecraftForge.EVENT_BUS.addListener(this::LivingHealEvent);
    }

    public void LivingHealEvent(LivingHealEvent event) {
        if(event.getEntity()!=null){
            if (event.getEntity().hasEffect(solidarytinkerEffects.seriously_injured.get())) {
                int level = event.getEntity().getEffect(solidarytinkerEffects.seriously_injured.get()).getAmplifier();
                if (level <= 3) {
                    event.setAmount(event.getAmount() * 1 - 0.3f * level);
                } else
                    event.setCanceled(true);
            }
        }
    }

    @Override
    public float staticdamage(IToolStackView tool, int level, ToolAttackContext context, LivingEntity attacker, LivingEntity livingTarget, float baseDamage, float damage) {
        if(level<=5){
            livingTarget.addEffect(new MobEffectInstance(solidarytinkerEffects.seriously_injured.get(),100,level,true,true));
        }
        else livingTarget.addEffect(new MobEffectInstance(solidarytinkerEffects.seriously_injured.get(),400,level,true,true));
        return damage;
    }

    @Override
    public void arrowhurt(ModifierNBT modifiers, NamespacedNBT persistentData, int level, Projectile projectile, EntityHitResult hit, AbstractArrow arrow, LivingEntity attacker, LivingEntity target) {
        target.addEffect(new MobEffectInstance(solidarytinkerEffects.seriously_injured.get(),100,0,true,true));
    }
}
