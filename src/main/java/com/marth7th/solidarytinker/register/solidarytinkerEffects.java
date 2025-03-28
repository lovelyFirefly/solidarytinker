package com.marth7th.solidarytinker.register;

import com.marth7th.solidarytinker.effects.*;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.marth7th.solidarytinker.solidarytinker.MOD_ID;

public class solidarytinkerEffects {
    public static final DeferredRegister<MobEffect> EFFECT = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, MOD_ID);
    public static final RegistryObject<MobEffect> bloodanger = EFFECT.register("bloodanger", BloodAnger::new);
    public static final RegistryObject<MobEffect> seriously_injured = EFFECT.register("seriously_injured", Seriously_Injured::new);
    public static final RegistryObject<MobEffect> mercurypoisoning = EFFECT.register("mercurypoisoning", MercuryPoisoning::new);
    public static final RegistryObject<MobEffect> higtca = EFFECT.register("higtca", HighCa::new);
    public static final RegistryObject<MobEffect> SuperPoison = EFFECT.register("superpoison", SuperPoison::new);

}