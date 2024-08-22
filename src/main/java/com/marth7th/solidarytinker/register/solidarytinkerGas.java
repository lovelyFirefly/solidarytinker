package com.marth7th.solidarytinker.register;

import mekanism.api.chemical.gas.Gas;
import mekanism.common.registration.impl.GasDeferredRegister;
import mekanism.common.registration.impl.GasRegistryObject;

import static com.marth7th.solidarytinker.solidarytinker.MOD_ID;
import static mekanism.common.registries.MekanismGases.GASES;

public class solidarytinkerGas{
    public static final GasDeferredRegister GAS = new GasDeferredRegister(MOD_ID);
    public static final GasRegistryObject<Gas> FLUORINE  = GASES.register("fluorine", 0xadc739);

}
