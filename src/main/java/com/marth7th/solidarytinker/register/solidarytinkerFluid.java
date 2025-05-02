package com.marth7th.solidarytinker.register;

import com.marth7th.solidarytinker.solidarytinker;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.material.Material;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import slimeknights.mantle.registration.deferred.FluidDeferredRegister;
import slimeknights.mantle.registration.object.FlowingFluidObject;
import slimeknights.mantle.registration.object.FluidObject;

import static com.marth7th.solidarytinker.solidarytinker.MOD_ID;

public class solidarytinkerFluid {
    public static final FluidDeferredRegister FLUIDS = new FluidDeferredRegister(MOD_ID);

    public solidarytinkerFluid() {
    }

    public static final FluidObject<ForgeFlowingFluid> molten_heavy_armor_steel = register("molten_heavy_armor_steel", 860);

    private static FluidType.Properties hot(String name) {
        return FluidType.Properties.create().density(2000).viscosity(10000).temperature(1000).descriptionId(solidarytinker.makeDescriptionId("fluid", name)).sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL).sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_FILL);
    }

    private static FluidType.Properties cool(String name) {
        return cool().descriptionId(solidarytinker.makeDescriptionId("fluid", name)).sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL).sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_FILL);
    }

    private static FlowingFluidObject<ForgeFlowingFluid> register(String name, int temp) {
        return FLUIDS.register(name).type(hot(name).temperature(temp).lightLevel(12)).block(Material.LAVA, 15).bucket().flowing();
    }
    public static final FluidObject<ForgeFlowingFluid> molten_dwarf = register("molten_dwarf", 5867);//熔融白矮星物质
    public static final FluidObject<ForgeFlowingFluid> super_dt = register("super_dt", 9500);//超能氘氚燃料
    public static final FluidObject<ForgeFlowingFluid> molten_starfall = register("molten_starfall", 1450);//熔融星落钢
    public static final FluidObject<ForgeFlowingFluid> molten_icefantasy = register("molten_icefantasy", 1450);//熔融墨冰合金
    public static final FluidObject<ForgeFlowingFluid> molten_rainbow = register("molten_rainbow", 5600);//熔融彩虹化合物
    public static final FluidObject<ForgeFlowingFluid> molten_damascus_steel = register("molten_damascus_steel", 1850);//熔融大马士革
    public static final FluidObject<ForgeFlowingFluid> molten_experience_steel = register("molten_experience_steel", 860);//熔融经验钢
    public static final FluidObject<ForgeFlowingFluid> molten_magicuranium = register("molten_magicuranium", 860);//熔融魔力铀
    public static final FluidObject<ForgeFlowingFluid> molten_bloodmeat = register("molten_bloodmeat", 860);//半熔融血肉混合物
    public static final FluidObject<ForgeFlowingFluid> molten_kemomimi = register("molten_kemomimi", 860);//熔融kemomimi
    public static final FluidObject<ForgeFlowingFluid> molten_takeru = register("molten_takeru", 860);//熔融takeru
    public static final FluidObject<ForgeFlowingFluid> molten_extremelycoldsteel = register("molten_extremelycoldsteel", 860);//熔融超低温钢
    public static final FluidObject<ForgeFlowingFluid> molten_cold_chroma_alloy = register("molten_cold_chroma_alloy", 860);//熔融极寒彩钢
    public static final FluidObject<ForgeFlowingFluid> molten_mercury = register("molten_mercury", 860);//水银
    public static final FluidObject<ForgeFlowingFluid> molten_sulfur = register("molten_sulfur", 860);//熔融硫磺
    public static final FluidObject<ForgeFlowingFluid> molten_leadamalgamation = register("molten_leadamalgamation", 860);//熔融铅汞齐

    private static FluidType.Properties cool() {
        return FluidType.Properties.create().sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL).sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_FILL);
    }

}
