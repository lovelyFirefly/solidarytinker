package com.marth7th.solidarytinker.config;


import net.minecraftforge.common.ForgeConfigSpec;

public class SolidarytinkerConfig {
    public static final ForgeConfigSpec.Builder MaterialBuilder = new ForgeConfigSpec.Builder()
            .comment("材料词条部分的配置")//块上面的注解
            .push("材料词条");//块配置名称
    public static final ForgeConfigSpec.Builder ToolBuilder = new ForgeConfigSpec.Builder()
            .comment("工具部分的配置")//块上面的注解
            .push("模组工具");//块配置名称
    //星野锭红温百分比比例
    public static final ForgeConfigSpec.IntValue HoshinoRedTemperature = MaterialBuilder.comment("星野锭红温伤害对应的百分比的值,默认6%")
            .defineInRange("RedTemperatureValue", 6, 0, Integer.MAX_VALUE);
    //星野锭的叠加箭矢上限数量
    public static final ForgeConfigSpec.IntValue HoshinoArrowCount = MaterialBuilder.comment("星野锭增伤箭矢数量的上限,大于这个数量会转而造成单次高伤并且清除箭矢,默认20根")
            .defineInRange("ArrowCount", 20, 0, Integer.MAX_VALUE);
    //星野锭每层箭矢对应的增伤比例
    public static final ForgeConfigSpec.IntValue HoshinoArrowCountDamage = MaterialBuilder.comment("星野锭叠加箭矢对应的增伤,默认8%")
            .defineInRange("EachArrowValue", 145, 0, Integer.MAX_VALUE);
    //星野锭的满层箭矢单次增伤比例，默认145倍
    public static final ForgeConfigSpec.IntValue HoshinoMaxArrowCountDamage = MaterialBuilder.comment("叠满箭矢后单次伤害对应的倍率,默认145倍")
            .defineInRange("MaxArrowCountDamageValue", 145, 0, Integer.MAX_VALUE);
    //星野锭对应护甲的每级基于上级减免伤害的比例
    public static final ForgeConfigSpec.DoubleValue HoshinoArmor = MaterialBuilder.comment("每层护甲会减免掉的伤害比例(基于上一级),默认30%")
            .defineInRange("EachHoshinoArmorValue", 0.3D, 0, 1);
    //墨冰回升点数总增伤的上限
    public static final ForgeConfigSpec.DoubleValue iceMaxValue = MaterialBuilder.comment("墨冰的增伤伤害上限,默认无上限")
            .defineInRange("iceMaxValue", Float.MAX_VALUE, 0, Float.MAX_VALUE);
    //质子化
    public static final ForgeConfigSpec.IntValue protonation = MaterialBuilder.comment("质子化给的护甲和韧性上限,默认2025")
            .defineInRange("protonation", 2025, 0, 100000);
    //重伤
    public static final ForgeConfigSpec.DoubleValue Injured = MaterialBuilder.comment("重伤每级减少目标治疗量的百分比,默认30%")
            .defineInRange("Injured", 0.3D, 0, 1);
    //白矮星燃烧时长
    public static final ForgeConfigSpec.IntValue DwarfBlaze = MaterialBuilder.comment("白矮星物质点燃目标时长,默认2147483647秒")
            .defineInRange("DwarfBlaze", Integer.MAX_VALUE, 0, Integer.MAX_VALUE);
    //白矮星吸附
    public static final ForgeConfigSpec.IntValue DwarfSuperBlazing = MaterialBuilder.comment("白矮星物质吸附目标半径,默认40格")
            .defineInRange("DwarfSuperBlazing", 40, 0, Integer.MAX_VALUE);
    //白矮星暗星
    public static final ForgeConfigSpec.DoubleValue DwarfMaxDamage = MaterialBuilder.comment("白矮星物质吞食生命对应的增伤上限,默认无上限,超出这个值后不再叠加")
            .defineInRange("DwarfMaxDamage", Float.MAX_VALUE, 0, Float.MAX_VALUE);
    //经验钢
    public static final ForgeConfigSpec.IntValue ExperienceSteelCost = MaterialBuilder.comment("经验钢消耗的经验点数，默认10")
            .defineInRange("ExperienceSteelCost", 10, 0, Integer.MAX_VALUE);
    public static final ForgeConfigSpec.DoubleValue ExperienceMeleeSteelDamage = MaterialBuilder.comment("经验钢近战每级对应的增伤，默认0.5")
            .defineInRange("ExperienceMeleeSteelDamage", 0.5, 0, Float.MAX_VALUE);
    public static final ForgeConfigSpec.DoubleValue ExperienceProjectileSteelDamage = MaterialBuilder.comment("经验钢远程每级对应的增伤，默认0.25")
            .defineInRange("ExperienceProjectileSteelDamage", 0.25, 0, Float.MAX_VALUE);
    public static final ForgeConfigSpec.DoubleValue ExperienceSteelArmorBlock = MaterialBuilder.comment("经验钢护甲每级格挡非穿甲的伤害，默认0.08")
            .defineInRange("ExperienceSteelArmorBlock", 0.08, 0, Float.MAX_VALUE);
    public static final ForgeConfigSpec.DoubleValue ExperienceSteelArmorBlockBypassArmor = MaterialBuilder.comment("经验钢护甲每级格挡穿甲的伤害，默认0.04")
            .defineInRange("ExperienceSteelArmorBlockBypassArmor", 0.04, 0, Float.MAX_VALUE);
    //极寒彩钢
    public static final ForgeConfigSpec.DoubleValue FluxArmorBlock = MaterialBuilder.comment("极寒彩钢免伤比例，默认92.5%")
            .defineInRange("FluxArmorBlock", 0.925, 0, 1);
    public static final ForgeConfigSpec.IntValue FluxArmorCost = MaterialBuilder.comment("极寒彩钢每点伤害的电力消耗，默认200")
            .defineInRange("FluxArmorBlock", 200, 0, Integer.MAX_VALUE);
    //大马士革钢是否为强制即死
    public static final ForgeConfigSpec.BooleanValue damascus_steel = MaterialBuilder.comment("大马士革钢是否直接杀死目标,如果否则会造成1000点高额玩家伤害，默认开启")
            .define("should_direct_kill", true);
    //匠魂开拓者工具不同模式模式对应的挖掘速度
    public static final ForgeConfigSpec.IntValue MekaToolSlowSpeed = ToolBuilder.comment("匠魂开拓者工具低速模式对应的挖掘速度,默认5")
            .defineInRange("MekaToolSlowSpeed", 5, 0, Integer.MAX_VALUE);
    public static final ForgeConfigSpec.IntValue MekaToolMediumSpeed = ToolBuilder.comment("匠魂开拓者工具中速模式对应的挖掘速度,默认15")
            .defineInRange("MekaToolMediumSpeed", 15, 0, Integer.MAX_VALUE);
    public static final ForgeConfigSpec.IntValue MekaToolHighSpeed = ToolBuilder.comment("匠魂开拓者工具高速模式对应的挖掘速度,默认40")
            .defineInRange("MekaToolHighSpeed", 40, 0, Integer.MAX_VALUE);
    public static final ForgeConfigSpec.IntValue MekaToolExtremeSpeed = ToolBuilder.comment("匠魂开拓者工具极速模式对应的挖掘速度,默认800")
            .defineInRange("MekaToolExtremeSpeed", 800, 0, Integer.MAX_VALUE);
    //三叉戟相关
    public static final ForgeConfigSpec.IntValue TridentLoyalSpeed = ToolBuilder.comment("三叉戟忠诚收回的速度倍率,默认5")
            .defineInRange("TridentLoyalSpeed", 5, 0, Integer.MAX_VALUE);
    public static final ForgeConfigSpec.BooleanValue TridentbypassMagic = ToolBuilder.comment("三叉戟投掷是否穿透魔抗")
            .define("TridentbypassMagic", true);
    public static final ForgeConfigSpec.DoubleValue TridentDamage = ToolBuilder.comment("三叉戟远程伤害倍率,默认0.8")
            .defineInRange("TridentDamage", 0.8D, 0.01, 1000D);
    public static final ForgeConfigSpec.IntValue TridentRipSpeed = ToolBuilder.comment("三叉戟冲刺的速度倍率,默认1 ")
            .defineInRange("TridentRipSpeed", 1, 0, Integer.MAX_VALUE);

    public static final ForgeConfigSpec Materialspec = MaterialBuilder.pop().build();
    public static final ForgeConfigSpec Toolspec = ToolBuilder.pop().build();
}
