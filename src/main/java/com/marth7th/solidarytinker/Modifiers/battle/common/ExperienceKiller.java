package com.marth7th.solidarytinker.Modifiers.battle.common;

import com.marth7th.solidarytinker.config.SolidarytinkerConfig;
import com.marth7th.solidarytinker.extend.superclass.BattleModifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.EntityHitResult;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModifierNBT;
import slimeknights.tconstruct.library.tools.nbt.NamespacedNBT;

public class ExperienceKiller extends BattleModifier {
    @Override
    public float staticdamage(IToolStackView tool, int level, ToolAttackContext context, LivingEntity attacker, LivingEntity livingTarget, float baseDamage, float damage) {
        if (attacker instanceof Player player) {
            int cost = SolidarytinkerConfig.ExperienceSteelCost.get();
            float Damage = SolidarytinkerConfig.ExperienceMeleeSteelDamage.get().floatValue();
            int a = player.experienceLevel;
            player.giveExperiencePoints(-cost);
            return damage + a * Damage * level;
        }
        return damage;
    }

    @Override
    public void arrowhurt(ModifierNBT modifiers, NamespacedNBT persistentData, int level, Projectile projectile, EntityHitResult hit, AbstractArrow arrow, LivingEntity attacker, LivingEntity target) {
        if (attacker instanceof Player player) {
            int cost = SolidarytinkerConfig.ExperienceSteelCost.get();
            float Damage = SolidarytinkerConfig.ExperienceProjectileSteelDamage.get().floatValue();
            int a = player.experienceLevel;
            player.giveExperiencePoints(-cost);
            arrow.setBaseDamage(arrow.getBaseDamage() + a * Damage * level);
        }
    }
}
