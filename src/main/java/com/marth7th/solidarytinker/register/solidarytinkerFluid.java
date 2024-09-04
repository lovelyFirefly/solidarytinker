package com.marth7th.solidarytinker.register;

import net.minecraft.sounds.SoundEvents;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import slimeknights.mantle.registration.deferred.FluidDeferredRegister;
import slimeknights.mantle.registration.object.FluidObject;

import static com.marth7th.solidarytinker.solidarytinker.MOD_ID;
public class solidarytinkerFluid{
    public static final FluidDeferredRegister FLUIDS = new FluidDeferredRegister(MOD_ID);
    private static FluidObject<ForgeFlowingFluid> register(String name, int temp) {
        return FLUIDS.register(name).type(FluidType.Properties.create().density(2000).viscosity(10000).temperature(temp).sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL_LAVA)).bucket().flowing();
    }
    public static final FluidObject<ForgeFlowingFluid> molten_dwarf = register("molten_dwarf", 5867);//熔融白矮星物质
    public static final FluidObject<ForgeFlowingFluid> super_dt = register("super_dt", 9500);//超能氘氚燃料
    public static final FluidObject<ForgeFlowingFluid> molten_starfall = register("molten_starfall", 1450);//熔融星落钢
    public static final FluidObject<ForgeFlowingFluid> molten_icefantasy = register("molten_icefantasy", 1450);//熔融墨冰合金
    public static final FluidObject<ForgeFlowingFluid> molten_rainbow = register("molten_rainbow", 5600);//熔融彩虹化合物
    public static final FluidObject<ForgeFlowingFluid> molten_damascus_steel = register("molten_damascus_steel", 1850);//熔融大马士革
    public static final FluidObject<ForgeFlowingFluid> molten_experience_steel = register("molten_experience_steel", 860);//熔融经验钢


}
