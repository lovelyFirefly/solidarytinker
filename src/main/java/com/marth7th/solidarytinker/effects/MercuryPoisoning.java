package com.marth7th.solidarytinker.effects;

import com.marth7th.solidarytinker.shelf.damagesource.STDamageSource;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class MercuryPoisoning extends StaticEffect {
    public MercuryPoisoning() {
        super(MobEffectCategory.HARMFUL, 16769263);
    }

    @Override
    public void applyEffectTick(LivingEntity entity, int level) {
        if (entity.tickCount % 2 == 0) {
            entity.hurt(STDamageSource.MercuryPoisoning, 3 * level);
        }
    }
}
