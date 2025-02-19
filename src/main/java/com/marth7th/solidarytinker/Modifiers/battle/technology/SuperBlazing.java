package com.marth7th.solidarytinker.Modifiers.battle.technology;

import com.marth7th.solidarytinker.config.SolidarytinkerConfig;
import com.marth7th.solidarytinker.extend.superclass.BattleModifier;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.ModifierNBT;
import slimeknights.tconstruct.library.tools.nbt.NamespacedNBT;
import slimeknights.tconstruct.tools.TinkerModifiers;

import java.util.List;

public class SuperBlazing extends BattleModifier {
    @Override
    public float beforeMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damage, float baseKnockback, float knockback) {
        LivingEntity attacker = context.getAttacker();
        if (attacker instanceof Player player) {
            double x = attacker.getX();
            double y = attacker.getY();
            double z = attacker.getZ();
            int FireTick = SolidarytinkerConfig.DwarfBlaze.get();
            int range = SolidarytinkerConfig.DwarfSuperBlazing.get();
            List<Mob> mobbbb = player.level.getEntitiesOfClass(Mob.class, new AABB(x + range, y + range, z + range, x - range, y - range, z - range));
            for (Mob targets : mobbbb) {
                if (targets != null && player.isCrouching()) {
                    double a = targets.getX();
                    double c = targets.getZ();
                    targets.setRemainingFireTicks(FireTick);
                    targets.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 1000, 1, true, true));
                    targets.addEffect(new MobEffectInstance(TinkerModifiers.enderferenceEffect.get(), 1000, 1, true, true));
                    {
                        if (Math.abs(Math.abs(a) - Math.abs(x)) > 3 || Math.abs(Math.abs(c) - Math.abs(z)) > 3) {
                            targets.setPos(x, y, z);
                        }
                    }
                }
            }
        }
        return damage;
    }

    @Override
    public boolean onProjectileHitEntity(ModifierNBT modifiers, NamespacedNBT persistentData, ModifierEntry modifier, Projectile projectile, EntityHitResult hit, @Nullable LivingEntity attacker, @Nullable LivingEntity target) {
        if (attacker instanceof Player player) {
            double x = attacker.getX();
            double y = attacker.getY();
            double z = attacker.getZ();
            int FireTick = SolidarytinkerConfig.DwarfBlaze.get();
            int range = SolidarytinkerConfig.DwarfSuperBlazing.get();
            List<Mob> mobbbb = player.level.getEntitiesOfClass(Mob.class, new AABB(x + range, y + range, z + range, x - range, y - range, z - range));
            for (Mob targets : mobbbb) {
                if (targets != null && player.isCrouching()) {
                    double a = targets.getX();
                    double c = targets.getZ();
                    targets.setRemainingFireTicks(FireTick);
                    targets.addEffect(new MobEffectInstance(MobEffects.BLINDNESS, 1000, 1, true, true));
                    targets.addEffect(new MobEffectInstance(TinkerModifiers.enderferenceEffect.get(), 1000, 1, true, true));
                    {
                        if (Math.abs(Math.abs(a) - Math.abs(x)) > 3 || Math.abs(Math.abs(c) - Math.abs(z)) > 3) {
                            targets.setPos(x, y, z);
                        }
                    }
                }
            }
        }
        return false;
    }
}
