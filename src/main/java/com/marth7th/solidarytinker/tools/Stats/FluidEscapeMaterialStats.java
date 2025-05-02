package com.marth7th.solidarytinker.tools.Stats;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;

import com.marth7th.solidarytinker.solidarytinker;
import com.marth7th.solidarytinker.register.solidarytinkerToolstats;
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

public record FluidEscapeMaterialStats(float base_consumption, float consumption_multiplier, float damage_reduction, float energy_capability) implements IMaterialStats {
    public static final MaterialStatsId ID = new MaterialStatsId(MOD_ID, "fluid_escape");
    public static final MaterialStatType<FluidEscapeMaterialStats> TYPE= new MaterialStatType<>(ID,new FluidEscapeMaterialStats(0,0,0,0), RecordLoadable.create(
            FloatLoadable.ANY.defaultField("base_consumption", 0.0F, true, FluidEscapeMaterialStats::base_consumption),
            FloatLoadable.ANY.defaultField("consumption_multiplier", 0.0F, true, FluidEscapeMaterialStats::consumption_multiplier),
            FloatLoadable.ANY.defaultField("damage_reduction", 0.0F, true, FluidEscapeMaterialStats::damage_reduction),
            FloatLoadable.ANY.defaultField("energy_capability", 0.0F, true, FluidEscapeMaterialStats::energy_capability),
            FluidEscapeMaterialStats::new));
    private static final String BASE_CONSUMPTION =IMaterialStats.makeTooltipKey(solidarytinker.getResource("base_consumption"));
    private static final String CONSUMPTION_MULTIPLIER=IMaterialStats.makeTooltipKey(solidarytinker.getResource("consumption_multiplier"));
    private static final String DAMAGE_REDUCTION=IMaterialStats.makeTooltipKey(solidarytinker.getResource("damage_reduction"));
    private static final String ENERGY_CAPABILITY=IMaterialStats.makeTooltipKey(solidarytinker.getResource("energy_capability"));
    private static final List<Component> DESCRIPTION = ImmutableList.of(
            IMaterialStats.makeTooltip(solidarytinker.getResource("fluid_escape.base_consumption.description")),
            IMaterialStats.makeTooltip(solidarytinker.getResource("fluid_escape.consumption_multiplier.description")),
            IMaterialStats.makeTooltip(solidarytinker.getResource("fluid_escape.damage_reduction.description")),
            IMaterialStats.makeTooltip(solidarytinker.getResource("fluid_escape.energy_capability.description")));
    public FluidEscapeMaterialStats(float base_consumption, float consumption_multiplier, float damage_reduction, float energy_capability) {
        this.base_consumption=base_consumption;
        this.consumption_multiplier=consumption_multiplier;
        this.energy_capability=energy_capability;
        this.damage_reduction=damage_reduction;
    }

    @Override
    public @NotNull MaterialStatType<?> getType() {
        return TYPE;
    }
    @Override
    public @NotNull List<Component> getLocalizedInfo() {
        List<Component> info = Lists.newArrayList();
        info.add(IToolStat.formatColoredBonus(BASE_CONSUMPTION, this.base_consumption));
        info.add(IToolStat.formatColoredBonus(CONSUMPTION_MULTIPLIER, this.consumption_multiplier));
        info.add(IToolStat.formatColoredBonus(DAMAGE_REDUCTION, this.damage_reduction));
        info.add(IToolStat.formatColoredBonus(ENERGY_CAPABILITY, this.energy_capability));
        return info;
    }
    public float getBase_Consumption() {
        return this.base_consumption;
    }
    public float getConsumption_Multiplier(){
        return this.consumption_multiplier;
    }
    public float getDamage_Reduction(){
        return this.damage_reduction;
    }
    public float getenergy_capability(){
        return this.energy_capability;
    }
    @Override
    public @NotNull List<Component> getLocalizedDescriptions() {
        return DESCRIPTION;
    }

    @Override
    public void apply(@NotNull ModifierStatsBuilder builder, float scale) {
        solidarytinkerToolstats.BASE_CONSUMPTION.update(builder, this.base_consumption);
        solidarytinkerToolstats.CONSUMPTION_MULTIPLIER.update(builder, this.consumption_multiplier);
        solidarytinkerToolstats.DAMAGE_REDUCTION.update(builder, this.consumption_multiplier);
        solidarytinkerToolstats.ENERGY_CAPACITY.update(builder,this.energy_capability);
    }
}
