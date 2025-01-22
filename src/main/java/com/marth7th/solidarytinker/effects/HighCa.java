package com.marth7th.solidarytinker.effects;

import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;

public class HighCa extends StaticEffect {
    public HighCa() {
        super(MobEffectCategory.BENEFICIAL, 0xffffff);
    }

    @Override
    public void applyEffectTick(LivingEntity living, int amplifier) {
        float pe = (float) 40 / (2 * amplifier);
        if (living.getHealth() < living.getMaxHealth()) {
            if (living.tickCount % pe == 0) {
                living.heal(amplifier);
            }
        } else if (living.getHealth() >= living.getMaxHealth()) {
            if (living.tickCount % pe == 0) {
                living.setAbsorptionAmount(Math.min(6 * amplifier, living.getAbsorptionAmount() + 1));
            }
        }
    }
}
