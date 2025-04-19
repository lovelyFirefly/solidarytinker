package com.marth7th.solidarytinker.effects;

import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class StaticEffect extends MobEffect {
    public StaticEffect(MobEffectCategory pCategory, int pColor) {
        super(pCategory, pColor);
    }
    @Override
    public boolean isDurationEffectTick(int duration, int amplifier) {
        return duration > 0;
    }
}
