package com.marth7th.solidarytinker;

import com.c2h6s.etshtinker.Mapping.ionizerFluidMapMek;
import com.marth7th.solidarytinker.etshtinker.etshinkercarbon;
import com.marth7th.solidarytinker.register.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import slimeknights.tconstruct.library.tools.capability.TinkerDataCapability;

@Mod(solidarytinker.MOD_ID)

public class solidarytinker {
    /**
     * 联动模组
     * mekanism
     * etshtinker的离子炮
     */
    public static boolean Mekenabled = ModList.get().isLoaded("mekanism");
    public static boolean ETSH = ModList.get().isLoaded("etshtinker");
    public static final String MOD_ID = "solidarytinker"; //*是你的模组名，需要英文
    public solidarytinker() {
        /**
         *几个注册表都在这边，有的联动所以需要前置
         */
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        eventBus.addListener(this::commonSetup);
        solidarytinkerItem.ITEMS.register(eventBus);
        solidarytinkerModifiers.MODIFIERS.register(eventBus);
        solidarytinkerFluid.FLUIDS.register(eventBus);
        solidarytinkerBlock.BLOCK.register(eventBus);
        solidarytinkerEffects.EFFECT.register(eventBus);
        if(Mekenabled){
            solidarytinkerGas.GAS.register(eventBus);
        }
    }

    /**
     * 抑制etshtinker相关类加载
     */

    public void commonSetup(FMLCommonSetupEvent event) {
        if (Mekenabled&&ETSH){
            event.enqueueWork(ionizerFluidMapMek::extendMap);
            event.enqueueWork(etshinkercarbon::extendMap);
        }
//        幽默挖掘等级(未实装）
//        TierSortingRegistry.registerTier(overlord.instance,new ResourceLocation("solidarytinker:overlord"), List.of(Tiers.NETHERITE),List.of());
    }
    public static ResourceLocation getResource(String id) {
        return new ResourceLocation("solidarytinker", id);
    }
    public static <T> TinkerDataCapability.TinkerDataKey<T> createKey(String name) {
        return TinkerDataCapability.TinkerDataKey.of(getResource(name));
    }
}
