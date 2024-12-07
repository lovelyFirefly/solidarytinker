package com.marth7th.solidarytinker.effects;

import com.marth7th.solidarytinker.shelf.damagesource.STDamageSource;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.monster.EnderMan;

public class MercuryPoisoning extends StaticEffect {
    public MercuryPoisoning() {
        super(MobEffectCategory.HARMFUL, 16769263);
    }

    @Override
    public void applyEffectTick(LivingEntity living, int amplifier) {
        if (living instanceof EnderMan enderMan && living.tickCount % 10 == 0) {
            enderMan.hurt(STDamageSource.MercuryPoisoning, (3 * amplifier + 1) + (enderMan.getMaxHealth() * 0.01F * amplifier));
        } else if (living.tickCount % 10 == 0) {
            living.hurt(STDamageSource.MercuryPoisoning, 3 * amplifier + 1);
        }
    }
}
