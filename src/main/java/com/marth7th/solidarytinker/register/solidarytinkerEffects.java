package com.marth7th.solidarytinker.register;

import com.marth7th.solidarytinker.effects.bloodanger;
import net.minecraft.world.effect.MobEffect;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static com.marth7th.solidarytinker.solidarytinker.MOD_ID;

public class solidarytinkerEffects  {
    public static final DeferredRegister<MobEffect> EFFECT = DeferredRegister.create(ForgeRegistries.MOB_EFFECTS, MOD_ID);
    public static final RegistryObject<MobEffect> bloodanger=EFFECT.register("bloodanger",bloodanger::new);


}
