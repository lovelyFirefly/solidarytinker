package com.marth7th.solidarytinker.register;

import com.marth7th.solidarytinker.Modifiers.armor.*;
import com.marth7th.solidarytinker.Modifiers.battle.common.*;
import com.marth7th.solidarytinker.Modifiers.battle.hidden.aaa;
import com.marth7th.solidarytinker.Modifiers.battle.hidden.variety;
import com.marth7th.solidarytinker.Modifiers.battle.mekanism.darkstar;
import com.marth7th.solidarytinker.Modifiers.battle.mekanism.removeradition;
import com.marth7th.solidarytinker.Modifiers.battle.mekanism.superblazing;
import slimeknights.tconstruct.library.modifiers.util.ModifierDeferredRegister;
import slimeknights.tconstruct.library.modifiers.util.StaticModifier;

import static com.marth7th.solidarytinker.solidarytinker.MOD_ID;

public class solidarytinkerModifiers {
    /**
     * 词条的注册部分
     * 图省事所有直接把等号外部分写在一起了（
     */
    public static ModifierDeferredRegister MODIFIERS = ModifierDeferredRegister.create(MOD_ID);
    public static final StaticModifier<aaa> AAA_STATIC_MODIFIER=MODIFIERS.register("aaa", aaa::new);   //测试
    public static final StaticModifier<wenguanger>WENGUANGER_STATIC_MODIFIER=MODIFIERS.register("wenguanger", wenguanger::new);   //问故
    public static final StaticModifier<protonation>PROTONATION_STATIC_MODIFIER=MODIFIERS.register("protonation", protonation::new);   //测试
    public static final StaticModifier<darkstar>DARKSTAR_STATIC_MODIFIER=MODIFIERS.register("darkstar", darkstar::new);   //暗星
    public static final StaticModifier<superblazing>SUPERBLAZING_STATIC_MODIFIER=MODIFIERS.register("superblazing", superblazing::new);   //炽焰
    public static final StaticModifier<collapse>COLLAPSE_STATIC_MODIFIER=MODIFIERS.register("collapse", collapse::new);   //坍缩
    public static final StaticModifier<neverend>NEVEREND_STATIC_MODIFIER=MODIFIERS.register("neverend", neverend::new);   //永不休止
    public static final StaticModifier<reliable>RELIABLE_STATIC_MODIFIER=MODIFIERS.register("reliable", reliable::new);   //可靠
    public static final StaticModifier<tacticsattack>TACTICSATTACK_STATIC_MODIFIER=MODIFIERS.register("tacticsattack", tacticsattack::new);//战术进攻
    public static final StaticModifier<sandstrom>SANDSTROM_STATIC_MODIFIER=MODIFIERS.register("sandstrom", sandstrom::new);   //沙海守望
    public static final StaticModifier<absolutejustice>ABSOLUTEJUSTICE_STATIC_MODIFIER=MODIFIERS.register("absolutejustice", absolutejustice::new);   //绝对正义
    public static final StaticModifier<tacticsprotect>TACTICSPROTECT_STATIC_MODIFIER=MODIFIERS.register("tacticsprotect", tacticsprotect::new);   //战术防御
    public static final StaticModifier<ancientocean>ANCIENTOCEAN_STATIC_MODIFIER=MODIFIERS.register("ancientocean", ancientocean::new);   //古海孑遗
    public static final StaticModifier<deepoceanblessing>DEEPOCEANBLESSING_STATIC_MODIFIER=MODIFIERS.register("deepoceanblessing", deepoceanblessing::new);   //深海恩惠
    public static final StaticModifier<deepoceanecho>DEEPOCEANECHO_STATIC_MODIFIER=MODIFIERS.register("deepoceanecho", deepoceanecho::new);   //深海回声
    public static final StaticModifier<deepoceanprotect>DEEPOCEANPROTECT_STATIC_MODIFIER=MODIFIERS.register("deepoceanprotect", deepoceanprotect::new);   //深海加护
    public static final StaticModifier<variety>VARIETY_STATIC_MODIFIER=MODIFIERS.register("variety", variety::new);   //千变
    public static final StaticModifier<thefood>THEFOOD_STATIC_MODIFIER=MODIFIERS.register("thefood", thefood::new);   //厨神
    public static final StaticModifier<removeradition>RADITIONPROTECT_STATIC_MODIFIER=MODIFIERS.register("removeradition", removeradition::new);   //变辐侠
    public static final StaticModifier<clean>CLEAN_STATIC_MODIFIER=MODIFIERS.register("clean", clean::new);   //净化
    public static final StaticModifier<uncannyvalley>UNCANNYVALLEY_STATIC_MODIFIER=MODIFIERS.register("uncannyvalley", uncannyvalley::new);   //恐怖谷效应
    public static final StaticModifier<anglefox>ANGLEFOX_STATIC_MODIFIER=MODIFIERS.register("anglefox", anglefox::new);   //天使狐
    public static final StaticModifier<voraciousfox>VORACIOUSFOX_STATIC_MODIFIER=MODIFIERS.register("voraciousfox", voraciousfox::new);   //贪吃狐
    public static final StaticModifier<experiencekiller>EXPRIENCEKILLER_STATIC_MODIFIER=MODIFIERS.register("experiencekiller", experiencekiller::new);   //经验杀手
    public static final StaticModifier<experienceprotect>EXPERIENCEPROTECT_STATIC_MODIFIER=MODIFIERS.register("experienceprotect", experienceprotect::new);   //经验防护

}
