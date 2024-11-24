package com.marth7th.solidarytinker.Modifiers.battle.technology;

import com.marth7th.solidarytinker.extend.energy.FluxStorage;
import com.marth7th.solidarytinker.extend.superclass.FluxBattleModifier;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.projectile.AbstractArrow;
import net.minecraft.world.entity.projectile.Projectile;
import org.jetbrains.annotations.Nullable;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.nbt.NamespacedNBT;
import slimeknights.tconstruct.library.tools.stat.ToolStats;

public class FluxWeapon extends FluxBattleModifier {
    @Override
    public float staticdamage(IToolStackView tool, int level, ToolAttackContext context, LivingEntity attacker, LivingEntity livingTarget, float baseDamage, float damage) {
        float need = tool.getStats().get(ToolStats.ATTACK_DAMAGE);
        if (FluxStorage.getEnergyStored(tool) >= need * 0.4F * 400 * level) {
            FluxStorage.removeEnergy(tool, (int) (400 * need), false, false);
            return damage + need * (0.4f * level);
        }
        return damage;
    }

    @Override
    public void onTinkerArrowShoot(IToolStackView tool, int level, LivingEntity shooter, Projectile projectile, AbstractArrow arrow, NamespacedNBT namespacedNBT, boolean primary) {
        float need = tool.getStats().get(ToolStats.PROJECTILE_DAMAGE);
        if (FluxStorage.getEnergyStored(tool) >= need * 400 * level) {
            FluxStorage.removeEnergy(tool, (int) (400 * need), false, false);
            arrow.setBaseDamage(arrow.getBaseDamage() + need * (0.3f * level));
        }
    }

    @Override
    public int getPriority() {
        return 800;
    }

    @Override
    public int onDamageTool(IToolStackView tool, ModifierEntry modifierEntry, int amount, @Nullable LivingEntity livingEntity) {
        if (FluxStorage.getEnergyStored(tool) > 100 * amount) {
            FluxStorage.removeEnergy(tool, 100 * amount, false, false);
            return 0;
        }
        return amount;
    }
}
