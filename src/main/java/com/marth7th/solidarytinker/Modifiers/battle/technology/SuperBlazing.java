package com.marth7th.solidarytinker.Modifiers.battle.technology;

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

import java.util.List;

public class SuperBlazing extends BattleModifier {
    @Override
    public void afterMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damageDealt) {
        LivingEntity attacker = context.getAttacker();
        if (attacker instanceof Player player) {
            double x = attacker.getX();
            double y = attacker.getY();
            double z = attacker.getZ();
            List<Mob> mobbbb = player.level.getEntitiesOfClass(Mob.class, new AABB(x + 40, y + 40, z + 40, x - 40, y - 40, z - 40));
            for (Mob targets : mobbbb) {
                if (targets != null) {
                    double a = targets.getX();
                    double b = targets.getY();
                    double c = targets.getZ();
                    targets.setRemainingFireTicks(2147483647);
                    targets.addEffect(new MobEffectInstance(MobEffects.BLINDNESS,10,1,true,true));
                    {
                        if (Math.abs(Math.abs(a) - Math.abs(x)) > 5 || Math.abs(Math.abs(c) - Math.abs(z)) > 5) {
                            targets.setPos(x, y, z);
                        }
                    }
                }
            }
        }
    }


    @Override
    public boolean onProjectileHitEntity(ModifierNBT modifiers, NamespacedNBT persistentData, ModifierEntry modifier, Projectile projectile, EntityHitResult hit, @Nullable LivingEntity attacker, @Nullable LivingEntity target) {
        if (attacker instanceof Player player) {
            double x = attacker.getX();
            double y = attacker.getY();
            double z = attacker.getZ();
            List<Mob> mobbbb = player.level.getEntitiesOfClass(Mob.class, new AABB(x + 40, y + 40, z + 40, x - 40, y - 40, z - 40));
            for (Mob targets : mobbbb) {
                if (targets != null) {
                    double a = targets.getX();
                    double c = targets.getZ();
                    targets.setRemainingFireTicks(2147483647);
                    targets.addEffect(new MobEffectInstance(MobEffects.BLINDNESS,10,1,true,true));
                    {
                        if (Math.abs(Math.abs(a) - Math.abs(x)) > 5 || Math.abs(Math.abs(c) - Math.abs(z)) > 5) {
                            targets.setPos(x, y, z);
                        }
                    }
                }
            }
        }
        return false;
    }
}
