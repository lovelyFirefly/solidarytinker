package com.marth7th.solidarytinker.effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class StaticEffect extends MobEffect {
    protected StaticEffect(MobEffectCategory type, int color) {
        super(type, color);
    }

    @Override
    public void applyEffectTick(LivingEntity living, int amplifier) {
    }

    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return duration > 0;
    }
}
