package com.marth7th.solidarytinker.tools.Stats;


import com.c2h6s.etshtinker.tools.stats.PlasmaGeneratorMaterialStats;
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

public record SoulGeHeartMaterialStats(float detection_range, float exert_times, float attack_frequency) implements IMaterialStats {
    public static final MaterialStatsId ID = new MaterialStatsId("solidarytinker", "soulge_heart");
    public static final MaterialStatType<SoulGeHeartMaterialStats> TYPE= new MaterialStatType<>(ID,new SoulGeHeartMaterialStats(1,1,1), RecordLoadable.create(
            FloatLoadable.ANY.defaultField("detection_range", 0.0F, true, SoulGeHeartMaterialStats::detection_range),
            FloatLoadable.ANY.defaultField("exert_times", 0.0F, true, SoulGeHeartMaterialStats::exert_times),
            FloatLoadable.ANY.defaultField("attack_frequency", 0.0F, true, SoulGeHeartMaterialStats::attack_frequency),
            SoulGeHeartMaterialStats::new));
    private static final String DETECTION_RANGE =IMaterialStats.makeTooltipKey(solidarytinker.getResource("detection_range"));
    private static final String EXERT_TIMES=IMaterialStats.makeTooltipKey(solidarytinker.getResource("exert_times"));
    private static final String ATTACK_FREQUENCY=IMaterialStats.makeTooltipKey(solidarytinker.getResource("attack_frequency"));
    private static final List<Component> DESCRIPTION = ImmutableList.of(
            IMaterialStats.makeTooltip(solidarytinker.getResource("soulge_heart.detection_range.description")),
            IMaterialStats.makeTooltip(solidarytinker.getResource("soulge_heart.exert_times.description")),
            IMaterialStats.makeTooltip(solidarytinker.getResource("soulge_heart.attack_frequency.description")));
    public SoulGeHeartMaterialStats(float detection_range, float exert_times, float attack_frequency) {
        this.detection_range=detection_range;
        this.exert_times=exert_times;
        this.attack_frequency=attack_frequency;
    }

    @Override
    public @NotNull MaterialStatType<?> getType() {
        return TYPE;
    }
    @Override
    public @NotNull List<Component> getLocalizedInfo() {
        List<Component> info = Lists.newArrayList();
        info.add(IToolStat.formatColoredBonus(DETECTION_RANGE, this.detection_range));
        info.add(IToolStat.formatColoredBonus(EXERT_TIMES, this.exert_times));
        info.add(IToolStat.formatColoredBonus(ATTACK_FREQUENCY, this.attack_frequency));
        return info;
    }
    public float getDetectionRange() {
        return this.detection_range;
    }
    public float getExertTimes(){
        return this.exert_times;
    }
    public float getAttackFrequency(){
        return this.attack_frequency;
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
        solidarytinkerToolstats.DETECTION_RANGE.update(builder, this.detection_range);
        solidarytinkerToolstats.EXERT_TIMES.update(builder, this.exert_times);
        solidarytinkerToolstats.ATTACK_FREQUENCY.update(builder, this.attack_frequency);
    }
}
