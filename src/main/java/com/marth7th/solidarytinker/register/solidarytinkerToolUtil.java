package com.marth7th.solidarytinker.register;


import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import slimeknights.tconstruct.library.client.model.TinkerItemProperties;

import static com.marth7th.solidarytinker.solidarytinker.MOD_ID;


@Mod.EventBusSubscriber(modid = MOD_ID, value = {Dist.CLIENT}, bus = Mod.EventBusSubscriber.Bus.MOD
)
public class solidarytinkerToolUtil {
    public solidarytinkerToolUtil() {
    }
    @SubscribeEvent
    public static void clientSetupEvent(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            TinkerItemProperties.registerToolProperties(solidarytinkerItem.trident.get().asItem());
        });
    }
}
