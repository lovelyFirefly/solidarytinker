package com.marth7th.solidarytinker.register;

import com.marth7th.solidarytinker.Modifiers.armor.EnderInhibit;
import com.marth7th.solidarytinker.Modifiers.armor.FluxArmor;
import com.marth7th.solidarytinker.Modifiers.battle.technology.FluxWeapon;
import com.marth7th.solidarytinker.Modifiers.battle.technology.Fluxshot;
import com.marth7th.solidarytinker.Modifiers.battle.technology.MercuryPoisoning;
import com.marth7th.solidarytinker.Modifiers.slots.energyadd;
import com.marth7th.solidarytinker.Modifiers.slots.energymultiple;
import com.marth7th.solidarytinker.Modifiers.slots.energytransport;
import slimeknights.tconstruct.library.modifiers.util.ModifierDeferredRegister;
import slimeknights.tconstruct.library.modifiers.util.StaticModifier;

import static com.marth7th.solidarytinker.solidarytinker.MOD_ID;

public class solidarytinkerModifierMekEtsh {
    public static ModifierDeferredRegister MODIFIERS = ModifierDeferredRegister.create(MOD_ID);
    public static final StaticModifier<energyadd> energyadd = MODIFIERS.register("energyadd", energyadd::new);
    public static final StaticModifier<energymultiple> energymultiple = MODIFIERS.register("energymultiple", energymultiple::new);
    public static final StaticModifier<energytransport> energytransport = MODIFIERS.register("energytransport", energytransport::new);
    public static final StaticModifier<FluxArmor> FLUX_ARMOR_STATIC_MODIFIER = MODIFIERS.register("fluxarmor", FluxArmor::new);   //测试
    public static final StaticModifier<FluxWeapon> fluxweapon = MODIFIERS.register("fluxweapon", FluxWeapon::new);   //测试
    public static final StaticModifier<Fluxshot> fluxshot = MODIFIERS.register("fluxshot", Fluxshot::new);   //测试
    public static final StaticModifier<MercuryPoisoning> MercuryPoisoning = MODIFIERS.register("mercurypoisoning", MercuryPoisoning::new);   //测试
    public static final StaticModifier<EnderInhibit> enderinhibit = MODIFIERS.register("enderinhibit", EnderInhibit::new);   //测试
}
