package com.marth7th.solidarytinker.register;

import com.marth7th.solidarytinker.Modifiers.curios.*;
import slimeknights.tconstruct.library.modifiers.util.ModifierDeferredRegister;
import slimeknights.tconstruct.library.modifiers.util.StaticModifier;

import static com.marth7th.solidarytinker.solidarytinker.MOD_ID;

public class TinkerCuriosModifier {
    public static ModifierDeferredRegister MODIFIERS = ModifierDeferredRegister.create(MOD_ID);
    public static final StaticModifier<AngelProtect> ANGELPROTECT_STATIC_MODIFIER = MODIFIERS.register("angelprotect", AngelProtect::new);   //天使狐
    public static final StaticModifier<DeepOceanChew> DEEPOCEANCHEW_STATIC_MODIFIER = MODIFIERS.register("deepoceanchew", DeepOceanChew::new);   //测试
    public static final StaticModifier<Natived> NATIVED_STATIC_MODIFIER = MODIFIERS.register("natived", Natived::new);
    public static final StaticModifier<Watery> WATERY_STATIC_MODIFIER = MODIFIERS.register("watery", Watery::new);
    public static final StaticModifier<bha> BHA_STATIC_MODIFIER = MODIFIERS.register("bha", bha::new);
}
