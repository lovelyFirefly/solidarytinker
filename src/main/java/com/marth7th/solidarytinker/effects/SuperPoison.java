package com.marth7th.solidarytinker.effects;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobType;
import net.minecraft.world.entity.player.Player;

public class SuperPoison extends StaticEffect {
    public SuperPoison() {
        super(MobEffectCategory.HARMFUL, 13257983);
    }

    @Override
    public void applyEffectTick(LivingEntity living, int amplifier) {
        if (living.tickCount % 5 == 0) {
            var entity = living.getLastHurtByMob();
            if (entity instanceof Player player) {
                if (living.getMobType() == MobType.UNDEAD) {
                    living.hurt(DamageSource.playerAttack(player).bypassArmor(), 2);
                } else
                    living.hurt(DamageSource.playerAttack(player), 2);
            }
        }
    }
}
