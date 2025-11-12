package com.marth7th.solidarytinker.register;

import com.marth7th.solidarytinker.solidarytinker;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraftforge.common.SoundActions;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fluids.FluidType;
import net.minecraftforge.fluids.ForgeFlowingFluid;
import slimeknights.mantle.registration.deferred.FluidDeferredRegister;
import slimeknights.mantle.registration.object.FlowingFluidObject;
import slimeknights.mantle.registration.object.FluidObject;

import java.util.HashMap;
import java.util.Map;

import static com.marth7th.solidarytinker.solidarytinker.MOD_ID;
import static slimeknights.tconstruct.fluids.block.BurningLiquidBlock.createBurning;

public class solidarytinkerFluid {
    public static final FluidDeferredRegister FLUIDS = new FluidDeferredRegister(MOD_ID);
    protected static Map<FluidObject<ForgeFlowingFluid>, Boolean> FLUID_MAP = new HashMap<>();

    public static final FluidObject<ForgeFlowingFluid> molten_heavy_armor_steel = registerHotBurning("molten_heavy_armor_steel", 860,15,3,3);
    public static final FluidObject<ForgeFlowingFluid> molten_dwarf = registerHotBurning("molten_dwarf", 5867,15,3,3);//熔融白矮星物质
    public static final FluidObject<ForgeFlowingFluid> super_dt = registerHotBurning("super_dt", 9500,15,3,3);//超能氘氚燃料
    public static final FluidObject<ForgeFlowingFluid> molten_starfall = registerHotBurning("molten_starfall", 1450,15,3,3);//熔融星野
    public static final FluidObject<ForgeFlowingFluid> molten_icefantasy = registerHotBurning("molten_icefantasy", 1450,15,3,3);//熔融墨冰合金
    public static final FluidObject<ForgeFlowingFluid> molten_rainbow = registerHotBurning("molten_rainbow", 5600,15,3,3);//熔融彩虹化合物
    public static final FluidObject<ForgeFlowingFluid> molten_damascus_steel = registerHotBurning("molten_damascus_steel", 1850,15,3,3);//熔融大马士革
    public static final FluidObject<ForgeFlowingFluid> molten_experience_steel = registerHotBurning("molten_experience_steel", 860,15,3,3);//熔融经验钢
    public static final FluidObject<ForgeFlowingFluid> molten_magicuranium = registerHotBurning("molten_magicuranium", 860,15,3,3);//熔融魔力铀
    public static final FluidObject<ForgeFlowingFluid> molten_bloodmeat = registerHotBurning("molten_bloodmeat", 860,15,3,3);//半熔融血肉混合物
    public static final FluidObject<ForgeFlowingFluid> molten_kemomimi = registerHotBurning("molten_kemomimi", 860,15,3,3);//熔融kemomimi
    public static final FluidObject<ForgeFlowingFluid> molten_takeru = registerHotBurning("molten_takeru", 860,15,3,3);//熔融takeru
    public static final FluidObject<ForgeFlowingFluid> molten_extremelycoldsteel = registerHotBurning("molten_extremelycoldsteel", 860,15,3,3);//熔融超低温钢
    public static final FluidObject<ForgeFlowingFluid> molten_cold_chroma_alloy = registerHotBurning("molten_cold_chroma_alloy", 860,15,3,3);//熔融极寒彩钢
    public static final FluidObject<ForgeFlowingFluid> molten_mercury = registerHotBurning("molten_mercury", 860,15,3,3);//水银
    public static final FluidObject<ForgeFlowingFluid> molten_sulfur = registerHotBurning("molten_sulfur", 860,15,3,3);//熔融硫磺
    public static final FluidObject<ForgeFlowingFluid> molten_leadamalgamation = registerHotBurning("molten_leadamalgamation", 860,15,3,3);//熔融铅汞齐
    public static final FluidObject<ForgeFlowingFluid> molten_elysia = registerHotBurning("molten_elysia", 860,15,3,3);


    private static FluidType.Properties hot(String name, int Temp) {
        return FluidType.Properties.create().density(2000).viscosity(10000).temperature(Temp)
                .descriptionId("fluid." + MOD_ID + "." + name)
                .sound(SoundActions.BUCKET_FILL, SoundEvents.BUCKET_FILL_LAVA)
                .sound(SoundActions.BUCKET_EMPTY, SoundEvents.BUCKET_EMPTY_LAVA)
                .motionScale(0.0023333333333333335D)
                .canSwim(false).canDrown(false)
                .pathType(BlockPathTypes.LAVA).adjacentPathType(null);
    }
    private static FluidObject<ForgeFlowingFluid> registerHotBurning(String name, int temp, int lightLevel, int burnTime, float damage) {
        FluidObject<ForgeFlowingFluid> object = FLUIDS.register(name).type(hot(name, temp)).bucket().block(createBurning(lightLevel, burnTime, damage)).flowing();
        FLUID_MAP.put(object, false);
        return object;
    }
    public static void register(IEventBus bus) {
        FLUIDS.register(bus);
    }
}
