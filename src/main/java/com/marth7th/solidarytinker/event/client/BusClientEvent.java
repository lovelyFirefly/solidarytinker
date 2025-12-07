package com.marth7th.solidarytinker.event.client;

import com.marth7th.solidarytinker.client.Hud.BarOverlayImpl;
import com.marth7th.solidarytinker.client.Hud.HoshinoStagnationHUD;
import com.marth7th.solidarytinker.client.Hud.NumberBlockOverlay;
import com.marth7th.solidarytinker.client.KeyBinding;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterGuiOverlaysEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class BusClientEvent {
    @SubscribeEvent
    public static void onKeyRegister(RegisterKeyMappingsEvent event) {
        event.register(KeyBinding.DIGGING_SPEED_KEY);
        event.register(KeyBinding.SOULGE_CONFIG);
    }

    @SubscribeEvent
    public static void registerGuiOverlay(RegisterGuiOverlaysEvent event) {
        event.registerAboveAll("energy", BarOverlayImpl.EnergyHud);
        event.registerAboveAll("number_block", NumberBlockOverlay.GemHealthHud);
        event.registerAboveAll("stagnation", HoshinoStagnationHUD.STAGNATION_WAIT_HUD);
    }

}
