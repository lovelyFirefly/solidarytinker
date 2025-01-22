package com.marth7th.solidarytinker.Modifiers.battle.Uncategorized;

import com.marth7th.solidarytinker.extend.superclass.BattleModifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.EntityHitResult;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModifierNBT;
import slimeknights.tconstruct.library.tools.nbt.NamespacedNBT;

public class Pilgrimage extends BattleModifier {
    @Override
    public float staticdamage(IToolStackView tool, int level, ToolAttackContext context, LivingEntity attacker, LivingEntity livingTarget, float baseDamage, float damage) {
        double a = Math.abs(attacker.getX());
        double b = Math.abs(attacker.getZ());
        double range = Math.sqrt(a * a + b * b) / 40;
        if (!attacker.level.dimension().equals(Level.END)) {
            return damage * (float) range;
        } else return damage * 2F;
    }

    @Override
    public void arrowhurt(ModifierNBT modifiers, NamespacedNBT persistentData, int level, Projectile projectile, EntityHitResult hit, AbstractArrow arrow, LivingEntity attacker, LivingEntity target) {
        double a = Math.abs(attacker.getX());
        double b = Math.abs(attacker.getZ());
        double range = Math.sqrt(a * a + b * b) / 40;
        if (!attacker.level.dimension().equals(Level.END)) {
            arrow.setBaseDamage(arrow.getBaseDamage() * (float) range);
        } else arrow.setBaseDamage(arrow.getBaseDamage() * 2F);
    }
}
