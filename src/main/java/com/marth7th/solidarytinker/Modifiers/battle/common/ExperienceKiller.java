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
            float Damagescale = SolidarytinkerConfig.ExperienceMeleeSteelDamage.get().floatValue();
            int exp = player.experienceLevel;
            float add= Math.min(exp * Damagescale * level,1000*level);
            player.giveExperiencePoints(-cost);
            return damage + add;
        }
        return damage;
    }

    @Override
    public void arrowhurt(ModifierNBT modifiers, NamespacedNBT persistentData, int level, Projectile projectile, EntityHitResult hit, AbstractArrow arrow, LivingEntity attacker, LivingEntity target) {
        if (attacker instanceof Player player) {
            int cost = SolidarytinkerConfig.ExperienceSteelCost.get();
            float damageScale = SolidarytinkerConfig.ExperienceProjectileSteelDamage.get().floatValue();
            int exp = player.experienceLevel;
            float add= Math.min(exp * damageScale * level,1000*level);
            player.giveExperiencePoints(-cost);
            arrow.setBaseDamage(arrow.getBaseDamage() + add);
        }
    }
}
