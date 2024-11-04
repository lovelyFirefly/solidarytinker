package com.marth7th.solidarytinker.register;

import com.marth7th.solidarytinker.Modifiers.curios.angelprotect;
import slimeknights.tconstruct.library.modifiers.util.ModifierDeferredRegister;
import slimeknights.tconstruct.library.modifiers.util.StaticModifier;

import static com.marth7th.solidarytinker.solidarytinker.MOD_ID;

public class TinkerCuriosModifier{
    public static ModifierDeferredRegister MODIFIERS = ModifierDeferredRegister.create(MOD_ID);
    public static final StaticModifier<angelprotect> ANGELPROTECT_STATIC_MODIFIER=MODIFIERS.register("angelprotect", angelprotect::new);   //测试
}
