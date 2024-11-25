package com.marth7th.solidarytinker.effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class StaticEffect extends MobEffect {
    protected StaticEffect(MobEffectCategory type, int color) {
        super(type, color);
    }

    public void setAmplifier(LivingEntity living, int amplifier) {
    }

    public boolean aBoolean(int duration, int amplifier) {
        return duration > 0;
    }
}
