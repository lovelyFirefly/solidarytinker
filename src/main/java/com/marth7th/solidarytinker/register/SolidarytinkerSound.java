package com.marth7th.solidarytinker.register;

import com.marth7th.solidarytinker.solidarytinker;
import net.minecraft.sounds.SoundEvent;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.function.Supplier;

public class SolidarytinkerSound {
    public static final DeferredRegister<SoundEvent> sound = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, solidarytinker.MOD_ID);
    public static final Supplier<SoundEvent> electric_hit = sound.register("electric_hit", () -> new SoundEvent(solidarytinker.getResource("electric_hit")));
    public static final Supplier<SoundEvent> electric_not_full = sound.register("electric_not_full", () -> new SoundEvent(solidarytinker.getResource("electric_not_full")));
    public static final Supplier<SoundEvent> electric_cooldown = sound.register("electric_cooldown", () -> new SoundEvent(solidarytinker.getResource("electric_cooldown")));
    public static void register(IEventBus bus){
        sound.register(bus);
    }
}
