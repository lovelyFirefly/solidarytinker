package com.marth7th.solidarytinker.Modifiers.battle.biomancy;

import com.marth7th.solidarytinker.extend.superclass.BattleModifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.EntityHitResult;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModifierNBT;
import slimeknights.tconstruct.library.tools.nbt.NamespacedNBT;

public class Swell extends BattleModifier {
    @Override
    public float staticdamage(IToolStackView tool, int level, ToolAttackContext context, LivingEntity attacker, LivingEntity livingTarget, float baseDamage, float damage) {
        float a = attacker.getAbsorptionAmount();
        if (a > 0 && a < attacker.getMaxHealth() * 2) {
            return damage + a * 0.5f * level;
        } else if (a > attacker.getMaxHealth() * 2) {
            return damage + a * 1f * level;

        }
        return damage;
    }

    @Override
    public void arrowhurt(ModifierNBT modifiers, NamespacedNBT persistentData, int level, Projectile projectile, EntityHitResult hit, AbstractArrow arrow, LivingEntity attacker, LivingEntity target) {
        float a = attacker.getAbsorptionAmount();
        if (a > 0 && a < attacker.getMaxHealth() * 2) {
            arrow.setBaseDamage(arrow.getBaseDamage() + a * 0.5f * level);
        } else if (a > attacker.getMaxHealth() * 2) {
            arrow.setBaseDamage(arrow.getBaseDamage() + a * 1 * level);
        }
    }
}
