package com.marth7th.solidarytinker.shelf.damagesource;

import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.entity.LivingEntity;

public class STDamageSource {
    public static DamageSource tinker(LivingEntity entity) {
        return new EntityDamageSource("tinker", entity).bypassArmor().bypassEnchantments().bypassInvul().bypassMagic();
    }

    public static final DamageSource MercuryPoisoning = new DamageSource("mercurypoisoning").bypassArmor().bypassMagic().bypassInvul().bypassEnchantments();
}
