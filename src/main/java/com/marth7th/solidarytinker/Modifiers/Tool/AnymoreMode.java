package com.marth7th.solidarytinker.Modifiers.Tool;

import com.marth7th.solidarytinker.register.solidarytinkerModifiers;
import com.marth7th.solidarytinker.tools.tinkeritem.SoulGe;
import com.marth7th.solidarytinker.tools.tinkeritem.SoulgeFuel;
import com.marth7th.solidarytinker.util.method.SoulgeHelper;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;
import slimeknights.tconstruct.library.modifiers.ModifierEntry;
import slimeknights.tconstruct.library.modifiers.ModifierHooks;
import slimeknights.tconstruct.library.modifiers.hook.build.ToolStatsModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.combat.MeleeDamageModifierHook;
import slimeknights.tconstruct.library.modifiers.hook.combat.MeleeHitModifierHook;
import slimeknights.tconstruct.library.modifiers.impl.NoLevelsModifier;
import slimeknights.tconstruct.library.module.ModuleHookMap;
import slimeknights.tconstruct.library.tools.capability.fluid.ToolTankHelper;
import slimeknights.tconstruct.library.tools.context.ToolAttackContext;
import slimeknights.tconstruct.library.tools.helper.ToolAttackUtil;
import slimeknights.tconstruct.library.tools.nbt.IToolContext;
import slimeknights.tconstruct.library.tools.nbt.IToolStackView;
import slimeknights.tconstruct.library.tools.stat.ModifierStatsBuilder;
import slimeknights.tconstruct.library.tools.stat.ToolStats;

public class AnymoreMode extends NoLevelsModifier implements MeleeDamageModifierHook, ToolStatsModifierHook , MeleeHitModifierHook {
    @Override
    protected void registerHooks(ModuleHookMap.Builder hookBuilder) {
        hookBuilder.addModule(ToolTankHelper.TANK_HANDLER);
        hookBuilder.addHook(this, ModifierHooks.MELEE_DAMAGE, ModifierHooks.TOOL_STATS,ModifierHooks.MELEE_HIT);
    }

    private static int getTemperatureRiseTick(IToolStackView view) {
        return view.getPersistentData().getInt(SoulGe.TEMPERATURE_RISE_TICK);
    }

    @Override
    public int getPriority() {
        return 8;
    }


    @Override
    public float getMeleeDamage(@NotNull IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float baseDamage, float damage) {
        int tmpTick = getTemperatureRiseTick(tool);

        if (!(context.getLivingTarget() instanceof Mob mob)) return damage;
        if (!(context.getAttacker() instanceof Player player)) return damage;
        var attackSpeed= tool.getStats().get(ToolStats.ATTACK_SPEED);

        if (!SoulgeHelper.isSingleMode(tool)) return damage * attackSpeed;

        var fluidStack = ToolTankHelper.TANK_HELPER.getFluid(tool);
        var fluid = fluidStack.getFluid();

        float fuelDamageScale = 1;
        boolean isMagic = false;
        boolean hasExtraBurnModifier = tool.getModifierLevel(solidarytinkerModifiers.EXTRA_BURN_STATIC_MODIFIER.getId()) > 0;
        boolean hasCorrectFluid = false;
        boolean hasEnoughFluid = false;

        float commonSecond = 1.05f;
        float commonThird = 1.5f;

        int overloadFuelScale = 1;
        float overloadDamageScale = 1;

        if (hasExtraBurnModifier) {
            var array = SoulgeFuel.values();
            for (SoulgeFuel fuel : array) {
                if (SoulgeFuel.LiquidMagic.getFluid().equals(fluid)) {
                    isMagic = true;
                }
                if (fuel.getFluid().equals(fluid)) {
                    fuelDamageScale = fuel.getScale();
                    hasCorrectFluid = true;
                    break;
                }
            }
            if (fluidStack.getAmount() >= 10f) {
                hasEnoughFluid = true;
            }
            if (isMagic) {
                mob.hurt(DamageSource.indirectMagic(player, player), damage * 0.1f);
            }
            if (hasCorrectFluid && hasEnoughFluid) {
                fluidStack.shrink(10 * overloadFuelScale);
                ToolTankHelper.TANK_HELPER.setFluid(tool, fluidStack);
                commonThird = 4f;
                commonSecond = 1.5f;
                if (tool.getModifierLevel(solidarytinkerModifiers.OVERLOAD_BURN_STATIC_MODIFIER.getId()) > 0) {
                    overloadFuelScale = SoulgeHelper.getExtraBurnScale(tool);
                    overloadDamageScale = (float) Math.sqrt(overloadFuelScale);
                }
            }
        }
        if (tmpTick > 24) {
            return damage * commonThird * fuelDamageScale * overloadDamageScale;
        }
        if (tmpTick > 8) {
            return damage * commonSecond * fuelDamageScale * overloadDamageScale;
        }
        return damage * fuelDamageScale * overloadDamageScale;
    }

    @Override
    public void addToolStats(IToolContext context, ModifierEntry modifier, ModifierStatsBuilder builder) {
        ToolTankHelper.CAPACITY_STAT.add(builder, 40000);
    }

    @Override
    public float beforeMeleeHit(IToolStackView tool, ModifierEntry modifier, ToolAttackContext context, float damage, float baseKnockback, float knockback) {
        return 0;
    }
}
