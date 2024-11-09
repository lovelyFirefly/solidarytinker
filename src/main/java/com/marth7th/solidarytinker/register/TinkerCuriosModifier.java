package com.marth7th.solidarytinker.register;

import com.marth7th.solidarytinker.Modifiers.curios.*;
import slimeknights.tconstruct.library.modifiers.util.ModifierDeferredRegister;
import slimeknights.tconstruct.library.modifiers.util.StaticModifier;

import static com.marth7th.solidarytinker.solidarytinker.MOD_ID;

public class TinkerCuriosModifier {
    public static ModifierDeferredRegister MODIFIERS = ModifierDeferredRegister.create(MOD_ID);
    public static final StaticModifier<angleprotect> ANGELPROTECT_STATIC_MODIFIER = MODIFIERS.register("angelprotect", angleprotect::new);   //天使狐
    public static final StaticModifier<deepoceanchew> DEEPOCEANCHEW_STATIC_MODIFIER = MODIFIERS.register("deepoceanchew", deepoceanchew::new);   //测试
    public static final StaticModifier<natived> NATIVED_STATIC_MODIFIER = MODIFIERS.register("natived", natived::new);
    public static final StaticModifier<watery> WATERY_STATIC_MODIFIER = MODIFIERS.register("watery", watery::new);
    public static final StaticModifier<bha> BHA_STATIC_MODIFIER = MODIFIERS.register("bha", bha::new);
}
