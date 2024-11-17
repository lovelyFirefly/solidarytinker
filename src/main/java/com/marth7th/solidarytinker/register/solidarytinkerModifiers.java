package com.marth7th.solidarytinker.register;

import com.marth7th.solidarytinker.Modifiers.armor.*;
import com.marth7th.solidarytinker.Modifiers.battle.biomancy.Corrode;
import com.marth7th.solidarytinker.Modifiers.battle.biomancy.Swell;
import com.marth7th.solidarytinker.Modifiers.battle.common.*;
import com.marth7th.solidarytinker.Modifiers.battle.hidden.Release;
import com.marth7th.solidarytinker.Modifiers.battle.hidden.nos;
import com.marth7th.solidarytinker.Modifiers.battle.hidden.test;
import com.marth7th.solidarytinker.Modifiers.battle.hidden.variety;
import com.marth7th.solidarytinker.Modifiers.battle.technology.*;
import slimeknights.tconstruct.library.modifiers.util.ModifierDeferredRegister;
import slimeknights.tconstruct.library.modifiers.util.StaticModifier;

import static com.marth7th.solidarytinker.solidarytinker.MOD_ID;

public class solidarytinkerModifiers {
    /**
     * 词条的注册部分
     * 图省事所有直接把等号外部分写在一起了（
     */
    public static ModifierDeferredRegister MODIFIERS = ModifierDeferredRegister.create(MOD_ID);
    public static final StaticModifier<nos> NOS_STATIC_MODIFIER=MODIFIERS.register("nos", nos::new);   //测试
    public static final StaticModifier<Protonation> PROTONATION_STATIC_MODIFIER = MODIFIERS.register("protonation", Protonation::new);   //测试
    public static final StaticModifier<DarkStar> DARKSTAR_STATIC_MODIFIER = MODIFIERS.register("darkstar", DarkStar::new);   //暗星
    public static final StaticModifier<SuperBlazing> SUPERBLAZING_STATIC_MODIFIER = MODIFIERS.register("superblazing", SuperBlazing::new);   //炽焰
    public static final StaticModifier<Collapse> COLLAPSE_STATIC_MODIFIER = MODIFIERS.register("collapse", Collapse::new);   //坍缩
    public static final StaticModifier<NeverEnd> NEVEREND_STATIC_MODIFIER = MODIFIERS.register("neverend", NeverEnd::new);   //永不休止
    public static final StaticModifier<Reliable> RELIABLE_STATIC_MODIFIER = MODIFIERS.register("reliable", Reliable::new);   //可靠
    public static final StaticModifier<TacticsAttack> TACTICSATTACK_STATIC_MODIFIER = MODIFIERS.register("tacticsattack", TacticsAttack::new);//战术进攻
    public static final StaticModifier<SandStrom> SANDSTROM_STATIC_MODIFIER = MODIFIERS.register("sandstrom", SandStrom::new);   //沙海守望
    public static final StaticModifier<AbsoluteJustice> ABSOLUTEJUSTICE_STATIC_MODIFIER = MODIFIERS.register("absolutejustice", AbsoluteJustice::new);   //绝对正义
    public static final StaticModifier<TacticsProtect> TACTICSPROTECT_STATIC_MODIFIER = MODIFIERS.register("tacticsprotect", TacticsProtect::new);   //战术防御
    public static final StaticModifier<Ancientocean> ANCIENTOCEAN_STATIC_MODIFIER = MODIFIERS.register("ancientocean", Ancientocean::new);   //古海孑遗
    public static final StaticModifier<DeepOceanBlessing> DEEPOCEANBLESSING_STATIC_MODIFIER = MODIFIERS.register("deepoceanblessing", DeepOceanBlessing::new);   //深海恩惠
    public static final StaticModifier<DeepOceanEcho> DEEPOCEANECHO_STATIC_MODIFIER = MODIFIERS.register("deepoceanecho", DeepOceanEcho::new);   //深海回声
    public static final StaticModifier<DeepOceanProtect> DEEPOCEANPROTECT_STATIC_MODIFIER = MODIFIERS.register("deepoceanprotect", DeepOceanProtect::new);   //深海加护
    public static final StaticModifier<variety>VARIETY_STATIC_MODIFIER=MODIFIERS.register("variety", variety::new);   //千变
    public static final StaticModifier<TheFood> THEFOOD_STATIC_MODIFIER = MODIFIERS.register("thefood", TheFood::new);   //厨神
    public static final StaticModifier<InHeart> INHEART_STATIC_MODIFIER = MODIFIERS.register("inheart", InHeart::new);   //本心
    public static final StaticModifier<RemoveRadition> REMOVERADITION_STATIC_MODIFIER = MODIFIERS.register("removeradition", RemoveRadition::new);   //变辐侠
    public static final StaticModifier<Clean> CLEAN_STATIC_MODIFIER = MODIFIERS.register("clean", Clean::new);   //净化
    public static final StaticModifier<UncannyValley> UNCANNYVALLEY_STATIC_MODIFIER = MODIFIERS.register("uncannyvalley", UncannyValley::new);   //恐怖谷效应
    public static final StaticModifier<AngelFox> ANGELFOX_STATIC_MODIFIER = MODIFIERS.register("angelfox", AngelFox::new);   //天使狐
    public static final StaticModifier<VoraciousFox> VORACIOUSFOX_STATIC_MODIFIER = MODIFIERS.register("voraciousfox", VoraciousFox::new);   //贪吃狐
    public static final StaticModifier<ExperienceKiller> EXPRIENCEKILLER_STATIC_MODIFIER = MODIFIERS.register("experiencekiller", ExperienceKiller::new);   //经验杀手
    public static final StaticModifier<ExperienceProtect> EXPERIENCEPROTECT_STATIC_MODIFIER = MODIFIERS.register("experienceprotect", ExperienceProtect::new);   //经验防护
    public static final StaticModifier<Crushing> CRUSHING_STATIC_MODIFIER = MODIFIERS.register("crushing", Crushing::new);   //碾压
    public static final StaticModifier<Ignore> IGNORE_STATIC_MODIFIER = MODIFIERS.register("ignore", Ignore::new);   //无视
    public static final StaticModifier<Swell> SWELL_STATIC_MODIFIER = MODIFIERS.register("swell", Swell::new);   //膨胀
    public static final StaticModifier<Corrode> CORRODE_STATIC_MODIFIER = MODIFIERS.register("corrode", Corrode::new);   //侵蚀
    public static final StaticModifier<Elasticity> ELASTICITY_STATIC_MODIFIER = MODIFIERS.register("elasticity", Elasticity::new);   //弹性
    public static final StaticModifier<Release> RELEASE_STATIC_MODIFIER = MODIFIERS.register("release", Release::new);   //弹性
    public static final StaticModifier<SeaBless> SEABLESS_STATIC_MODIFIER = MODIFIERS.register("seabless", SeaBless::new);   //海神赐福
    public static final StaticModifier<Riptide> RIPTIDE_STATIC_MODIFIER = MODIFIERS.register("riptide", Riptide::new);   //激流
    public static final StaticModifier<crcs> CRCS_STATIC_MODIFIER=MODIFIERS.register("crcs", crcs::new);   //风雨无阻
    public static final StaticModifier<Injured> INJURED_STATIC_MODIFIER = MODIFIERS.register("injured", Injured::new);   //重伤
    public static final StaticModifier<ExtremelyCold> EXTREMELYCOLD_STATIC_MODIFIER = MODIFIERS.register("extremelycold", ExtremelyCold::new);   //极寒
    public static final StaticModifier<Brittle> BRITTLE_STATIC_MODIFIER = MODIFIERS.register("brittle", Brittle::new);   //脆质化
    public static final StaticModifier<ColdFetters> COLDFETTERS_STATIC_MODIFIER = MODIFIERS.register("coldfetters", ColdFetters::new);   //寒冰血脉
    public static final StaticModifier<Loyal> LOYAL_STATIC_MODIFIER = MODIFIERS.register("loyal", Loyal::new);   //忠诚
    public static final StaticModifier<Lightningbolt> LIGHTNINGBOLT_STATIC_MODIFIER = MODIFIERS.register("lightningbolt", Lightningbolt::new);   //引雷
    public static final StaticModifier<test> TEST_STATIC_MODIFIER = MODIFIERS.register("test", test::new);   //测试

}
