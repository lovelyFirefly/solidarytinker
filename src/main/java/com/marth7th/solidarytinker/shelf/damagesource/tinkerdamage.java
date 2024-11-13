package com.marth7th.solidarytinker.shelf.damagesource;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.entity.LivingEntity;

public class tinkerdamage {
    public static DamageSource tinker(LivingEntity entity) {
        return new EntityDamageSource("tinker", entity).bypassArmor().bypassEnchantments().bypassInvul().bypassMagic();
    }

    public static final DamageSource MOMO = new DamageSource("momo");
}
