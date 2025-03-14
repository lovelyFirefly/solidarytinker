package com.marth7th.solidarytinker;

import com.c2h6s.etshtinker.Mapping.ionizerFluidMapMek;
import com.kwpugh.gobber2.lists.tiers.ToolMaterialTiers;
import com.marth7th.solidarytinker.config.SolidarytinkerConfig;
import com.marth7th.solidarytinker.etshtinker.EtshinkerCarbon;
import com.marth7th.solidarytinker.register.*;
import com.marth7th.solidarytinker.shelf.Network.STChannel;
import com.marth7th.solidarytinker.shelf.energy.FluxStorage;
import com.marth7th.solidarytinker.shelf.tier.Momo;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Tiers;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.TierSortingRegistry;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import slimeknights.tconstruct.library.tools.capability.TinkerDataCapability;
import slimeknights.tconstruct.library.tools.capability.ToolCapabilityProvider;

import java.util.List;

@Mod(solidarytinker.MOD_ID)
@Mod.EventBusSubscriber(
        bus = Mod.EventBusSubscriber.Bus.MOD
)

public class solidarytinker {
    public static boolean gobber2 = ModList.get().isLoaded("gobber2");
    public static boolean Mekenabled = ModList.get().isLoaded("mekanism");
    public static boolean ETSH = ModList.get().isLoaded("etshtinker");
    public static boolean TI = ModList.get().isLoaded("tinkers_ingenuity");
    public static final String MOD_ID = "solidarytinker";

    public solidarytinker() {
        /*
         几个注册表都在这边，有的联动所以需要前置
         */
        IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        eventBus.addListener(this::commonSetup);
        MinecraftForge.EVENT_BUS.register(this);
        solidarytinkerItem.ITEMS.register(eventBus);
        solidarytinkerItem.OTHER_ITEM.register(eventBus);
        solidarytinkerModifiers.MODIFIERS.register(eventBus);
        solidarytinkerFluid.FLUIDS.register(eventBus);
        solidarytinkerBlock.BLOCK.register(eventBus);
        solidarytinkerEffects.EFFECT.register(eventBus);
        //config
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, SolidarytinkerConfig.Materialspec, "solidarytinkermaterials.toml");
        ModLoadingContext.get().registerConfig(ModConfig.Type.COMMON, SolidarytinkerConfig.Toolspec, "solidarytinkertools.toml");


        solidarytinkerSlots.init();
        if (Mekenabled && ETSH) {
            solidarytinkerGas.GAS.register(eventBus);
            solidarytinkerModifierMekEtsh.MODIFIERS.register(eventBus);
        }
        if (TI) {
            TinkerCuriosModifier.MODIFIERS.register(eventBus);
        }
    }

    public void commonSetup(FMLCommonSetupEvent event) {
        ToolCapabilityProvider.register(FluxStorage::new);

        STChannel.register();
        if (Mekenabled && ETSH) {
            event.enqueueWork(ionizerFluidMapMek::extendMap);
            event.enqueueWork(EtshinkerCarbon::extendMap);
        }
        if (!TierSortingRegistry.isTierSorted(Momo.instance)) {
            if (gobber2 && TierSortingRegistry.isTierSorted(ToolMaterialTiers.END_GOBBER)) {
                TierSortingRegistry.registerTier(Momo.instance, new ResourceLocation("solidarytinker:momo"), List.of(ToolMaterialTiers.END_GOBBER), List.of());
            } else {
                TierSortingRegistry.registerTier(Momo.instance, new ResourceLocation("solidarytinker:momo"), List.of(Tiers.NETHERITE), List.of());
            }
        }
    }

    public static ResourceLocation getResource(String id) {
        return new ResourceLocation("solidarytinker", id);
    }

    public static <T> TinkerDataCapability.TinkerDataKey<T> createKey(String name) {
        return TinkerDataCapability.TinkerDataKey.of(getResource(name));
    }

    public static String makeDescriptionId(String type, String name) {
        return type + ".solidarytinker." + name;
    }
}
