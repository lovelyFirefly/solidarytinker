package com.marth7th.solidarytinker.tools.Stats;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.marth7th.solidarytinker.register.solidarytinkerToolstats;
import com.marth7th.solidarytinker.solidarytinker;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.NotNull;
import slimeknights.mantle.data.loadable.primitive.FloatLoadable;
import slimeknights.mantle.data.loadable.record.RecordLoadable;
import slimeknights.tconstruct.library.materials.stats.IMaterialStats;
import slimeknights.tconstruct.library.materials.stats.MaterialStatType;
import slimeknights.tconstruct.library.materials.stats.MaterialStatsId;
import slimeknights.tconstruct.library.tools.stat.IToolStat;
import slimeknights.tconstruct.library.tools.stat.ModifierStatsBuilder;

import java.util.List;

import static com.marth7th.solidarytinker.solidarytinker.MOD_ID;

public record CoreBatteryMaterialStats(float attackRange, float targetAmount, float energyCapability) implements IMaterialStats {
    public static final MaterialStatsId ID = new MaterialStatsId(MOD_ID, "core_battery");
    public static final MaterialStatType<CoreBatteryMaterialStats> TYPE= new MaterialStatType<>(ID,new CoreBatteryMaterialStats(1,1,1), RecordLoadable.create(
            FloatLoadable.ANY.defaultField("attack_range", 0.0F, true, CoreBatteryMaterialStats::attackRange),
            FloatLoadable.ANY.defaultField("target_amount", 0.0F, true, CoreBatteryMaterialStats::targetAmount),
            FloatLoadable.ANY.defaultField("energy_capability", 0.0F, true, CoreBatteryMaterialStats::energyCapability),
            CoreBatteryMaterialStats::new));
    private static final String ATTACK_RANGE =IMaterialStats.makeTooltipKey(solidarytinker.getResource("attack_range"));
    private static final String TARGET_AMOUNT=IMaterialStats.makeTooltipKey(solidarytinker.getResource("target_amount"));
    private static final String ENERGY_CAPABILITY=IMaterialStats.makeTooltipKey(solidarytinker.getResource("energy_capability"));
    private static final List<Component> DESCRIPTION = ImmutableList.of(
            IMaterialStats.makeTooltip(solidarytinker.getResource("core_battery.attack_range.description")),
            IMaterialStats.makeTooltip(solidarytinker.getResource("core_battery.target_amount.description")),
            IMaterialStats.makeTooltip(solidarytinker.getResource("core_battery.energy_capability.description")));
    public CoreBatteryMaterialStats(float attackRange, float targetAmount, float energyCapability) {
        this.attackRange=attackRange;
        this.targetAmount=targetAmount;
        this.energyCapability=energyCapability;
    }
    @Override
    public @NotNull MaterialStatType<?> getType() {
        return TYPE;
    }
    @Override
    public @NotNull List<Component> getLocalizedInfo() {
        List<Component> info = Lists.newArrayList();
        info.add(IToolStat.formatColoredBonus(ATTACK_RANGE, this.attackRange));
        info.add(IToolStat.formatColoredBonus(TARGET_AMOUNT, this.targetAmount));
        info.add(IToolStat.formatColoredBonus(ENERGY_CAPABILITY, this.energyCapability));
        return info;
    }
    public float getAttackRange() {
        return this.attackRange;
    }
    public float getTargetAmount(){
        return this.targetAmount;
    }
    public float getEnergyCapability(){
        return this.energyCapability;
    }
    @Override
    public @NotNull List<Component> getLocalizedDescriptions() {
        return DESCRIPTION;
    }

    @Override
    public @NotNull MaterialStatsId getIdentifier() {
        return ID;
    }

    @Override
    public void apply(@NotNull ModifierStatsBuilder builder, float scale) {
        solidarytinkerToolstats.DETECTION_RANGE.update(builder, this.attackRange);
        solidarytinkerToolstats.TARGET_AMOUNT.update(builder, this.targetAmount);
        solidarytinkerToolstats.ENERGY_CAPACITY.update(builder, this.energyCapability);
    }
}
